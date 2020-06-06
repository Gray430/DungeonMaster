package net.dungeonsworkshop.dungeonmaster.common.map.editor;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.dungeonsworkshop.dungeonmaster.common.map.objects.Level;
import net.dungeonsworkshop.dungeonmaster.util.Vec2i;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3i;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class FileLoader {

    public static JsonObject getJsonFromLocation(ResourceLocation resourceLocation) {
        try {
            String jsonString = IOUtils.toString(FileLoader.class.getResourceAsStream("/data/" + resourceLocation.getPath()), StandardCharsets.UTF_8);
            return new JsonParser().parse(jsonString).getAsJsonObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new JsonObject();
    }

    public static Level getLevelFromJson(JsonObject jsonObject) {
        return null;
    }

    public static Map<Vec2i, Integer> getRegionPlaneFromJson(JsonObject tileJson) {
        Map<Vec2i, Integer> regionPlaneMap = new HashMap<>();
        JsonArray sizeArray = tileJson.get("size").getAsJsonArray();
        Vec3i size = new Vec3i(sizeArray.get(0).getAsInt(), sizeArray.get(1).getAsInt(), sizeArray.get(2).getAsInt());

        try {
            byte[] decompressed = decompress(new Base64().decode(tileJson.get("region-plane").getAsString()));

            int it = 0;
            for (int z = 0; z < size.getZ(); z++)
                for (int x = 0; x < size.getX(); x++) {
                    regionPlaneMap.put(new Vec2i(x, z), (int) decompressed[it]);
                    it++;
                }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return regionPlaneMap;
    }

    public static void outputJson(JsonObject jsonObject) {
        try (FileWriter fileWriter = new FileWriter("../output/objectgroup.json")) {
            EditorManager.GSON.toJson(jsonObject, fileWriter);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        byte[] buffer = new byte[data.length];
        while (!inflater.finished()) {
            int count = inflater.inflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        outputStream.close();
        return outputStream.toByteArray();
    }

}
