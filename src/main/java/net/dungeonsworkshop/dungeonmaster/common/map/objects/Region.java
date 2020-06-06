package net.dungeonsworkshop.dungeonmaster.common.map.objects;

import com.google.gson.*;
import net.minecraft.util.math.Vec3i;

import java.lang.reflect.Type;
import java.util.Objects;

public class Region {

    private String name;
    private Vec3i pos;
    private Vec3i size;
    private String tags;
    private RegionType type;

    public Region(String name, Vec3i pos, Vec3i size, String tags, RegionType type) {
        this.name = name;
        this.pos = pos;
        this.size = size;
        this.tags = tags;
        this.type = type;
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

    public RegionType getType() {
        return type;
    }

    public void setType(RegionType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Region)) return false;
        Region region = (Region) o;
        return getName().equals(region.getName()) &&
                getPos().equals(region.getPos()) &&
                getSize().equals(region.getSize()) &&
                getTags().equals(region.getTags()) &&
                getType() == region.getType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getPos(), getSize(), getTags(), getType());
    }

    public enum RegionType {
        Trigger,
        PropArea;

        public static RegionType getRegionFromString(String name) {
            for (int i = 0; i < RegionType.values().length; i++) {
                if (name.equalsIgnoreCase(RegionType.values().toString())) return RegionType.values()[i];
            }
            return Trigger;
        }

    }

    public static class RegionDeserilizer implements JsonDeserializer<Region> {

        @Override
        public Region deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();

            String name = jsonObject.get("name").getAsString();
            String tags = jsonObject.get("tags").getAsString();
            JsonArray sizeArray = jsonObject.get("size").getAsJsonArray();
            JsonArray posArray = jsonObject.get("pos").getAsJsonArray();
            RegionType type = RegionType.getRegionFromString(jsonObject.get("type").getAsString());

            Vec3i size = new Vec3i(sizeArray.get(0).getAsInt(), sizeArray.get(1).getAsInt(), sizeArray.get(2).getAsInt());
            Vec3i pos = new Vec3i(posArray.get(0).getAsInt(), posArray.get(1).getAsInt(), posArray.get(2).getAsInt());

            return new Region(name, pos, size, tags, type);
        }
    }

}
