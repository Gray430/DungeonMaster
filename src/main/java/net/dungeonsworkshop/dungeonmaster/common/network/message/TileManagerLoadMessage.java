package net.dungeonsworkshop.dungeonmaster.common.network.message;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;

public class TileManagerLoadMessage {
    private final BlockPos pos;
    private final boolean load;

    public TileManagerLoadMessage(BlockPos pos, boolean load) {
        this.pos = pos;
        this.load = load;
    }

    public static void encode(TileManagerLoadMessage msg, PacketBuffer buf) {
        buf.writeBlockPos(msg.pos);
        buf.writeBoolean(msg.load);
    }

    public static TileManagerLoadMessage decode(PacketBuffer buf) {
        return new TileManagerLoadMessage(buf.readBlockPos(), buf.readBoolean());
    }

    public BlockPos getPos() {
        return pos;
    }

    public boolean toLoad() {
        return load;
    }
}