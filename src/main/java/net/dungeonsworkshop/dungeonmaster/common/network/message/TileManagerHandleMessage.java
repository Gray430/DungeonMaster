package net.dungeonsworkshop.dungeonmaster.common.network.message;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;

public class TileManagerHandleMessage {

    private BlockPos pos;

    public TileManagerHandleMessage(BlockPos pos) {
        this.pos = pos;
    }

    public static void encode(TileManagerHandleMessage msg, PacketBuffer buf) {
        buf.writeBlockPos(msg.getPos());
    }

    public static TileManagerHandleMessage decode(PacketBuffer buf) {
        return new TileManagerHandleMessage(buf.readBlockPos());
    }

    public BlockPos getPos() {
        return pos;
    }

}
