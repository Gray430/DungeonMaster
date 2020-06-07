package net.dungeonsworkshop.dungeonmaster.util;

import net.minecraft.block.BlockState;

import java.util.Map;

public enum LevelIdEnum {
    Empty(""),
    Vanilla("vanilla"),
    SquidCoast("SquidCoast");

    private final String id;
    private Map<BBlockState, BlockState> bedrockToJava;
    private Map<BlockState, BBlockState> javaToBedrock;

    LevelIdEnum(String id) {
        this.id = id;
    }

    public static LevelIdEnum getFromID(String string) {
        for (int i = 0; i < LevelIdEnum.values().length; i++) {
            if (LevelIdEnum.values()[i].id.equalsIgnoreCase(string)) {
                return LevelIdEnum.values()[i];
            }
        }
        return Empty;
    }

    public Map<BBlockState, BlockState> getBedrockToJava() {
        return bedrockToJava;
    }

    public void setBedrockToJava(Map<BBlockState, BlockState> bedrockToJava) {
        this.bedrockToJava = bedrockToJava;
    }

    public Map<BlockState, BBlockState> getJavaToBedrock() {
        return javaToBedrock;
    }

    public void setJavaToBedrock(Map<BlockState, BBlockState> javaToBedrock) {
        this.javaToBedrock = javaToBedrock;
    }

    public String getId() {
        return id;
    }
}
