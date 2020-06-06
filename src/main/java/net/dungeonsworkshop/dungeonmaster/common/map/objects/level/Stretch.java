package net.dungeonsworkshop.dungeonmaster.common.map.objects.level;

import com.google.gson.*;
import net.dungeonsworkshop.dungeonmaster.common.map.objects.Level;
import net.dungeonsworkshop.dungeonmaster.common.map.objects.Tile;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Stretch {

    private List<String> tiles;
    private int length = 1;

    public List<String> getTiles() {
        return tiles;
    }

    public void setTiles(List<String> tiles) {
        this.tiles = tiles;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public static class StretchDeserializer implements JsonDeserializer<Stretch> {

        @Override
        public Stretch deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();

            List<String> tiles = new ArrayList<>();
            if(json.getAsJsonObject().has("tiles"))
                jsonObject.getAsJsonArray("tiles").forEach(jsonElement ->
                        tiles.add(jsonElement.getAsString())
                );

            Stretch stretch = new Stretch();

            stretch.setTiles(tiles);
            if(json.getAsJsonObject().has("length"))
                stretch.setLength(jsonObject.get("length").getAsInt());

            return stretch;
        }

    }


}
