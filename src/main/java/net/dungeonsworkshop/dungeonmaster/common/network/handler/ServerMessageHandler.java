package net.dungeonsworkshop.dungeonmaster.common.network.handler;

import net.dungeonsworkshop.dungeonmaster.common.network.DisplayScreenMessage;
import net.dungeonsworkshop.dungeonmaster.common.network.TileBlockLoadMessage;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ServerMessageHandler implements MessageHandler {
    public static final MessageHandler INSTANCE = new ServerMessageHandler();

    private ServerMessageHandler() {
    }

    @Override
    public void handleOpenGuiMessage(DisplayScreenMessage msg, Supplier<NetworkEvent.Context> ctx) {
        throw new UnsupportedOperationException("Server cannot open a GUI");
    }

    @Override
    public void handleTileBlockLoadMessage(TileBlockLoadMessage msg, Supplier<NetworkEvent.Context> ctx) {
        throw new UnsupportedOperationException("Server should not be trying to load Tile Manager Block information");
    }

}
