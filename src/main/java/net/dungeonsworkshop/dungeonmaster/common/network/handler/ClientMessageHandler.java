package net.dungeonsworkshop.dungeonmaster.common.network.handler;

import io.github.ocelot.common.valuecontainer.SyncValueContainerMessage;
import io.github.ocelot.common.valuecontainer.ValueContainer;
import net.dungeonsworkshop.dungeonmaster.DungeonMaster;
import net.dungeonsworkshop.dungeonmaster.client.gui.TileBlockScreen;
import net.dungeonsworkshop.dungeonmaster.common.network.DisplayScreenMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * @author Ocelot
 */
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

            if (msg.getType() == DisplayScreenMessage.GuiType.VALUE_CONTAINER_EDITOR)
            {
                if (pos == null)
                {
                    DungeonMaster.LOGGER.error("Gui packet " + msg.getType() + " was expected to have a block pos!");
                    return;
                }

                TileEntity te = world.getTileEntity(pos);
                if (!(te instanceof ValueContainer) && !(world.getBlockState(pos).getBlock() instanceof ValueContainer))
                {
                    DungeonMaster.LOGGER.error("Tile Entity at '" + pos + "' was expected to be a ValueContainer, but it was " + world.getTileEntity(pos) + "!");
                    return;
                }
                minecraft.displayGuiScreen(new TileBlockScreen(te instanceof ValueContainer ? (ValueContainer) te : (ValueContainer) world.getBlockState(pos).getBlock(), pos));
            }
        });
        ctx.get().setPacketHandled(true);
    }

    @Override
    public void handleSyncValueContainerMessage(SyncValueContainerMessage msg, Supplier<NetworkEvent.Context> ctx) {
        throw new UnsupportedOperationException("Client cannot be told to sync Value Containers");
    }
}
