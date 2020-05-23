package net.dungeonsworkshop.dungeonmaster.common.init;

import net.dungeonsworkshop.dungeonmaster.DungeonMaster;
import net.dungeonsworkshop.dungeonmaster.common.blocks.GrassBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
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
    public static final RegistryObject<Block> MISSING_BLOCK = register("missing_block", () -> new Block(Block.Properties.create(Material.BARRIER)));

    public static class SquidCoastBlocks{
        public static final RegistryObject<GrassBlock> SQ_GRASS_BLOCK_DARK = register("squidcoast/grass_block_dark", () -> new GrassBlock(Block.Properties.from(Blocks.GRASS_BLOCK)));
        public static final RegistryObject<GrassBlock> SQ_CUSTOM_0 = register("squidcoast/custom_0", () -> new GrassBlock(Block.Properties.from(Blocks.GRASS_BLOCK)));
    }
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
