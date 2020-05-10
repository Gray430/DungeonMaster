package net.dungeonsworkshop.dungeonmaster.common.map;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.HashMap;

public class Tile {

    private Vec3i size;
    private java.util.Map<BlockPos, Block> blocks = new HashMap<>();

    public Tile(Vec3i size){
        this.size = size;
    }

    public Tile setBlock(Block block, BlockPos pos){
        if(this.blocks.containsKey(pos)){
            this.blocks.replace(pos, block);
        }else{
            this.blocks.put(pos, block);
        }
        return this;
    }

    public Block getBlockAtPos(BlockPos pos){ return this.blocks.getOrDefault(pos, Blocks.AIR);}

    public static void buildTileAtPos(World world, BlockPos pos, Tile tile){
        for(int y = 0; y < tile.size.getY(); y++){
            for(int z = 0; z < tile.size.getZ(); z++){
                for(int x = 0; x < tile.size.getX(); x++){
                    BlockPos itPos = new BlockPos(x, y, z);
//                    System.out.println("Set block at " + itPos.toString() + " to " + tile.getBlockAtPos(itPos).getDefaultState().toString());
                    world.setBlockState(pos.add(itPos), tile.getBlockAtPos(itPos).getDefaultState());
                }
            }
        }
    }

}
