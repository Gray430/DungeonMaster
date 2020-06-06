package net.dungeonsworkshop.dungeonmaster.common.map.objects.level;

import com.google.gson.*;
import net.dungeonsworkshop.dungeonmaster.common.map.editor.EditorManager;
import net.dungeonsworkshop.dungeonmaster.common.map.objects.Level;
import net.dungeonsworkshop.dungeonmaster.common.map.objects.Tile;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LevelTile {

    private String id;
//    private int rotations;
    private String entryDoor;
    private String location;

    public LevelTile(String id){
        this.id = id;
    }

    public Tile getTileObject(){
        return EditorManager.instance().getTileByID(id);
    }

//    public static class LevelTileDeserializer implements JsonDeserializer<LevelTile> {
//
//        @Override
//        public LevelTile deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
//            JsonObject jsonObject = json.getAsJsonObject();
//
//            String id = jsonObject.get("id").getAsString();
//            String resourcepack = jsonObject.getAsJsonArray("resource-packs").get(0).getAsString();
//            String visualTheme = jsonObject.get("visual-theme").getAsString();
//            String soundTheme = jsonObject.get("sound-theme").getAsString();
//
//            jsonObject.getAsJsonArray("tiles").forEach(jsonElement -> {
//                levelTiles.put(jsonElement.getAsJsonObject().get("id").getAsString(), context.deserialize(jsonElement, LevelTile.class));
//            });
//
//
//            LevelTile levelTile = new LevelTile(id);
//
//            return levelTile;
//        }
//
//    }

}
