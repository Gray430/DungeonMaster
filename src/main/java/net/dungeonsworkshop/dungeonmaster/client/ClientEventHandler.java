package net.dungeonsworkshop.dungeonmaster.client;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.dungeonsworkshop.dungeonmaster.DungeonMaster;
import net.dungeonsworkshop.dungeonmaster.common.entity.TileBlockTE;
import net.dungeonsworkshop.dungeonmaster.common.init.DungeonBlocks;
import net.dungeonsworkshop.dungeonmaster.common.map.editor.EditorManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = DungeonMaster.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEventHandler {

    public static void init(FMLClientSetupEvent event) {
        RenderTypeLookup.setRenderLayer(DungeonBlocks.SQ_FLOWER_ALLIUM.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(DungeonBlocks.SQ_FLOWER_DANDELION.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(DungeonBlocks.SQ_FLOWER_HOUSTONIA.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(DungeonBlocks.SQ_FLOWER_PAEONIA.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(DungeonBlocks.SQ_FLOWER_OXEYE_DAISY.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(DungeonBlocks.SQ_FLOWER_ROSE.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(DungeonBlocks.SQ_FLOWER_TULIP_WHITE.get(), RenderType.getCutout());
    }

    public static void onRenderWorldLast(RenderWorldLastEvent event){
        IRenderTypeBuffer.Impl renderTypeBuffer = Minecraft.getInstance().getRenderTypeBuffers().getBufferSource();
        for(int i = 0; i < EditorManager.TILE_MANAGERS.size(); i++){
            if(EditorManager.TILE_MANAGERS.get(i).isRemoved()){
                EditorManager.TILE_MANAGERS.remove(i--);
                continue;
            }

            Vec3i size = EditorManager.TILE_MANAGERS.get(i).getSize();
            IVertexBuilder builder = renderTypeBuffer.getBuffer(RenderType.getLines());
            WorldRenderer.drawBoundingBox(event.getMatrixStack(), builder, 0, -1, 0, size.getX(), size.getY() - 2, size.getZ(), 1, 1, 1, 1);
        }
        renderTypeBuffer.finish(RenderType.getLines());
    }

}
