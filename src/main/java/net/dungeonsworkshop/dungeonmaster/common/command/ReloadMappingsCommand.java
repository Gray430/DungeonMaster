package net.dungeonsworkshop.dungeonmaster.common.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.dungeonsworkshop.dungeonmaster.common.entity.TileBlockTE;
import net.dungeonsworkshop.dungeonmaster.common.map.editor.BlockMapper;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.BlockPosArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;

public class ReloadMappingsCommand {

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(
                Commands.literal("reloadblockmappings")
                        .requires((source) -> source.hasPermissionLevel(2))
                        .executes((ctx) -> execute(ctx.getSource()))
        );
    }

    private static int execute(CommandSource source) throws CommandSyntaxException {
        try {
            BlockMapper.loadBlockMappings();
            source.sendFeedback(new StringTextComponent("Reloaded Block Mappings"), true);
            return Command.SINGLE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

}
