package net.dungeonsworkshop.dungeonmaster.util;

import java.util.Objects;

public class BBlockState {

    private final int blockId;
    private int blockState;

    public BBlockState(int blockId, int blockState) {
        this.blockId = blockId;
        this.blockState = blockState;
    }

    public int getBlockId() {
        return blockId;
    }

    public int getBlockState() {
        return blockState;
    }

    public void setBlockState(int blockState) {
        this.blockState = blockState;
    }

    @Override
    public String toString() {
        return "BBlockState{" + "blockId='" + blockId + "', blockState='" + blockState + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BBlockState)) return false;
        BBlockState that = (BBlockState) o;
        return blockId == that.blockId &&
                getBlockState() == that.getBlockState();
    }

    @Override
    public int hashCode() {
        return Objects.hash(blockId, getBlockState());
    }
}
