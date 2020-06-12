package net.dungeonsworkshop.dungeonmaster.client.gui;

import net.dungeonsworkshop.dungeonmaster.DungeonMaster;
import net.dungeonsworkshop.dungeonmaster.client.gui.widget.IntFieldWidget;
import net.dungeonsworkshop.dungeonmaster.common.entity.TileBlockTE;
import net.dungeonsworkshop.dungeonmaster.common.map.objects.Tile;
import net.dungeonsworkshop.dungeonmaster.common.network.DungeonsMessageHandler;
import net.dungeonsworkshop.dungeonmaster.common.network.message.TileManagerSyncMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.network.PacketDistributor;

public class TileBlockScreen extends Screen {

    public static final ResourceLocation TEXTURE = new ResourceLocation(DungeonMaster.MOD_ID, "textures/gui/value_container_editor.png");
    private TileBlockTE tileBlockTE;

    FontRenderer fontRenderer;
    TextFieldWidget tileIdField;

    IntFieldWidget sizeXField;
    IntFieldWidget sizeYField;
    IntFieldWidget sizeZField;

    public TileBlockScreen(ITextComponent titleIn, TileBlockTE tileBlockTE) {
        super(titleIn);
        this.tileBlockTE = tileBlockTE;
    }

    @Override
    protected void init() {
        super.init();
        fontRenderer = Minecraft.getInstance().fontRenderer;

        tileIdField = new TextFieldWidget(fontRenderer, this.width / 2 - 50, this.height / 2 - 20, 100, 15, "Tile ID");
        sizeXField = new IntFieldWidget(fontRenderer, this.width / 2 - 250, this.height / 2 - 40, 35, 15, "Size X");
        sizeYField = new IntFieldWidget(fontRenderer, this.width / 2 - 210, this.height / 2 - 40, 35, 15, "Size Y");
        sizeZField = new IntFieldWidget(fontRenderer, this.width / 2 - 170, this.height / 2 - 40, 35, 15, "Size Z");

        tileIdField.setText(tileBlockTE.getTileId());
        sizeXField.setValue(tileBlockTE.getLoadedTile().getSize().getX());
        sizeYField.setValue(tileBlockTE.getLoadedTile().getSize().getY());
        sizeZField.setValue(tileBlockTE.getLoadedTile().getSize().getZ());

        Button button = new Button(this.width / 2 - 50, this.height / 2 + 20, 100, 20, "Import", buttonPress -> {
            tileBlockTE.setTileId(tileIdField.getText());
            tileBlockTE.getLoadedTile().setId(tileIdField.getText());
            tileBlockTE.importTile();
        });

        this.addButton(tileIdField);
        this.addButton(sizeXField);
        this.addButton(sizeYField);
        this.addButton(sizeZField);
        this.addButton(button);
    }

    @Override
    public void onClose() {
        tileBlockTE.setTileId(tileIdField.getText());
        Tile tile = tileBlockTE.getLoadedTile();
        tile.setId(tileIdField.getText());
        tile.setSize(new Vec3i(Integer.parseInt(sizeXField.getText()), Integer.parseInt(sizeYField.getText()), Integer.parseInt(sizeZField.getText())));
        tileBlockTE.setLoadedTile(tile);
        DungeonsMessageHandler.INSTANCE.send(PacketDistributor.SERVER.noArg(), new TileManagerSyncMessage(tileBlockTE.getPos(), tileBlockTE.getLoadedTile()));
        super.onClose();
    }

    @Override
    public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
        super.renderBackground();
        super.render(p_render_1_, p_render_2_, p_render_3_);
        fontRenderer.drawStringWithShadow(tileIdField.getMessage(), tileIdField.x, tileIdField.y - 10, 0xFFFFFF);
        fontRenderer.drawStringWithShadow(sizeXField.getMessage(), sizeXField.x, sizeXField.y - 10, 0xFFFFFF);
        fontRenderer.drawStringWithShadow(sizeYField.getMessage(), sizeYField.x, sizeYField.y - 10, 0xFFFFFF);
        fontRenderer.drawStringWithShadow(sizeZField.getMessage(), sizeZField.x, sizeZField.y - 10, 0xFFFFFF);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
