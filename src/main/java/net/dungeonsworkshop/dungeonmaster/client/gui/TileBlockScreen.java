package net.dungeonsworkshop.dungeonmaster.client.gui;

import io.github.ocelot.client.screen.ValueContainerEditorScreenImpl;
import io.github.ocelot.common.valuecontainer.ValueContainer;
import net.dungeonsworkshop.dungeonmaster.DungeonMaster;
import net.dungeonsworkshop.dungeonmaster.common.network.DungeonsMessageHandler;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.PacketDistributor;

public class TileBlockScreen extends ValueContainerEditorScreenImpl {

    public static final ResourceLocation TEXTURE = new ResourceLocation(DungeonMaster.MOD_ID, "textures/gui/value_container_editor.png");

    public TileBlockScreen(ValueContainer container, BlockPos pos) {
        super(container, pos, () -> new StringTextComponent("Tile Manager Block"));
    }

    @Override
    public ResourceLocation getBackgroundTextureLocation() {
        return TEXTURE;
    }

    @Override
    protected void sendDataToServer() {
    }

}
