package net.dungeonsworkshop.dungeonmaster.common.network.handler;

import net.dungeonsworkshop.dungeonmaster.common.entity.TileBlockTE;
import net.dungeonsworkshop.dungeonmaster.common.network.message.DisplayScreenMessage;
import net.dungeonsworkshop.dungeonmaster.common.network.message.TileManagerHandleMessage;
import net.dungeonsworkshop.dungeonmaster.common.network.message.TileManagerSyncMessage;
import net.dungeonsworkshop.dungeonmaster.common.network.message.TileManagerLoadMessage;
import net.minecraft.tileentity.TileEntity;
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
    public void handleTileBlockLoadMessage(TileManagerLoadMessage msg, Supplier<NetworkEvent.Context> ctx) {
        throw new UnsupportedOperationException("Server should not be trying to load Tile Manager Block information");
    }

    @Override
    public void handleSyncTileManagerMessage(TileManagerSyncMessage msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if(msg.getSyncType() == TileManagerSyncMessage.SyncType.SERVER){
                TileEntity tileEntity = ctx.get().getSender().world.getTileEntity(msg.getPos());
                if(tileEntity instanceof TileBlockTE){
                    ((TileBlockTE) tileEntity).setTileId(msg.getTile().getId());
                    ((TileBlockTE) tileEntity).setLoadedTile(msg.getTile());
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }

    @Override
    public void handleSpawnTileMessage(TileManagerHandleMessage msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            TileEntity tileEntity = ctx.get().getSender().world.getTileEntity(msg.getPos());
            if(tileEntity instanceof TileBlockTE){
                ((TileBlockTE) tileEntity).importTile();
            }
        });
        ctx.get().setPacketHandled(true);
    }

}
