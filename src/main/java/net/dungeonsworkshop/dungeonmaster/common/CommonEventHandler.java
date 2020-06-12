package net.dungeonsworkshop.dungeonmaster.common;

import net.dungeonsworkshop.dungeonmaster.DungeonMaster;
import net.dungeonsworkshop.dungeonmaster.common.map.editor.EditorManager;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DungeonMaster.MOD_ID)
public class CommonEventHandler {

    @SubscribeEvent
    public static void entityJoinWorldEvent(EntityJoinWorldEvent event){
        if(event.getEntity() instanceof ServerPlayerEntity){
            EditorManager.loadAllTileBlocksClient((ServerPlayerEntity) event.getEntity());
        }
    }

}
