package net.dungeonsworkshop.dungeonmaster.common.map.editor;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.mojang.brigadier.StringReader;
import net.dungeonsworkshop.dungeonmaster.DungeonMaster;
import net.dungeonsworkshop.dungeonmaster.common.init.DungeonBlocks;
import net.dungeonsworkshop.dungeonmaster.util.ResourcePackEnum;
import net.minecraft.block.BlockState;
import net.minecraft.command.arguments.BlockStateParser;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class BlockMapper {

    private static final Map<Pair<Integer, Integer>, BlockState> SC_ID_MAP = new HashMap<>();

    public static void loadBlockMappings(ResourcePackEnum levelId) {
        SC_ID_MAP.clear();
        try (InputStream inputStream = BlockMapper.class.getResourceAsStream("/data/" + DungeonMaster.MOD_ID + "/mapping/" + levelId.getId() + ".json")) {
            JsonArray mappingArray = new JsonParser().parse(IOUtils.toString(inputStream, StandardCharsets.UTF_8)).getAsJsonArray();

            for (int i = 0; i < mappingArray.size(); i++) {
                try {
                    String mapping = mappingArray.get(i).getAsString();
                    String[] javaBlockState = mapping.split(";");
                    String[] idState = javaBlockState[0].split(",");
                    javaBlockState[1] = javaBlockState[1].replace("LEVELNAME", levelId.getId().toLowerCase());
                    SC_ID_MAP.put(new ImmutablePair<Integer, Integer>(Integer.parseInt(idState[0]), Integer.parseInt(idState[1])), new BlockStateParser(new StringReader(javaBlockState[1]), false).parse(false).getState());
                } catch (Exception e) {
                    System.out.println("Mapping error at entry " + i + 1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static BlockState getJavaBlockState(int id, int state) {
        return SC_ID_MAP.computeIfAbsent(new ImmutablePair<>(id, state), __ -> DungeonBlocks.UNKNOWN_BLOCK.get().getDefaultState());
    }
}