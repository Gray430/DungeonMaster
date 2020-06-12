package net.dungeonsworkshop.dungeonmaster.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.dungeonsworkshop.dungeonmaster.DungeonMaster;
import net.dungeonsworkshop.dungeonmaster.common.entity.TileBlockTE;
import net.dungeonsworkshop.dungeonmaster.common.init.DungeonBlocks;
import net.dungeonsworkshop.dungeonmaster.common.items.RegionEditorItem;
import net.dungeonsworkshop.dungeonmaster.common.map.editor.EditorManager;
import net.dungeonsworkshop.dungeonmaster.common.map.objects.Tile;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = DungeonMaster.MOD_ID)
public class ClientEventHandler {

    //Distance Around the player in blocks that region identifiers will render
    public static final int renderRadius = 32;

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
    public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getEntity() == Minecraft.getInstance().player){
            EditorManager.getTileManagerPositions().clear();
        }
    }

    @SubscribeEvent
    public static void onRenderWorldLast(RenderWorldLastEvent event) {
        double partialTicks = event.getPartialTicks();
        PlayerEntity player = Minecraft.getInstance().player;
        Entity entity = Minecraft.getInstance().getRenderViewEntity();
        World world = Minecraft.getInstance().world;
        FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;
        if (world == null) return;

        int combinedLightIn = 15728880;
        int combinedOverlayIn = NO_OVERLAY;

        IRenderTypeBuffer.Impl renderTypeBuffer = Minecraft.getInstance().getRenderTypeBuffers().getBufferSource();
        IVertexBuilder builder = renderTypeBuffer.getBuffer(RenderType.getLines());
        MatrixStack matrixStack = event.getMatrixStack();
        for (BlockPos pos : EditorManager.getTileManagerPositions()) {
            Vec3d view = Minecraft.getInstance().getRenderManager().info.getProjectedView();
            TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity instanceof TileBlockTE) {
                Vec3i size = ((TileBlockTE) tileEntity).getLoadedTile().getSize();

                matrixStack.push();
                matrixStack.translate(pos.getX() - view.getX(), pos.getY() - view.getY(), pos.getZ() - view.getZ());
                WorldRenderer.drawBoundingBox(event.getMatrixStack(), builder, 0, 1, 0, size.getX(), size.getY() + 1, size.getZ(), 1, 1, 1, 1);
                matrixStack.pop();
            }
        }

        if (player.getHeldItemMainhand().getItem() instanceof RegionEditorItem) {
            CompoundNBT nbt = player.getHeldItemMainhand().getOrCreateTag();
            if (nbt.contains(RegionEditorItem.LINK_POS)) {
                int[] linkPosArray = nbt.getIntArray(RegionEditorItem.LINK_POS);
                BlockPos pos = new BlockPos(linkPosArray[0], linkPosArray[1], linkPosArray[2]);
                TileEntity tileEntity = world.getTileEntity(pos);
                if (tileEntity instanceof TileBlockTE) {
                    Tile tile = ((TileBlockTE) tileEntity).getLoadedTile();
                    BlockPos playerRelativeTilePos = player.getPosition().subtract(pos);
                    if (tile != null) {
                        BlockPos.Mutable blockPos = new BlockPos.Mutable();
                        Vec3d view = Minecraft.getInstance().getRenderManager().info.getProjectedView();
                        Vec3i size = tile.getSize();
                        tile.getRegionPlane().forEach((vec2i, integer) -> {
                            if (Math.pow(playerRelativeTilePos.getX() - vec2i.getX(), 2) + Math.pow(playerRelativeTilePos.getZ() - vec2i.getZ(), 2) <= renderRadius * renderRadius) {
                                matrixStack.push();
                                matrixStack.translate(pos.getX() - view.getX(), pos.getY() - view.getY(), pos.getZ() - view.getZ());
                                for (int y = tile.getSize().getY() + pos.getY(); pos.getY() - 1 < y; y--) {
                                    blockPos.setPos(vec2i.getX() + pos.getX(), y, vec2i.getZ() + pos.getZ());
                                    if (world.getBlockState(blockPos).getBlock() != Blocks.AIR || blockPos.getY() - 1 < pos.getY()) {
                                        matrixStack.translate(vec2i.getX() + 0.25, blockPos.getY() + 1.03215 - pos.getY(), vec2i.getZ() + 0.15);
                                        matrixStack.rotate(Vector3f.XP.rotationDegrees(90));
                                        matrixStack.scale(0.1f, 0.1f, 0.1f);
                                        fontRenderer.renderString(Integer.toString(integer + 1), 0, 0, 0xB00B1E, false, matrixStack.getLast().getMatrix(), renderTypeBuffer, false, 0, combinedLightIn);
                                        break;
                                    }
                                }

                                matrixStack.pop();
                            }
                        });
                    }
                }
            }
        }
        RenderSystem.depthMask(false);
        renderTypeBuffer.finish();
        RenderSystem.depthMask(true);
    }

}
