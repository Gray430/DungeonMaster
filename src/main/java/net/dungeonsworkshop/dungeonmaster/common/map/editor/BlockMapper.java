package net.dungeonsworkshop.dungeonmaster.common.map.editor;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.brigadier.StringReader;
import net.dungeonsworkshop.dungeonmaster.DungeonMaster;
import net.dungeonsworkshop.dungeonmaster.common.init.DungeonBlocks;
import net.dungeonsworkshop.dungeonmaster.util.BBlockState;
import net.dungeonsworkshop.dungeonmaster.util.LevelIdEnum;
import net.minecraft.block.BlockState;
import net.minecraft.command.arguments.BlockStateParser;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.StairsShape;
import net.minecraftforge.common.Tags;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class BlockMapper {

    public static void loadBlockMappings() {
        try (InputStream inputStream = BlockMapper.class.getResourceAsStream("/data/" + DungeonMaster.MOD_ID + "/mapping/mappings.json")) {
            JsonObject mappingJson = new JsonParser().parse(IOUtils.toString(inputStream, StandardCharsets.UTF_8)).getAsJsonObject();
            for (Map.Entry<String, JsonElement> jsonEntry : mappingJson.entrySet()) {
                Map<BBlockState, BlockState> bedrockToJavaMap = new HashMap<>();
                Map<BlockState, BBlockState> javaToBedrockMap = new HashMap<>();
                JsonArray mappingArray = jsonEntry.getValue().getAsJsonArray();

                for (int i = 0; i < mappingArray.size(); i++) {
                    try {
                        String mapping = mappingArray.get(i).getAsString();
                        String[] javaBlockState = mapping.split(";");
                        String[] idState = javaBlockState[0].split(",");
                        javaBlockState[1] = javaBlockState[1].replace("LEVELNAME", jsonEntry.getKey().toLowerCase());

                        BBlockState bedrockState = new BBlockState(Integer.parseInt(idState[0]), Integer.parseInt(idState[1]));
                        BlockState javaState = new BlockStateParser(new StringReader(javaBlockState[1]), false).parse(false).getState();

                        if (!bedrockToJavaMap.containsKey(bedrockState)) {
                            bedrockToJavaMap.put(bedrockState, javaState);
                        }
                        if (!javaToBedrockMap.containsKey(javaState)) {
                            javaToBedrockMap.put(javaState, bedrockState);
                        } else {
                            System.out.println("found duplicate entry for " + javaBlockState[1]);
                        }

                    } catch (Exception e) {
                        System.out.println("Mapping error at entry " + i + 1);
                    }
                }

                System.out.println("Loaded Mappings for " + jsonEntry.getKey());
                LevelIdEnum levelId = LevelIdEnum.getFromID(jsonEntry.getKey());
                levelId.setBedrockToJava(bedrockToJavaMap);
                levelId.setJavaToBedrock(javaToBedrockMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static BlockState getJavaBlockState(int id, int state, LevelIdEnum genericLevelID) {
        return genericLevelID.getBedrockToJava().computeIfAbsent(new BBlockState(id, state), empty -> DungeonBlocks.UNKNOWN_BLOCK.get().getDefaultState());
    }

    public static BlockState getJavaBlockState(BBlockState bBlockState, LevelIdEnum genericLevelID) {
        return genericLevelID.getBedrockToJava().computeIfAbsent(bBlockState, empty -> DungeonBlocks.UNKNOWN_BLOCK.get().getDefaultState());
    }

    public static BBlockState getBedrockBlockState(BlockState blockState, LevelIdEnum genericLevelID) {
        BBlockState bBlockState = genericLevelID.getJavaToBedrock().computeIfAbsent(blockState, empty -> new BBlockState(0, 0));
        if (blockState.getBlock().getTags().contains(Tags.Blocks.FENCES.getId()) || blockState.getBlock().getRegistryName().getPath().contains("wall") || blockState.getBlock().getRegistryName().getPath().contains("leaves")) {
            bBlockState = genericLevelID.getJavaToBedrock().computeIfAbsent(blockState.getBlock().getDefaultState(), empty -> new BBlockState(0, 0));
        } else if (blockState.getBlock().getRegistryName().getPath().contains("stair") && blockState.get(BlockStateProperties.STAIRS_SHAPE) != StairsShape.STRAIGHT) {
            bBlockState = genericLevelID.getJavaToBedrock().computeIfAbsent(blockState.getBlock().getDefaultState().with(BlockStateProperties.HORIZONTAL_FACING, blockState.get(BlockStateProperties.HORIZONTAL_FACING)), empty -> new BBlockState(0, 0));
        }
        return bBlockState;
    }

}