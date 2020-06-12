package net.dungeonsworkshop.dungeonmaster.common.network.handler;

import net.dungeonsworkshop.dungeonmaster.common.network.message.DisplayScreenMessage;
import net.dungeonsworkshop.dungeonmaster.common.network.message.TileManagerHandleMessage;
import net.dungeonsworkshop.dungeonmaster.common.network.message.TileManagerSyncMessage;
import net.dungeonsworkshop.dungeonmaster.common.network.message.TileManagerLoadMessage;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public interface MessageHandler {
    void handleOpenGuiMessage(DisplayScreenMessage msg, Supplier<NetworkEvent.Context> ctx);

    void handleTileBlockLoadMessage(TileManagerLoadMessage msg, Supplier<NetworkEvent.Context> ctx);

    void handleSyncTileManagerMessage(TileManagerSyncMessage msg, Supplier<NetworkEvent.Context> ctx);

    void handleSpawnTileMessage(TileManagerHandleMessage msg, Supplier<NetworkEvent.Context> ctx);
}