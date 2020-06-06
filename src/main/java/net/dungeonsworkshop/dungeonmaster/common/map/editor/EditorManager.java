package net.dungeonsworkshop.dungeonmaster.common.map.editor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.dungeonsworkshop.dungeonmaster.DungeonMaster;
import net.dungeonsworkshop.dungeonmaster.common.entity.TileBlockTE;
import net.dungeonsworkshop.dungeonmaster.common.map.objects.*;
import net.dungeonsworkshop.dungeonmaster.common.map.objects.level.Stretch;
import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

@Mod.EventBusSubscriber(modid = DungeonMaster.MOD_ID)
public class EditorManager extends JsonReloadListener {
    public static Gson GSON = new GsonBuilder()
            .registerTypeAdapter(Tile.class, new Tile.TileDeserializer())
            .registerTypeAdapter(Region.class, new Region.RegionDeserilizer())
            .registerTypeAdapter(Door.class, new Door.DoorDeserilizer())
            .registerTypeAdapter(Level.class, new Level.LevelDeserializer())
            .registerTypeAdapter(Stretch.class, new Stretch.StretchDeserializer())
            .setLenient()
            .create();
    private static final String name = "lovika";
    private static final Logger LOGGER = LogManager.getLogger();

    private static EditorManager instance;

    public static final List<TileBlockTE> TILE_MANAGERS = new ArrayList<>();

    public final Map<String, ObjectGroup> OBJECT_GROUPS = new HashMap<>();
    public final Map<String, Level> LEVELS = new HashMap<>();

    public EditorManager() {
        super(GSON, name);
        instance = this;
    }

    public Tile getTileByID(String id){
        for(Map.Entry<String, ObjectGroup> objectGroup : OBJECT_GROUPS.entrySet()){
            for(Map.Entry<String, Tile> map : objectGroup.getValue().getObjects().entrySet()){
                if(map.getKey().equalsIgnoreCase(id)){
                    return map.getValue();
                }
            }
        }
        return null;
    }

    private void LoadObjectGroups(Set<Pair<String, JsonObject>> objectGroups) {
        OBJECT_GROUPS.clear();
        objectGroups.forEach(pair -> {
            ObjectGroup objectGroup = new ObjectGroup(pair.getLeft());

            JsonArray objects = pair.getRight().getAsJsonArray("objects");
            objects.forEach(jsonElement -> {
                objectGroup.addTile(GSON.fromJson(jsonElement, Tile.class));
            });

            OBJECT_GROUPS.put(pair.getLeft(), objectGroup);
        });
    }

    private void LoadLevels(Set<Pair<String, JsonObject>> levels) {
        LEVELS.clear();
        levels.forEach(pair -> LEVELS.put(pair.getLeft(), GSON.fromJson(pair.getRight(), Level.class)));
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonObject> objectIn, IResourceManager resourceManagerIn, IProfiler profilerIn) {
        Set<Pair<String, JsonObject>> objectGroups = new HashSet<>();
        Set<Pair<String, JsonObject>> levels = new HashSet<>();
        for (Map.Entry<ResourceLocation, JsonObject> entry : objectIn.entrySet()) {
            ResourceLocation name = entry.getKey();
            try {

                if (name.getPath().startsWith("levels")) {
                    String levelName = name.getPath().substring("levels/".length()).split("/")[0];
                    levels.add(new ImmutablePair<>(levelName, entry.getValue()));
                } else if (name.getPath().startsWith("objectgroups")) {
                    String objectGroupName = name.getPath().substring("objectgroups/".length()).split("/")[0];
                    objectGroups.add(new ImmutablePair<>(objectGroupName, entry.getValue()));
                } else if (!name.getPath().startsWith("mappings")) {
                    LOGGER.warn("Unexpected Directory '" + name + "'");
                }
            } catch (Exception e) {
                LOGGER.error("Failed to load '" + name + "'", e);
            }
        }
        LoadObjectGroups(objectGroups);
        LoadLevels(levels);
        BlockMapper.loadBlockMappings();
        LOGGER.info("Loaded " + objectGroups.size() + " ObjectGroups & " + levels.size() + " Levels");
    }

    @SubscribeEvent
    public static void onEvent(FMLServerAboutToStartEvent event) {
        event.getServer().getResourceManager().addReloadListener(new EditorManager());
    }

    @SubscribeEvent
    public static void onEvent(FMLServerStoppingEvent event) {
        instance = null;
    }

    public static EditorManager instance() {
        return instance == null ? instance = new EditorManager() : instance;
    }

}
