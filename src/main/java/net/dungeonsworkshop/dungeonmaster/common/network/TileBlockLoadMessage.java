package net.dungeonsworkshop.dungeonmaster.common.network;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;

public class TileBlockLoadMessage {
    private final BlockPos pos;
    private final BlockPos size;
    private final boolean load;

    public TileBlockLoadMessage(BlockPos pos, BlockPos size, boolean load) {
        this.pos = pos;
        this.size = size;
        this.load = load;
    }

    public static void encode(TileBlockLoadMessage msg, PacketBuffer buf) {
        buf.writeBlockPos(msg.pos);
        buf.writeBlockPos(msg.size);
        buf.writeBoolean(msg.load);
    }

    public static TileBlockLoadMessage decode(PacketBuffer buf) {
        return new TileBlockLoadMessage(buf.readBlockPos(), buf.readBlockPos(), buf.readBoolean());
    }

    public BlockPos getPos() {
        return pos;
    }

    public BlockPos getSize(){
        return size;
    }

    public boolean toLoad() {
        return load;
    }
}