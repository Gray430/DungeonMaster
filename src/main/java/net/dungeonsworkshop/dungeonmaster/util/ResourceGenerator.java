package net.dungeonsworkshop.dungeonmaster.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ResourceGenerator {

    public static final String DEFAULT_BLOCKSTATE = "{\"variants\": {\"\": { \"model\": \"dungeonmaster:block/%s/%s\" }}}";
    public static final String SINGLE_TEXTURE_BLOCK  = "{\"parent\": \"block/cube_all\", \"textures\": { \"all\": \"dungeonmaster:block/%s\" }}";

//    public static void generateBlockState(){
//        try {
//            JsonObject blockState = JsonParser.parseString(DEFAULT_BLOCKSTATE).getAsJsonObject();
//
//            FileOutputStream fileOutputStream = new FileOutputStream(new File(""));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

}
