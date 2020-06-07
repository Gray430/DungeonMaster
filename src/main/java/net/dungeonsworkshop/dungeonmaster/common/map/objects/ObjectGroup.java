package net.dungeonsworkshop.dungeonmaster.common.map.objects;

import com.google.gson.*;
import net.minecraft.util.math.Vec3i;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectGroup {
    private final Map<String, Tile> objects = new HashMap<>();

    public ObjectGroup addTile(Tile tile) {
        objects.put(tile.getId().toLowerCase(), tile);
        return this;
    }

    public Map<String, Tile> getObjects() {
        return objects;
    }

    public Tile getTile(String id) {
        return objects.get(id.toLowerCase());
    }

    public static class ObjectGroupDeserializer implements JsonDeserializer<ObjectGroup> {

        @Override
        public ObjectGroup deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonArray tileJsonArray = json.getAsJsonObject().getAsJsonArray("objects");
            ObjectGroup objectGroup = new ObjectGroup();

            for(JsonElement tileJson : tileJsonArray){
                objectGroup.addTile(context.deserialize(tileJson, Tile.class));
            }

            return objectGroup;
        }
    }

}
