package net.dungeonsworkshop.dungeonmaster.common.entity;

import net.dungeonsworkshop.dungeonmaster.common.init.DungeonEntities;
import net.dungeonsworkshop.dungeonmaster.common.map.editor.BlockMapper;
import net.dungeonsworkshop.dungeonmaster.common.map.editor.EditorManager;
import net.dungeonsworkshop.dungeonmaster.common.map.editor.FileLoader;
import net.dungeonsworkshop.dungeonmaster.common.map.objects.Tile;
import net.dungeonsworkshop.dungeonmaster.common.network.DungeonsMessageHandler;
import net.dungeonsworkshop.dungeonmaster.common.network.message.TileManagerSyncMessage;
import net.dungeonsworkshop.dungeonmaster.common.network.message.TileManagerHandleMessage;
import net.dungeonsworkshop.dungeonmaster.util.BBlockState;
import net.dungeonsworkshop.dungeonmaster.util.LevelIdEnum;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.fml.network.PacketDistributor;
import org.apache.commons.codec.binary.Base64;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class TileBlockTE extends TileEntity {

    private String objectGroupId = "";
    private String tileId = "";
    private Tile loadedTile = new Tile("", new Vec3i(5, 5, 5), 0);

    public TileBlockTE() {
        super(DungeonEntities.TILE_BLOCK.get());
    }

    public void sync() {
        this.markDirty();
        DungeonsMessageHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new TileManagerSyncMessage(this.getPos(), loadedTile));
    }

    @Override
    public void onLoad() {
        if (!world.isRemote()) {
            EditorManager.loadTileBlock(this.pos, false);
        }
    }

    @Override
    public void remove() {
        super.remove();
        if(!world.isRemote()){
            EditorManager.unloadTileBlock(this.pos, false);
        }
    }

    public boolean importTile() {
        if(!world.isRemote()){
            Tile tile = EditorManager.getTileFromId("lobby", tileId);
            if (tile != null) {
                loadedTile = tile;
                tile.buildAtPos(this.getWorld(), this.getPos(), LevelIdEnum.SquidCoast);
                return true;
            } else {
                return false;
            }
        }else{
            DungeonsMessageHandler.INSTANCE.send(PacketDistributor.SERVER.noArg(), new TileManagerSyncMessage(this.getPos(), loadedTile));
            DungeonsMessageHandler.INSTANCE.send(PacketDistributor.SERVER.noArg(), new TileManagerHandleMessage(this.getPos()));
        }
        return true;
    }

    public void exportTile() {
        try {
            Vec3i finalSize = this.loadedTile.getSize();
            List<Integer> idList = new ArrayList<>();
            List<Integer> stateList = new ArrayList<>();

            List<Byte> byteHalfs = new ArrayList<>();

            BlockPos.Mutable currentPos = new BlockPos.Mutable();
            for (int y = 0; y < finalSize.getY(); y++) {
                for (int z = 0; z < finalSize.getZ(); z++) {
                    for (int x = 0; x < finalSize.getX(); x++) {
                        //Calculates the position along a 1D array from x y z coordinates
                        int i = x + z * finalSize.getX() + y * finalSize.getX() * finalSize.getZ();
                        //adds 1 to y because this tile entity is 1 block below the tile
                        currentPos.setPos(pos.getX() + x, pos.getY() + y + 1, pos.getZ() + z);
                        BBlockState currentBlock = BlockMapper.getBedrockBlockState(world.getBlockState(currentPos), LevelIdEnum.SquidCoast);
                        idList.add(currentBlock.getBlockId());
                        byteHalfs.add((byte) currentBlock.getBlockState());
                    }
                }
            }

            for (int i = 0; i < byteHalfs.size(); i++) {
                Byte finalState;
                byte firstHalf = (byte) (byteHalfs.get(i).byteValue() << 4);
                if (i + 1 <= byteHalfs.size()) {
                    i++;
                    finalState = (byte) (firstHalf | byteHalfs.get(i));
                } else {
                    finalState = (byte) (firstHalf | 0xF);
                }
                stateList.add(finalState.intValue());
            }

            List<Integer> blocksList = idList;
            blocksList.addAll(stateList);

            //convert the int list to bytes for compression
            byte[] exportList = new byte[blocksList.size()];
            for (int i = 0; i < blocksList.size(); i++) {
                exportList[i] = blocksList.get(i).byteValue();
            }

            Tile exportTile = new Tile(tileId, finalSize, 1);
            exportTile.setRegionPlane(loadedTile.getRegionPlane());
            exportTile.setHeightMap(loadedTile.getHeightMap());
            exportTile.setBlocks(new String(new Base64().encode(FileLoader.compress(exportList))));

            FileLoader.outputJson(exportTile.toJsonTile());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //TODO move region data serialization to separate packet
    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.putString("tileId", tileId);
        return super.write(compound);
    }

    @Override
    public void read(CompoundNBT compound) {
        tileId = compound.getString("tileId");
        super.read(compound);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        this.read(pkt.getNbtCompound());
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.pos, 0, this.getUpdateTag());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return INFINITE_EXTENT_AABB;
    }

    public String getTileId() {
        return tileId;
    }

    public void setTileId(String tileId) {
        this.tileId = tileId;
    }

    public Tile getLoadedTile() {
        return loadedTile;
    }

    public void setLoadedTile(Tile loadedTile) {
        this.loadedTile = loadedTile;
    }
}
