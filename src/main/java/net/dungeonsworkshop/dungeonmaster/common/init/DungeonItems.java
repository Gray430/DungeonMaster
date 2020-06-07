package net.dungeonsworkshop.dungeonmaster.common.init;

import net.dungeonsworkshop.dungeonmaster.DungeonMaster;
import net.dungeonsworkshop.dungeonmaster.common.items.RegionEditorItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class DungeonItems {

    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, DungeonMaster.MOD_ID);

    public static final RegistryObject<RegionEditorItem> REGION_EDITOR = ITEMS.register("regioneditor", () -> new RegionEditorItem(new Item.Properties().group(DungeonMaster.GROUP).maxStackSize(1)));


}
