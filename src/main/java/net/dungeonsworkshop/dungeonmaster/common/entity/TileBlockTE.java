package net.dungeonsworkshop.dungeonmaster.common.entity;

import io.github.ocelot.common.valuecontainer.StringValueContainerEntry;
import io.github.ocelot.common.valuecontainer.ValueContainer;
import io.github.ocelot.common.valuecontainer.ValueContainerEntry;
import io.github.ocelot.common.valuecontainer.VectorValueContainerEntry;
import net.dungeonsworkshop.dungeonmaster.common.init.DungeonBlocks;
import net.dungeonsworkshop.dungeonmaster.common.init.DungeonEntities;
import net.dungeonsworkshop.dungeonmaster.common.map.editor.BlockMapper;
import net.dungeonsworkshop.dungeonmaster.common.map.editor.EditorManager;
import net.dungeonsworkshop.dungeonmaster.common.map.editor.FileLoader;
import net.dungeonsworkshop.dungeonmaster.common.map.objects.Tile;
import net.dungeonsworkshop.dungeonmaster.util.BBlockState;
import net.dungeonsworkshop.dungeonmaster.util.LevelIdEnum;
import net.dungeonsworkshop.dungeonmaster.util.Vec2i;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import org.apache.commons.codec.binary.Base64;

import javax.annotation.Nullable;
import java.util.*;

public class TileBlockTE extends TileEntity implements ValueContainer {

    private Tile loadedTile;
    private String id = "";
    private Vec3d size = new Vec3d(5, 5, 5);
    private int y = 1;

    public TileBlockTE() {
        super(DungeonEntities.TILE_BLOCK.get());
    }

    @Override
    public void onLoad() {
        if (world.isRemote()) {
            EditorManager.TILE_MANAGERS.add(this);
        }
    }

    public void importTile() {
        loadedTile = EditorManager.instance().getTileByID(id);
        if (loadedTile != null) {
            loadedTile.buildAtPos(this.world, this.pos, LevelIdEnum.SquidCoast);
        } else {
            System.out.println("Could not find tile by the id " + this.id);
        }
    }

    public void exportTile() {
        try {
            Vec3i finalSize = this.getSize();
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

            //##### Handle Region-Plane #####
            Map<Vec2i, Integer> regionPlaneData = new HashMap<>();
            BlockPos.Mutable regionPlanePos = new BlockPos.Mutable();
            for (int z = 0; z < finalSize.getX(); z++) {
                for (int x = 0; x < finalSize.getZ(); x++) {
                    for(int y = world.getMaxHeight(); 0 < y; y--){
                        regionPlanePos.setPos(x, y, z);
                        if(world.getBlockState(regionPlanePos).getBlock() == DungeonBlocks.REGION_PLANE_BLOCK.get()){
                            regionPlaneData.put(new Vec2i(x, z), 1);
                            break;
                        }else if(y <= 0){
                            regionPlaneData.put(new Vec2i(x, z), 1);
                        }
                    }
                }
            }

            Tile exportTile = new Tile(id, finalSize, 1);
            exportTile.setRegionPlane(regionPlaneData);
            exportTile.setBlocks(new String(new Base64().encode(FileLoader.compress(exportList))));

            FileLoader.outputJson(exportTile.toJsonTile());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.putString("tileId", id);
        compound.putInt("sizeX", (int) size.getX());
        compound.putInt("sizeY", (int) size.getY());
        compound.putInt("sizeZ", (int) size.getZ());
        compound.putInt("Y", y);
        return super.write(compound);
    }

    @Override
    public void read(CompoundNBT compound) {
        id = compound.getString("tileId");
        size = new Vec3d(compound.getInt("sizeX"), compound.getInt("sizeY"), compound.getInt("sizeZ"));
        y = compound.getInt("Y");
        super.read(compound);
    }

    @Override
    public void getEntries(World world, BlockPos blockPos, List<ValueContainerEntry<?>> entries) {
        entries.add(new StringValueContainerEntry(new StringTextComponent("Tile ID"), "id", id));
        entries.add(new VectorValueContainerEntry(new StringTextComponent("Size"), "size", size));
        //entries.add(new IntValueContainerEntry(new StringTextComponent("Y"), "y", y));
    }

    @Override
    public void readEntries(World world, BlockPos blockPos, Map<String, ValueContainerEntry<?>> map) {
        if (map.containsKey("id")) {
            String id = map.get("id").getValue();
            loadedTile = EditorManager.instance().getTileByID(id);
            if (loadedTile != null) {
                this.setSize(new Vec3d(loadedTile.getSize().getX(), loadedTile.getSize().getY(), loadedTile.getSize().getZ()));
                //this.setY(loadedTile.getY());
            }
            this.id = id;
        }
        if (map.containsKey("size")) {
            Vec3d size = map.get("size").getValue();
            this.size = size;
        }
        if (map.containsKey("y")) {
            int y = map.get("y").getValue();
            this.y = y;
        }

        this.sync();
    }

    @Override
    public Optional<ITextComponent> getTitle(World world, BlockPos blockPos) {
        return Optional.empty();
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
    public AxisAlignedBB getRenderBoundingBox() {
        return INFINITE_EXTENT_AABB;
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    public void sync() {
        this.markDirty();
        if (world != null)
            world.notifyBlockUpdate(pos, this.getBlockState(), this.getBlockState(), Constants.BlockFlags.DEFAULT);
    }

    public void setSize(Vec3d size) {
        this.size = size;
        this.sync();
    }

    public void setY(int y) {
        this.y = y;
        this.sync();
    }

    public Vec3i getSize() {
        return new Vec3i(size.getX(), size.getY(), size.getZ());
    }

    public int getY() {
        return y;
    }
}
