package net.dungeonsworkshop.dungeonmaster.common.init;

import net.dungeonsworkshop.dungeonmaster.DungeonMaster;
import net.minecraft.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class DungeonItems {

    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, DungeonMaster.MOD_ID);

    //public static final RegistryObject<WindhornItem> WINDHORN = ITEMS.register("windhorn", () -> new WindhornItem(new Item.Properties().group(DungeonsMod.GROUP).maxStackSize(1)));


}
