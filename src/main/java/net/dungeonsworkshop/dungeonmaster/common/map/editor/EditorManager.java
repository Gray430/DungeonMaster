package net.dungeonsworkshop.dungeonmaster.common.map.editor;

import com.google.gson.*;
import net.dungeonsworkshop.dungeonmaster.DungeonMaster;
import net.dungeonsworkshop.dungeonmaster.common.map.objects.Door;
import net.dungeonsworkshop.dungeonmaster.common.map.objects.ObjectGroup;
import net.dungeonsworkshop.dungeonmaster.common.map.objects.Region;
import net.dungeonsworkshop.dungeonmaster.common.map.objects.Tile;
import net.dungeonsworkshop.dungeonmaster.common.network.DungeonsMessageHandler;
import net.dungeonsworkshop.dungeonmaster.common.network.message.TileManagerLoadMessage;
import net.dungeonsworkshop.dungeonmaster.common.network.message.TileManagerSyncMessage;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.network.PacketDistributor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = DungeonMaster.MOD_ID)
public class EditorManager {
    private static final String LOVIKA_URL = "data/" + DungeonMaster.MOD_ID + "/lovika";
    private static final Logger LOGGER = LogManager.getLogger();
    private static final List<BlockPos> TILE_MANAGER_POSITIONS = new ArrayList<>();

    public static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(ObjectGroup.class, new ObjectGroup.ObjectGroupDeserializer())
            .registerTypeAdapter(Tile.class, new Tile.TileDeserializer())
            .registerTypeAdapter(Region.class, new Region.RegionDeserilizer())
            .registerTypeAdapter(Door.class, new Door.DoorDeserilizer())
            .setLenient()
            .create();

    public static Tile getTileFromId(String objectGroupId, String id) {
        return getObjectGroupFromId(objectGroupId).getTile(id);
    }

    public static ObjectGroup getObjectGroupFromId(String id) {
        JsonObject jsonObject = readJsonFromResource(LOVIKA_URL + "/objectgroups/" + id.toLowerCase() + "/objectgroup.json");
        return GSON.fromJson(jsonObject, ObjectGroup.class);
    }

    public static JsonObject readJsonFromResource(String filePath) {
        try (InputStream in = EditorManager.class.getClassLoader().getResourceAsStream(filePath)) {
            if (in != null) {
                Reader reader = new InputStreamReader(in, "UTF-8");
                return new JsonParser().parse(reader).getAsJsonObject();
            }
        } catch (IOException e) {
            LOGGER.error("Reading string from resource failed", e);
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("objects", new JsonArray());
        return jsonObject;
    }

    public static void loadTileBlock(BlockPos pos, boolean client) {
        if (!TILE_MANAGER_POSITIONS.contains(pos)) {
            TILE_MANAGER_POSITIONS.add(pos);
        }
        if (!client) {
            DungeonsMessageHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new TileManagerSyncMessage(pos, TileManagerSyncMessage.SyncType.LOAD));
        }
    }

    public static void unloadTileBlock(BlockPos pos, boolean client) {
        if (TILE_MANAGER_POSITIONS.contains(pos)) {
            TILE_MANAGER_POSITIONS.remove(pos);
        }
        if (!client) {
            DungeonsMessageHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new TileManagerLoadMessage(pos, false));
        }
    }

    public static void loadAllTileBlocksClient(ServerPlayerEntity playerEntity) {
        TILE_MANAGER_POSITIONS.forEach(pos -> DungeonsMessageHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new TileManagerLoadMessage(pos, true)));
    }

    public static List<BlockPos> getTileManagerPositions() {
        return TILE_MANAGER_POSITIONS;
    }

    @SubscribeEvent
    public static void serverStartedEvent(FMLServerStartedEvent event) {
        BlockMapper.loadBlockMappings();
    }

}
