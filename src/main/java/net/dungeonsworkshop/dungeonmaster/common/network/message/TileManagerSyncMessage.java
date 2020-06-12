package net.dungeonsworkshop.dungeonmaster.common.network.message;

import net.dungeonsworkshop.dungeonmaster.common.map.objects.Tile;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;

public class TileManagerSyncMessage {

    private BlockPos pos;
    private Tile tile;
    private SyncType syncType;

    public TileManagerSyncMessage(BlockPos pos, Tile tile, SyncType syncType) {
        this.pos = pos;
        this.tile = tile;
        this.syncType = syncType;
    }

    public TileManagerSyncMessage(BlockPos pos, SyncType syncType) {
        this.pos = pos;
        this.tile = null;
        this.syncType = syncType;
    }

    public static void encode(TileManagerSyncMessage msg, PacketBuffer buf) {
        buf.writeEnumValue(msg.syncType);
        buf.writeBlockPos(msg.getPos());

        if(msg.syncType != SyncType.UNLOAD){
            buf.writeCompoundTag(Tile.SerializeToNBT(msg.getTile()));
        }
    }

    public static TileManagerSyncMessage decode(PacketBuffer buf) {
        SyncType syncType = buf.readEnumValue(SyncType.class);
        BlockPos pos = buf.readBlockPos();

        if(syncType != SyncType.UNLOAD) {
            Tile tile = Tile.DeserializeFromNBT(buf.readCompoundTag());
            return new TileManagerSyncMessage(pos, tile, SyncType.LOAD);
        }

        return new TileManagerSyncMessage(pos, SyncType.UNLOAD);
    }

    public Tile getTile() {
        return tile;
    }

    public BlockPos getPos() {
        return pos;
    }

    public SyncType getSyncType(){
        return syncType;
    }

    public enum SyncType{
        SERVER,
        LOAD,
        UNLOAD
    }

}
