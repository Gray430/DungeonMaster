package net.dungeonsworkshop.dungeonmaster.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.dungeonsworkshop.dungeonmaster.DungeonMaster;
import net.dungeonsworkshop.dungeonmaster.common.init.DungeonBlocks;
import net.dungeonsworkshop.dungeonmaster.common.map.editor.EditorManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.Map;

@Mod.EventBusSubscriber(modid = DungeonMaster.MOD_ID)
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

    @SubscribeEvent
    public static void onRenderWorldLast(RenderWorldLastEvent event) {
        double partialTicks = event.getPartialTicks();
        Entity entity = Minecraft.getInstance().getRenderViewEntity();

        IRenderTypeBuffer.Impl renderTypeBuffer = Minecraft.getInstance().getRenderTypeBuffers().getBufferSource();
        for(Map.Entry<Vec3i, Vec3i> tileBlock : EditorManager.ClientTileBlockManager.TILE_MANAGERS.entrySet()){
            Vec3d view = Minecraft.getInstance().getRenderManager().info.getProjectedView();
            Vec3i pos = tileBlock.getKey();
            Vec3i size = tileBlock.getValue();

            IVertexBuilder builder = renderTypeBuffer.getBuffer(RenderType.getLines());
            MatrixStack matrixStack = event.getMatrixStack();
            matrixStack.push();
            matrixStack.translate(pos.getX() - view.getX(), pos.getY() - view.getY(), pos.getZ() - view.getZ());
            WorldRenderer.drawBoundingBox(event.getMatrixStack(), builder, 0 , 1, 0, size.getX(), size.getY() + 1, size.getZ(), 1, 1, 1, 1);
            matrixStack.pop();
        }
        renderTypeBuffer.finish(RenderType.getLines());
    }

}
