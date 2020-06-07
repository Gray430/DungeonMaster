package net.dungeonsworkshop.dungeonmaster.common.map.editor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.dungeonsworkshop.dungeonmaster.DungeonMaster;
import net.dungeonsworkshop.dungeonmaster.common.entity.TileBlockTE;
import net.dungeonsworkshop.dungeonmaster.common.map.objects.Door;
import net.dungeonsworkshop.dungeonmaster.common.map.objects.ObjectGroup;
import net.dungeonsworkshop.dungeonmaster.common.map.objects.Region;
import net.dungeonsworkshop.dungeonmaster.common.map.objects.Tile;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid = DungeonMaster.MOD_ID)
public class EditorManager {
    public static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(ObjectGroup.class, new ObjectGroup.ObjectGroupDeserializer())
            .registerTypeAdapter(Tile.class, new Tile.TileDeserializer())
            .registerTypeAdapter(Region.class, new Region.RegionDeserilizer())
            .registerTypeAdapter(Door.class, new Door.DoorDeserilizer())
            .setLenient()
            .create();
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String LOVIKA_URL = "data/" + DungeonMaster.MOD_ID + "/lovika";

    public static Tile getTileFromId(String objectGroupId, String id) {
        return getObjectGroupFromId(objectGroupId).getTile(id);
    }

    public static ObjectGroup getObjectGroupFromId(String id) {
        JsonObject jsonObject = readJsonFromResource(LOVIKA_URL + "/objectgroups/" + id.toLowerCase() + "/objectgroup.json");
        return GSON.fromJson(jsonObject, ObjectGroup.class);
    }

    public static JsonObject readJsonFromResource(String filePath) {
        try (InputStream in = EditorManager.class.getClassLoader().getResourceAsStream(filePath)) {
            Reader reader = new InputStreamReader(in, "UTF-8");
            return new JsonParser().parse(reader).getAsJsonObject();
        } catch (IOException e) {
            LOGGER.error("Reading string from resource failed", e);
            return new JsonObject();
        }
    }

    @SubscribeEvent
    public static void serverStartedEvent(FMLServerStartedEvent event){
        BlockMapper.loadBlockMappings();
    }

    @OnlyIn(Dist.CLIENT)
    public static class ClientTileBlockManager{
        public static final Map<Vec3i, Vec3i> TILE_MANAGERS = new HashMap<>();


    }

}
