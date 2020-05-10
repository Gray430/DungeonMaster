package net.dungeonsworkshop.dungeonmaster.common.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.dungeonsworkshop.dungeonmaster.common.map.MapHelper;
import net.dungeonsworkshop.dungeonmaster.common.map.Tile;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;

public class SpawnTileCommand {

    public static void register(CommandDispatcher<CommandSource> dispatcher)
    {
        dispatcher.register(Commands.literal("spawntile").requires((source) -> source.hasPermissionLevel(2)).executes((ctx) -> execute(ctx.getSource())));
    }

    private static int execute(CommandSource source) throws CommandSyntaxException
    {
        ServerPlayerEntity player = source.asPlayer();
        Tile tile = MapHelper.loadTile("Crypts", "crypt_straight003");
        //Tile tile = MapHelper.loadTile("Lobby", "lobby_house_lvl1");
        Tile.buildTileAtPos(player.world, new BlockPos(player.getPosX(), player.getPosY(), player.getPosZ()), tile);
        source.sendFeedback(new StringTextComponent("Spawned Tile"), true);

        return Command.SINGLE_SUCCESS;
    }

}
