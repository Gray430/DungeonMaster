package net.dungeonsworkshop.dungeonmaster.common.blocks;

import net.dungeonsworkshop.dungeonmaster.common.entity.TileBlockTE;
import net.dungeonsworkshop.dungeonmaster.common.network.DungeonsMessageHandler;
import net.dungeonsworkshop.dungeonmaster.common.network.message.DisplayScreenMessage;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.annotation.Nullable;

public class TileBlock extends Block {

    public TileBlock(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if (player instanceof ServerPlayerEntity && !worldIn.isRemote() && worldIn.getTileEntity(pos) instanceof TileBlockTE) {
            DungeonsMessageHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new DisplayScreenMessage(DisplayScreenMessage.GuiType.TILE_MANAGER_BLOCK, pos));
        }

        return ActionResultType.SUCCESS;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TileBlockTE();
    }

}
