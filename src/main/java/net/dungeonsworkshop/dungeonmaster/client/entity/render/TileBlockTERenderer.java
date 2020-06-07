package net.dungeonsworkshop.dungeonmaster.client.entity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.dungeonsworkshop.dungeonmaster.common.entity.TileBlockTE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3i;

import static net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY;

public class TileBlockTERenderer extends TileEntityRenderer<TileBlockTE> {

    public TileBlockTERenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public boolean isGlobalRenderer(TileBlockTE te) {
        return true;
    }

    @Override
    public void render(TileBlockTE tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        ActiveRenderInfo renderInfo = Minecraft.getInstance().gameRenderer.getActiveRenderInfo();
        double x = renderInfo.getProjectedView().x;
        double y = renderInfo.getProjectedView().y;
        double z = renderInfo.getProjectedView().z;

        float scale = 1;

        Matrix3f matrix3f = matrixStackIn.getLast().getNormal();
        combinedLightIn = 15728880;
        combinedOverlayIn = NO_OVERLAY;

        matrixStackIn.push();
        matrixStackIn.translate(0, 2, 0);

        Matrix4f matrix4f = matrixStackIn.getLast().getMatrix();

        IVertexBuilder builder = bufferIn.getBuffer(RenderType.getLines());
        TextureAtlasSprite sprite = Minecraft.getInstance().getAtlasSpriteGetter(PlayerContainer.LOCATION_BLOCKS_TEXTURE).apply(new ResourceLocation("minecraft", "block/yellow_stained_glass"));

        int yLevel = tileEntityIn.getY() - 2;
        Vec3i size = tileEntityIn.getSize();
//        WorldRenderer.drawBoundingBox(matrixStackIn, builder, 0, -1, 0, size.getX(), size.getY() + yLevel, size.getZ(), 1, 1, 1, 1);

        matrixStackIn.pop();
    }

}
