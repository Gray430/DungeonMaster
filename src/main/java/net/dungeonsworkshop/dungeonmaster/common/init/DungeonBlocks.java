package net.dungeonsworkshop.dungeonmaster.common.init;

import net.dungeonsworkshop.dungeonmaster.DungeonMaster;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Function;
import java.util.function.Supplier;

public class DungeonBlocks {

    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, DungeonMaster.MOD_ID);

    //Blocks
    //public static final RegistryObject<Block> TILER = register("tiler", () -> new Tiler(Block.Properties.create(Material.BARRIER)));

    //Registry
    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block) {
        return register(name, block, object -> new BlockItem(object.get(), new Item.Properties().group(DungeonMaster.GROUP)));
    }

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block, Item.Properties itemProperties) {
        return register(name, block, object -> new BlockItem(object.get(), itemProperties));
    }

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block, Function<RegistryObject<T>, Item> item) {
        RegistryObject<T> object = BLOCKS.register(name, block);
        DungeonItems.ITEMS.register(name, () -> item.apply(object));
        return object;
    }

}
