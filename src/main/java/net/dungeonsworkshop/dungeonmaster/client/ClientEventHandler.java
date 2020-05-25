package net.dungeonsworkshop.dungeonmaster.client;

import net.dungeonsworkshop.dungeonmaster.DungeonMaster;
import net.dungeonsworkshop.dungeonmaster.common.init.DungeonBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = DungeonMaster.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEventHandler {

    public static void init(FMLClientSetupEvent event){
        RenderTypeLookup.setRenderLayer(DungeonBlocks.SQ_FLOWER_ALLIUM.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(DungeonBlocks.SQ_FLOWER_DANDELION.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(DungeonBlocks.SQ_FLOWER_HOUSTONIA.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(DungeonBlocks.SQ_FLOWER_PAEONIA.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(DungeonBlocks.SQ_FLOWER_OXEYE_DAISY.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(DungeonBlocks.SQ_FLOWER_ROSE.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(DungeonBlocks.SQ_FLOWER_TULIP_WHITE.get(), RenderType.getCutout());
    }

}
