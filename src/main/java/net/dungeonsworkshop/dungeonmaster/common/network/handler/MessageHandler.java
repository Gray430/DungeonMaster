package net.dungeonsworkshop.dungeonmaster.common.network.handler;

import io.github.ocelot.common.valuecontainer.SyncValueContainerMessage;
import net.dungeonsworkshop.dungeonmaster.common.network.DisplayScreenMessage;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * @author Ocelot
 */
public interface MessageHandler
{
    void handleOpenGuiMessage(DisplayScreenMessage msg, Supplier<NetworkEvent.Context> ctx);

    void handleSyncValueContainerMessage(SyncValueContainerMessage msg, Supplier<NetworkEvent.Context> ctx);
}