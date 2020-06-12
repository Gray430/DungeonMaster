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
