package net.dungeonsworkshop.dungeonmaster.common.map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.dungeonsworkshop.dungeonmaster.DungeonMaster;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.registries.GameData;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import javax.annotation.Nullable;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import static net.dungeonsworkshop.dungeonmaster.common.map.BlockMapper.loadBlockMappings;

public class MapHelper {

    public static Tile loadTile(String objectGroup, String tileId){
        loadBlockMappings(objectGroup);
        byte[] decompressed = new byte[0];
        List<Integer> stateData = new ArrayList<>();
        JsonObject tileJson = getTileJson(objectGroup, tileId);
        JsonArray sizeArray = tileJson.get("size").getAsJsonArray();
        Vec3i size = new Vec3i(
                sizeArray.get(0).getAsInt(),
                sizeArray.get(1).getAsInt(),
                sizeArray.get(2).getAsInt()
        );
        Tile tile = new Tile(size);

        try{
            decompressed = decompress(new Base64().decode(tileJson.get("blocks").getAsString()));

            for (int i = 0; i < decompressed.length - (size.getX() * size.getY() * size.getZ()); i++)
            {
                byte states = decompressed[(size.getX() * size.getY() * size.getZ()) + i];
                stateData.add((states >> 4) & 0xF);
                stateData.add(states & 0xF);
            }

            int it = 0;
            for (int y = 0; y < size.getY(); y++)
                for (int z = 0; z < size.getZ(); z++)
                    for (int x = 0; x < size.getX(); x++) {
                        byte blockId = decompressed[it];
                        int blockState = stateData.get(it);
//                        if(x == 57 && y == 49 && z == 157){
//                            //57 49 157
//                            System.out.println("ID: " + blockId + " | " + "State: " + blockState);
//                        }
                        if(blockId != 0x00){
                            BlockState block = BlockMapper.getJavaBlockState(blockId, blockState);
                            tile.setBlock(block, new BlockPos(x, y, z));
                        }
                        it++;
                    }
        }catch (IOException | DataFormatException e){e.printStackTrace();}

        return tile;
    }

    public static String getInfoAtPos(String objectGroup, String tileId, BlockPos pos){
        byte[] decompressed = new byte[0];
        List<Integer> stateData = new ArrayList<>();
        JsonObject tileJson = getTileJson(objectGroup, tileId);
        JsonArray sizeArray = tileJson.get("size").getAsJsonArray();
        Vec3i size = new Vec3i(
                sizeArray.get(0).getAsInt(),
                sizeArray.get(1).getAsInt(),
                sizeArray.get(2).getAsInt()
        );

        try{
            decompressed = decompress(new Base64().decode(tileJson.get("blocks").getAsString()));

            for (int i = 0; i < decompressed.length - (size.getX() * size.getY() * size.getZ()); i++)
            {
                byte states = decompressed[(size.getX() * size.getY() * size.getZ()) + i];
                stateData.add((states >> 4) & 0xF);
                stateData.add(states & 0xF);
            }

            int it = pos.getX() + pos.getZ() * size.getX() + pos.getY() * size.getX() * size.getZ();
            byte blockId = decompressed[it];
            int blockState = stateData.get(it);

            return "Block ID: " + blockId + " Block State: " + blockState;
        }catch (IOException | DataFormatException e){e.printStackTrace();}
        return "Error decompressing";
    }

    @Nullable
    public static JsonObject getTileJson(String objectgroupName, String tileId){
        try {
            String jsonString = IOUtils.toString(MapHelper.class.getResourceAsStream("/data/" + DungeonMaster.MOD_ID + "/lovika/objectgroups/" + objectgroupName + "/objectgroup.json"), StandardCharsets.UTF_8);
            JsonArray objects = JsonParser.parseString(jsonString).getAsJsonObject().get("objects").getAsJsonArray();
            for(JsonElement element : objects){
                JsonObject object = element.getAsJsonObject();
                if(object.get("id").getAsString().equalsIgnoreCase(tileId)){
                    return object;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] compress(byte[] data) throws IOException {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        deflater.finish();
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer); // returns the generated code... index
            outputStream.write(buffer, 0, count);
        }
        outputStream.close();
        return outputStream.toByteArray();
    }
    public static byte[] decompress(byte[] data) throws IOException, DataFormatException {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!inflater.finished()) {
            int count = inflater.inflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        outputStream.close();
        return outputStream.toByteArray();
    }

}
