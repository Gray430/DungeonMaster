package net.dungeonsworkshop.dungeonmaster.common.network.handler;

import net.dungeonsworkshop.dungeonmaster.DungeonMaster;
import net.dungeonsworkshop.dungeonmaster.client.gui.TileBlockScreen;
import net.dungeonsworkshop.dungeonmaster.common.entity.TileBlockTE;
import net.dungeonsworkshop.dungeonmaster.common.map.editor.EditorManager;
import net.dungeonsworkshop.dungeonmaster.common.network.message.DisplayScreenMessage;
import net.dungeonsworkshop.dungeonmaster.common.network.message.TileManagerHandleMessage;
import net.dungeonsworkshop.dungeonmaster.common.network.message.TileManagerSyncMessage;
import net.dungeonsworkshop.dungeonmaster.common.network.message.TileManagerLoadMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientMessageHandler implements MessageHandler {

    public static final MessageHandler INSTANCE = new ClientMessageHandler();

    @Override
    public void handleOpenGuiMessage(DisplayScreenMessage msg, Supplier<NetworkEvent.Context> ctx) {
        Minecraft minecraft = Minecraft.getInstance();
        World world = minecraft.world;

        ctx.get().enqueueWork(() ->
        {
            BlockPos pos = msg.getPos();

            if (world == null)
                return;

            if (msg.getType() == DisplayScreenMessage.GuiType.TILE_MANAGER_BLOCK) {
                if (pos == null) {
                    DungeonMaster.LOGGER.error("Gui packet " + msg.getType() + " was expected to have a block pos!");
                    return;
                }

                TileEntity tileEntity = world.getTileEntity(pos);
                if (tileEntity instanceof TileBlockTE){
                    minecraft.displayGuiScreen(new TileBlockScreen(new StringTextComponent("Tile Manager Screen"), (TileBlockTE) tileEntity));
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }

    @Override
    public void handleTileBlockLoadMessage(TileManagerLoadMessage msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() ->
        {
            BlockPos pos = msg.getPos();
            boolean load = msg.toLoad();

            if (load) {
                EditorManager.loadTileBlock(pos, ctx.get().getDirection().getReceptionSide().isClient());
            } else {
                EditorManager.unloadTileBlock(pos, ctx.get().getDirection().getReceptionSide().isClient());
            }
        });
        ctx.get().setPacketHandled(true);
    }

    @Override
    public void handleSyncTileManagerMessage(TileManagerSyncMessage msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            TileEntity tileEntity = Minecraft.getInstance().world.getTileEntity(msg.getPos());
            if(tileEntity instanceof TileBlockTE){
                if(msg.getSyncType() == TileManagerSyncMessage.SyncType.LOAD){
                    EditorManager.loadTileBlock(msg.getPos(), true);
                    ((TileBlockTE) tileEntity).setTileId(msg.getTile().getId());
                    ((TileBlockTE) tileEntity).setLoadedTile(msg.getTile());
                }
                if(msg.getSyncType() == TileManagerSyncMessage.SyncType.UNLOAD){
                    EditorManager.unloadTileBlock(msg.getPos(), true);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }

    @Override
    public void handleSpawnTileMessage(TileManagerHandleMessage msg, Supplier<NetworkEvent.Context> ctx) {
        throw new UnsupportedOperationException("Client should not be trying to spawn tile");
    }

}
