package net.dungeonsworkshop.dungeonmaster.common.map.objects;

import com.google.gson.*;
import net.dungeonsworkshop.dungeonmaster.common.map.editor.BlockMapper;
import net.dungeonsworkshop.dungeonmaster.common.map.editor.FileLoader;
import net.dungeonsworkshop.dungeonmaster.util.BBlockState;
import net.dungeonsworkshop.dungeonmaster.util.LevelIdEnum;
import net.dungeonsworkshop.dungeonmaster.util.Vec2i;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import org.apache.commons.codec.binary.Base64;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tile {

    private String id;
    private Vec3i size;
    private String blocks = "";
    private BlockPos playerSpawnPos;
    private int y = 1;
    private Map<Vec2i, Integer> regionPlane = new HashMap<>();
    private Map<Vec2i, Integer> heightMap = new HashMap<>();
    private List<Door> doors = new ArrayList<>();
    private List<Region> regions = new ArrayList<>();

    public Tile(String id, Vec3i size, int y) {
        this.id = id;
        this.size = size;
        this.playerSpawnPos = new BlockPos(size.getX() / 2, size.getY() - 30, size.getZ() / 2);
        this.y = y;

        if (this.regionPlane.size() != size.getX() * size.getZ()) {
            for (int z = 0; z < size.getZ(); z++) {
                for (int x = 0; x < size.getX(); x++) {
                    this.regionPlane.putIfAbsent(new Vec2i(x, z), 0);
                }
            }
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public Vec3i getSize() {
        return size;
    }

    public void setSize(Vec3i size) {
        this.size = size;
    }

    public String getBlocks() {
        return blocks;
    }

    public void setBlocks(String blocks) {
        this.blocks = blocks;
    }

    public BlockPos getPlayerSpawnPos() {
        return playerSpawnPos;
    }

    public void setPlayerSpawnPos(BlockPos playerSpawnPos) {
        this.playerSpawnPos = playerSpawnPos;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setRegionPlane(Map<Vec2i, Integer> regionPlane) {
        this.regionPlane = regionPlane;
    }

    public Map<Vec2i, Integer> getHeightMap() {
        return heightMap;
    }

    public void setHeightMap(Map<Vec2i, Integer> heightMap) {
        this.heightMap = heightMap;
    }

    public List<Door> getDoors() {
        return doors;
    }

    public void setDoors(List<Door> doors) {
        this.doors = doors;
    }

    public List<Region> getRegions() {
        return regions;
    }

    public void setRegions(List<Region> regions) {
        this.regions = regions;
    }

    public List<BBlockState> getBlocksDecoded() {
        List<Integer> stateData = new ArrayList<>();
        List<BBlockState> blockList = new ArrayList<>();

        try {
            byte[] decompressed = FileLoader.decompress(new Base64().decode(blocks));

            for (int i = 0; i < decompressed.length - (size.getX() * size.getY() * size.getZ()); i++) {
                byte states = decompressed[(size.getX() * size.getY() * size.getZ()) + i];
                stateData.add((states >> 4) & 0xF);
                stateData.add(states & 0xF);
            }

            for (int i = 0; i < size.getX() * size.getY() * size.getZ(); i++) {
                blockList.add(i, new BBlockState(decompressed[i], stateData.get(i)));
                if (i == (260 + 148 * size.getX() + 107 * size.getX() * size.getZ())) {
                    System.out.println("Reached End of tile.");
                }
                if (i > decompressed.length - 1) {
                    System.out.println("Greater than tile size");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return blockList;
    }

    public Map<Vec2i, Integer> getRegionPlane() {
        return this.regionPlane;
    }

    public BlockState getBlockAtPos(BlockPos pos, LevelIdEnum resourcePack) {
        List<Integer> stateData = new ArrayList<>();

        try {
            byte[] decompressed = FileLoader.decompress(new Base64().decode(blocks));

            for (int i = 0; i < decompressed.length - (size.getX() * size.getY() * size.getZ()); i++) {
                byte states = decompressed[(size.getX() * size.getY() * size.getZ()) + i];
                stateData.add((states >> 4) & 0xF);
                stateData.add(states & 0xF);
            }

            int i = pos.getX() + pos.getZ() * size.getX() + pos.getY() * size.getX() * size.getZ();
            return BlockMapper.getJavaBlockState(new BBlockState(decompressed[i], stateData.get(i)), resourcePack);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BlockMapper.getJavaBlockState(new BBlockState(0, 0), resourcePack);
    }

    public BBlockState getUnmappedBlockAtPos(BlockPos pos) {
        List<Integer> stateData = new ArrayList<>();

        try {
            byte[] decompressed = FileLoader.decompress(new Base64().decode(blocks));

            for (int i = 0; i < decompressed.length - (size.getX() * size.getY() * size.getZ()); i++) {
                byte states = decompressed[(size.getX() * size.getY() * size.getZ()) + i];
                stateData.add((states >> 4) & 0xF);
                stateData.add(states & 0xF);
            }

            int i = pos.getX() + pos.getZ() * size.getX() + pos.getY() * size.getX() * size.getZ();
            return new BBlockState(decompressed[i], stateData.get(i));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new BBlockState(0, 0);
    }

    public JsonObject toJsonTile() throws IOException {
        int[] pos = new int[]{0, 0, 0};
        byte[] regionData = new byte[size.getX() * size.getZ()];
        byte[] heightData = new byte[size.getX() * size.getZ()];

        int it = 0;
        for (int z = 0; z < size.getZ(); z++) {
            for (int x = 0; x < size.getX(); x++) {
                //Read height map data from map to a byte array for compression
                if (heightData.length >= it && heightMap.containsKey(new Vec2i(x, z))) {
                    heightData[it] = (byte) heightMap.get(new Vec2i(x, z)).intValue();
                } else {
                    heightData[it] = (byte) 0;
                }

                //Read region data from map to a byte array for compression
                if (regionData.length >= it && regionPlane.containsKey(new Vec2i(x, z))) {
                    regionData[it] = (byte) regionPlane.get(new Vec2i(x, z)).intValue();
                } else {
                    regionData[it] = (byte) 0;
                }
                it++;
            }
        }

        String regionPlane = new String(new Base64().encode(FileLoader.compress(regionData)));
        String heightPlane = new String(new Base64().encode(FileLoader.compress(heightData)));

        JsonArray posArray = new JsonArray();
        posArray.add(0);
        posArray.add(0);
        posArray.add(0);

        JsonArray sizeArray = new JsonArray();
        sizeArray.add(size.getX());
        sizeArray.add(size.getY());
        sizeArray.add(size.getZ());

        Door playerSpawnDoor = new Door("", playerSpawnPos, new Vec3i(3, 1, 3), "");
        JsonArray doorArray = new JsonArray();
        doorArray.add(playerSpawnDoor.toJson());

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("blocks", blocks);
        jsonObject.add("doors", doorArray);
        jsonObject.addProperty("height-plane", heightPlane);
        jsonObject.addProperty("id", id);
        jsonObject.addProperty("is-leaky", false);
        jsonObject.add("pos", posArray);
        jsonObject.addProperty("region-plane", regionPlane);
        jsonObject.addProperty("region-y-plane", heightPlane);
        jsonObject.add("size", sizeArray);
        jsonObject.addProperty("tags", "");
        jsonObject.addProperty("y", 1);
        return jsonObject;
    }

    public void setRegionPlaneDataAtPos(Vec2i vec2i, int regionPlaneData){
        regionPlane.put(vec2i, Math.max(0, Math.min(4, regionPlaneData)));
    }

    public void buildAtPos(World world, BlockPos pos, LevelIdEnum resourcePack) {
        BlockPos position = pos.add(0, 1, 0);
        List<BBlockState> bBlocks = getBlocksDecoded();
        BlockPos.Mutable actualPos = new BlockPos.Mutable();

        for (int y = 0; y < this.size.getY(); y++) {
            for (int z = 0; z < this.size.getZ(); z++) {
                for (int x = 0; x < this.size.getX(); x++) {
                    int i = x + z * size.getX() + y * size.getX() * size.getZ();
                    actualPos.setPos(position.getX() + x, position.getY() + y, position.getZ() + z);
                    if (bBlocks.get(i).getBlockId() != 0) {
                        world.setBlockState(actualPos, BlockMapper.getJavaBlockState(bBlocks.get(i), resourcePack));
                    } else {
                        world.setBlockState(actualPos, Blocks.AIR.getDefaultState());
                    }
                }
            }
        }
    }

    public static CompoundNBT SerializeToNBT(Tile tile){
        CompoundNBT tileNBT = new CompoundNBT();

        int it = 0;
        int[] heightMapData = new int[tile.getSize().getX() * tile.getSize().getZ()];
        for(int z = 0; z < tile.getSize().getZ(); z++){
            for(int x = 0; x < tile.getSize().getX(); x++){
                if(tile.getHeightMap().get(new Vec2i(x, z)) != null){
                    heightMapData[it] = tile.getHeightMap().get(new Vec2i(x, z));
                }else{
                    heightMapData[it] = 0;
                }
                it++;
            }
        }

        it = 0;
        int[] regionPlaneData = new int[tile.getSize().getX() * tile.getSize().getZ()];
        for(int z = 0; z < tile.getSize().getZ(); z++){
            for(int x = 0; x < tile.getSize().getX(); x++){
                if(tile.getRegionPlane().get(new Vec2i(x, z)) != null){
                    regionPlaneData[it] = tile.getRegionPlane().get(new Vec2i(x, z));
                }else{
                    regionPlaneData[it] = 0;
                }
                it++;
            }
        }

        tileNBT.putString("id", tile.id);
        tileNBT.putIntArray("pos", new int[]{0, 0 ,0});
        tileNBT.putIntArray("size", new int[]{tile.getSize().getX(), tile.getSize().getY(), tile.getSize().getZ()});
        tileNBT.putIntArray("heightMap", heightMapData);
        tileNBT.putIntArray("region-plane", regionPlaneData);
        tileNBT.putInt("y", tile.getY());

        return tileNBT;
    }

    public static Tile DeserializeFromNBT(CompoundNBT tileNBT){

        String id = tileNBT.getString("id");
        int[] posArray = tileNBT.getIntArray("pos");
        int[] sizeArray = tileNBT.getIntArray("size");
        int[] heightMapData = tileNBT.getIntArray("heightMap");
        int[] regionPlaneData = tileNBT.getIntArray("region-plane");
        int y = tileNBT.getInt("y");

        BlockPos pos = new BlockPos(posArray[0], posArray[1], posArray[2]);
        BlockPos size = new BlockPos(sizeArray[0], sizeArray[1], sizeArray[2]);

        int it = 0;
        Map<Vec2i, Integer> heightMap = new HashMap<>();
        for(int z = 0; z < size.getZ(); z++){
            for(int x = 0; x < size.getX(); x++){
                heightMap.put(new Vec2i(x, z), heightMapData[it]);
                it++;
            }
        }

        it = 0;
        Map<Vec2i, Integer> regionPlaneMap = new HashMap<>();
        for(int z = 0; z < size.getZ(); z++){
            for(int x = 0; x < size.getX(); x++){
                regionPlaneMap.put(new Vec2i(x, z), regionPlaneData[it]);
                it++;
            }
        }

        Tile tile = new Tile(id, size, y);
        tile.setHeightMap(heightMap);
        tile.setRegionPlane(regionPlaneMap);

        return tile;
    }

    public static class TileDeserializer implements JsonDeserializer<Tile> {

        @Override
        public Tile deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();

            String id = jsonObject.get("id").getAsString();
            JsonArray sizeArray = jsonObject.get("size").getAsJsonArray();
            JsonArray posArray = jsonObject.get("pos").getAsJsonArray();
            Vec3i size = new Vec3i(sizeArray.get(0).getAsInt(), sizeArray.get(1).getAsInt(), sizeArray.get(2).getAsInt());
            Vec3i pos = new Vec3i(posArray.get(0).getAsInt(), posArray.get(1).getAsInt(), posArray.get(2).getAsInt());
            int y = jsonObject.get("y").getAsInt();

            Tile tile = new Tile(id, size, y);

            List<Region> regions = new ArrayList<>();
            if (jsonObject.has("regions")){
                jsonObject.getAsJsonArray("regions").forEach(jsonElement -> {
                    regions.add(context.deserialize(jsonElement, Region.class));
                });
            }

            List<Door> doors = new ArrayList<>();
            if (jsonObject.has("doors")) {
                jsonObject.getAsJsonArray("doors").forEach(jsonElement -> {
                    doors.add(context.deserialize(jsonElement, Door.class));
                });
            }

            Map<Vec2i, Integer> regionPlaneMap = new HashMap<>();
            try {
                byte[] decompressed = FileLoader.decompress(new Base64().decode(jsonObject.get("region-plane").getAsString()));

                int it = 0;
                for (int z = 0; z < size.getZ(); z++)
                    for (int x = 0; x < size.getX(); x++) {
                        regionPlaneMap.put(new Vec2i(x, z), (int) decompressed[it]);
                        it++;
                    }
            } catch (Exception e) {
                e.printStackTrace();
            }

            tile.setRegions(regions);
            tile.setDoors(doors);
            tile.setRegionPlane(regionPlaneMap);
            tile.setBlocks(jsonObject.get("blocks").getAsString());

            return tile;
        }
    }

}
