package net.dungeonsworkshop.dungeonmaster.common.map.objects;

import com.google.gson.*;
import net.minecraft.util.math.Vec3i;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Door {

    private String name;
    private Vec3i pos;
    private Vec3i size;
    private String tags;

    public Door(String name, Vec3i pos, Vec3i size, String tags) {
        this.name = name;
        this.pos = pos;
        this.size = size;
        this.tags = tags;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Vec3i getPos() {
        return pos;
    }

    public void setPos(Vec3i pos) {
        this.pos = pos;
    }

    public Vec3i getSize() {
        return size;
    }

    public void setSize(Vec3i size) {
        this.size = size;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Door)) return false;
        Door region = (Door) o;
        return getName().equals(region.getName()) &&
                getPos().equals(region.getPos()) &&
                getSize().equals(region.getSize()) &&
                getTags().equals(region.getTags());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getPos(), getSize(), getTags());
    }

    public static class DoorDeserilizer implements JsonDeserializer<Door> {

        @Override
        public Door deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();

            String name = jsonObject.get("name").getAsString();
            String tags = jsonObject.get("tags").getAsString();
            JsonArray posArray = jsonObject.get("pos").getAsJsonArray();
            JsonArray sizeArray = jsonObject.get("size").getAsJsonArray();
            Vec3i pos = new Vec3i(posArray.get(0).getAsInt(), posArray.get(1).getAsInt(), posArray.get(2).getAsInt());
            Vec3i size = new Vec3i(sizeArray.get(0).getAsInt(), sizeArray.get(1).getAsInt(), sizeArray.get(2).getAsInt());

            return new Door(name, pos, size, tags);
        }
    }

}
