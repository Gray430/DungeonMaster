package net.dungeonsworkshop.dungeonmaster.common.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.dungeonsworkshop.dungeonmaster.common.map.editor.EditorManager;
import net.dungeonsworkshop.dungeonmaster.common.map.objects.Level;
import net.dungeonsworkshop.dungeonmaster.common.map.objects.Tile;
import net.dungeonsworkshop.dungeonmaster.util.LevelIdEnum;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.BlockPosArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;

public class SpawnLevelCommand {

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(
                Commands.literal("spawnlevel")
                        .requires((source) -> source.hasPermissionLevel(2))
                        .then(Commands.argument("position", BlockPosArgument.blockPos())
                                .executes((ctx) -> execute(ctx.getSource(), BlockPosArgument.getLoadedBlockPos(ctx, "position"))))
                        .executes((ctx) -> execute(ctx.getSource(), ctx.getSource().asPlayer().getPosition()))
        );
    }

    private static int execute(CommandSource source, BlockPos position) throws CommandSyntaxException {
        try {
            ServerPlayerEntity player = source.asPlayer();
            Level level = EditorManager.instance().LEVELS.get("squidcoast");
            source.getServer().execute(() -> {;
                level.spawnLevel(source.getWorld(), position, LevelIdEnum.SquidCoast);
                source.sendFeedback(new StringTextComponent("Spawned Level at " + position), true);
            });

            return Command.SINGLE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

}
