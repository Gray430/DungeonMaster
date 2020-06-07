package net.dungeonsworkshop.dungeonmaster.common.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.dungeonsworkshop.dungeonmaster.common.entity.TileBlockTE;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.BlockPosArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;

public class SpawnTileCommand {

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(
                Commands.literal("spawntile")
                        .requires((source) -> source.hasPermissionLevel(2))
                        .then(Commands.argument("position", BlockPosArgument.blockPos())
                                .executes((ctx) -> execute(ctx.getSource(), BlockPosArgument.getLoadedBlockPos(ctx, "position"))))
                        .executes((ctx) -> execute(ctx.getSource(), ctx.getSource().asPlayer().getPosition()))
        );
    }

    private static int execute(CommandSource source, BlockPos position) throws CommandSyntaxException {
        try {
            ServerPlayerEntity player = source.asPlayer();
            source.getServer().execute(() -> {
                TileEntity tileEntity = player.world.getTileEntity(position);
                if (tileEntity != null && tileEntity instanceof TileBlockTE) {
                    TileBlockTE tileBlockTE = (TileBlockTE) tileEntity;
                    source.sendFeedback(new StringTextComponent("Found Tile Block. Attempting to spawn " + tileBlockTE.getTileId()), true);
                    Boolean success = tileBlockTE.importTile();
                    if (success) {
                        source.sendFeedback(new StringTextComponent("Tile Found. Building at " + position), true);
                    } else {
                        source.sendFeedback(new StringTextComponent("Tile not found."), true);
                    }
                } else {
                    source.sendFeedback(new StringTextComponent("Tile block not found at " + position), true);
                }
            });

            return Command.SINGLE_SUCCESS;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

}
