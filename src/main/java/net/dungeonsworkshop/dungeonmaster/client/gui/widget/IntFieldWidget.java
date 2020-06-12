package net.dungeonsworkshop.dungeonmaster.client.gui.widget;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.TextFieldWidget;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.function.Predicate;

public class IntFieldWidget extends TextFieldWidget {

    private Predicate<String> validator = s -> {
        if (s.isEmpty() || NumberUtils.isCreatable(s)) return true;
        return false;
    };

    public IntFieldWidget(FontRenderer fontIn, int xIn, int yIn, int widthIn, int heightIn, String msg) {
        super(fontIn, xIn, yIn, widthIn, heightIn, msg);
    }

    @Deprecated
    @Override
    public void setText(String textIn) {
        super.setText(textIn);
    }

    public void setValue(int valueIn) {
        super.setText(Integer.toString(valueIn));
    }
}
