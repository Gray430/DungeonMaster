package net.dungeonsworkshop.dungeonmaster.common.map;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

import java.util.HashMap;

public class Tile {

    private Vec3i size;
    private java.util.Map<BlockPos, BlockState> blocks = new HashMap<>();

    public Tile(Vec3i size){
        this.size = size;
    }

    public Tile setBlock(BlockState blockState, BlockPos pos){
        if(this.blocks.containsKey(pos)){
            this.blocks.replace(pos, blockState);
        }else{
            this.blocks.put(pos, blockState);
        }
        return this;
    }

    public BlockState getBlockAtPos(BlockPos pos){ return this.blocks.getOrDefault(pos, Blocks.AIR.getDefaultState());}

    public static void buildTileAtPos(World world, BlockPos pos, Tile tile){
        for(int y = 0; y < tile.size.getY(); y++){
            for(int z = 0; z < tile.size.getZ(); z++){
                for(int x = 0; x < tile.size.getX(); x++){
                    BlockPos itPos = new BlockPos(x, y, z);
                    world.setBlockState(pos.add(itPos), tile.getBlockAtPos(itPos));
                }
            }
        }
    }

}
