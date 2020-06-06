package net.dungeonsworkshop.dungeonmaster.common.init;

import com.tterrag.registrate.util.RegistryEntry;
import net.dungeonsworkshop.dungeonmaster.DungeonMaster;
import net.dungeonsworkshop.dungeonmaster.common.blocks.*;
import net.dungeonsworkshop.dungeonmaster.common.blocks.GrassBlock;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ModelFile;

import java.util.function.Supplier;

import static net.dungeonsworkshop.dungeonmaster.DungeonMaster.registrate;

public class DungeonBlocks {
    //Blocks

    public static final RegistryEntry<TileBlock> TILE_BLOCK = registerFullBlock("tileblock", () -> new TileBlock(Block.Properties.create(Material.BARRIER)));
    public static final RegistryEntry<TileDoorBlock> TILE_DOOR = registerFullBlock("tiledoorblock", () -> new TileDoorBlock(Block.Properties.create(Material.BARRIER)));
    public static final RegistryEntry<RegionPlaneBlock> REGION_PLANE_BLOCK = registerFullBlock("regionplaneblock", () -> new RegionPlaneBlock(Block.Properties.create(Material.BARRIER)));

    // ------------------------ Squid Coast Start ------------------------
    //Full Blocks
    public static final RegistryEntry<Block> UNKNOWN_BLOCK = registerFullBlock("unknownblock", () -> new Block(Block.Properties.create(Material.BARRIER)));
    public static final RegistryEntry<GrassBlock> SQ_BEDROCK = registerFullBlock("squidcoast", "bedrock", () -> new GrassBlock(Block.Properties.from(Blocks.TERRACOTTA)));
    public static final RegistryEntry<GrassBlock> SQ_DIRT = registerFullBlock("squidcoast", "dirt", () -> new GrassBlock(Block.Properties.from(Blocks.DIRT)));
    public static final RegistryEntry<GrassBlock> SQ_NETHER_BRICK = registerFullBlock("squidcoast", "nether_brick", () -> new GrassBlock(Block.Properties.from(Blocks.DIRT)));
    public static final RegistryEntry<Block> SQ_DIRT_PODZOL = registerFullBlockSuffix("squidcoast", "dirt_podzol", "_side", () -> new Block(Block.Properties.from(Blocks.DIRT)));
    public static final RegistryEntry<Block> SQ_GRANITE = registerFullBlock("squidcoast", "stone_granite", () -> new Block(Block.Properties.from(Blocks.STONE)));
    public static final RegistryEntry<Block> SQ_GRANITE_SMOOTH = registerFullBlock("squidcoast", "stone_granite_smooth", () -> new Block(Block.Properties.from(Blocks.STONE)));
    public static final RegistryEntry<Block> SQ_DIORITE = registerFullBlock("squidcoast", "stone_diorite", () -> new Block(Block.Properties.from(Blocks.STONE)));
    public static final RegistryEntry<Block> SQ_DIORITE_SMOOTH = registerFullBlock("squidcoast", "stone_diorite_smooth", () -> new Block(Block.Properties.from(Blocks.STONE)));
    public static final RegistryEntry<Block> SQ_ANDESITE = registerFullBlock("squidcoast", "stone_andesite", () -> new Block(Block.Properties.from(Blocks.STONE)));
    public static final RegistryEntry<Block> SQ_ANDESITE_SMOOTH = registerFullBlock("squidcoast", "stone_andesite_smooth", () -> new Block(Block.Properties.from(Blocks.STONE)));
    public static final RegistryEntry<GrassBlock> SQ_STONEFLOOR1 = registerFullBlock("squidcoast", "stonefloor1", () -> new GrassBlock(Block.Properties.from(Blocks.STONE)));
    public static final RegistryEntry<GrassBlock> SQ_STONEFLOOR2 = registerFullBlock("squidcoast", "stonefloor2", () -> new GrassBlock(Block.Properties.from(Blocks.STONE)));
    public static final RegistryEntry<GrassBlock> SQ_STONEFLOOR3 = registerFullBlock("squidcoast", "stonefloor3", () -> new GrassBlock(Block.Properties.from(Blocks.STONE)));
    public static final RegistryEntry<GrassBlock> SQ_STONEFLOOR4 = registerFullBlock("squidcoast", "stonefloor4", () -> new GrassBlock(Block.Properties.from(Blocks.STONE)));
    public static final RegistryEntry<GrassBlock> SQ_STONEFLOOR5 = registerFullBlock("squidcoast", "stonefloor5", () -> new GrassBlock(Block.Properties.from(Blocks.STONE)));
    public static final RegistryEntry<GrassBlock> SQ_STONEFLOOR6 = registerFullBlock("squidcoast", "stonefloor6", () -> new GrassBlock(Block.Properties.from(Blocks.STONE)));
    public static final RegistryEntry<GrassBlock> SQ_STONEFLOOR7 = registerFullBlock("squidcoast", "stonefloor7", () -> new GrassBlock(Block.Properties.from(Blocks.STONE)));
    public static final RegistryEntry<GrassBlock> SQ_STONEFLOOR8 = registerFullBlock("squidcoast", "stonefloor8", () -> new GrassBlock(Block.Properties.from(Blocks.STONE)));
    public static final RegistryEntry<GrassBlock> SQ_CUSTOM_0 = registerFullBlock("squidcoast", "custom_0", () -> new GrassBlock(Block.Properties.from(Blocks.DIRT)));
    public static final RegistryEntry<GrassBlock> SQ_CUSTOM_1 = registerFullBlock("squidcoast", "custom_1", () -> new GrassBlock(Block.Properties.from(Blocks.DIRT)));
    public static final RegistryEntry<GrassBlock> SQ_CUSTOM_2 = registerFullBlock("squidcoast", "custom_2", () -> new GrassBlock(Block.Properties.from(Blocks.DIRT)));
    public static final RegistryEntry<GrassBlock> SQ_CUSTOM_4 = registerFullBlock("squidcoast", "custom_4", () -> new GrassBlock(Block.Properties.from(Blocks.STONE_BRICKS)));
    public static final RegistryEntry<GrassBlock> SQ_CUSTOM_5 = registerFullBlock("squidcoast", "custom_5", () -> new GrassBlock(Block.Properties.from(Blocks.STONE_BRICKS)));
    public static final RegistryEntry<GrassBlock> SQ_CUSTOM_6 = registerFullBlock("squidcoast", "custom_6", () -> new GrassBlock(Block.Properties.from(Blocks.STONE_BRICKS)));
    public static final RegistryEntry<GrassBlock> SQ_CUSTOM_9 = registerFullBlock("squidcoast", "custom_9", () -> new GrassBlock(Block.Properties.from(Blocks.DIRT)));
    public static final RegistryEntry<GrassBlock> SQ_COBBLESTONE = registerFullBlock("squidcoast", "cobblestone", () -> new GrassBlock(Block.Properties.from(Blocks.DIRT)));
    public static final RegistryEntry<GrassBlock> SQ_MOSSY_COBBLESTONE = registerFullBlock("squidcoast", "cobblestone_mossy", () -> new GrassBlock(Block.Properties.from(Blocks.DIRT)));

    //Sided
    public static final RegistryEntry<GrassBlock> SQ_MYCELIUM = registerSidedBlock("squidcoast", "mycelium", () -> new GrassBlock(Block.Properties.from(Blocks.GRASS_BLOCK)));
    public static final RegistryEntry<GrassBlock> SQ_QUARTZ_BLOCK = registerSidedBlock("squidcoast", "quartz_block", () -> new GrassBlock(Block.Properties.from(Blocks.TERRACOTTA)));
    public static final RegistryEntry<GrassBlock> SQ_GRASS = registerSidedBlock("squidcoast", "grass", () -> new GrassBlock(Block.Properties.from(Blocks.GRASS_BLOCK)));

    //Cross Blocks
    public static final RegistryEntry<CrossBlock> SQ_FLOWER_ALLIUM = registerCrossBlock("squidcoast", "flower_allium", () -> new CrossBlock(Block.Properties.from(Blocks.GRASS)));
    public static final RegistryEntry<CrossBlock> SQ_FLOWER_DANDELION = registerCrossBlock("squidcoast", "flower_dandelion", () -> new CrossBlock(Block.Properties.from(Blocks.GRASS)));
    public static final RegistryEntry<CrossBlock> SQ_FLOWER_HOUSTONIA = registerCrossBlock("squidcoast", "flower_houstonia", () -> new CrossBlock(Block.Properties.from(Blocks.GRASS)));
    public static final RegistryEntry<CrossBlock> SQ_FLOWER_OXEYE_DAISY = registerCrossBlock("squidcoast", "flower_oxeye_daisy", () -> new CrossBlock(Block.Properties.from(Blocks.GRASS)));
    public static final RegistryEntry<CrossBlock> SQ_FLOWER_PAEONIA = registerCrossBlock("squidcoast", "flower_paeonia", () -> new CrossBlock(Block.Properties.from(Blocks.GRASS)));
    public static final RegistryEntry<CrossBlock> SQ_FLOWER_ROSE = registerCrossBlock("squidcoast", "flower_rose", () -> new CrossBlock(Block.Properties.from(Blocks.GRASS)));
    public static final RegistryEntry<CrossBlock> SQ_FLOWER_TULIP_WHITE = registerCrossBlock("squidcoast", "flower_tulip_white", () -> new CrossBlock(Block.Properties.from(Blocks.GRASS)));

    //Pillars
    public static final RegistryEntry<RotatedPillarBlock> SQ_QUARTZ_BLOCK_LINES = registerSidedPillarBlock("squidcoast", "quartz_block_lines", () -> new RotatedPillarBlock(Block.Properties.from(Blocks.QUARTZ_PILLAR)));

    //Walls
    public static final RegistryEntry<WallBlock> SQ_COBBLESTONE_WALL = registerWallSingleTexture("squidcoast", "cobblestone_wall",
            DungeonMaster.getLocation("block/squidcoast/cobblestone"),
            () -> new WallBlock(Block.Properties.from(Blocks.COBBLESTONE)));
    public static final RegistryEntry<WallBlock> SQ_COBBLESTONE_MOSSY_WALL = registerWallSingleTexture("squidcoast", "cobblestone_mossy_wall",
            DungeonMaster.getLocation("block/squidcoast/cobblestone_mossy"),
            () -> new WallBlock(Block.Properties.from(Blocks.COBBLESTONE)));

    //Paths
    public static final RegistryEntry<PathBlock> SQ_GRASS_PATH = registerPathBlockSided("squidcoast", "grass_path", () -> new PathBlock(Block.Properties.from(Blocks.GRASS_BLOCK)));
    public static final RegistryEntry<PathBlock> SQ_DIRT_PATH = registerPathBlockSided("squidcoast", "dirt_path", () -> new PathBlock(Block.Properties.from(Blocks.DIRT)));
    public static final RegistryEntry<PathBlock> SQ_GRAVEL_PATH = registerPathBlockSided("squidcoast", "gravel_path", () -> new PathBlock(Block.Properties.from(Blocks.STONE)));
    public static final RegistryEntry<PathBlock> SQ_STONE_PATH = registerPathBlockSided("squidcoast", "stone_path", () -> new PathBlock(Block.Properties.from(Blocks.STONE)));
    public static final RegistryEntry<PathBlock> SQ_SKULL_PATH = registerPathBlockSided("squidcoast", "skull_path", () -> new PathBlock(Block.Properties.from(Blocks.STONE)));

    //Slabs
    public static final RegistryEntry<SlabBlock> SQ_RED_SANDSTONE = registerSlab("squidcoast", "red_sandstone", "_normal", "_top", () -> new SlabBlock(Block.Properties.from(Blocks.GRASS_BLOCK)));
    public static final RegistryEntry<SlabBlock> SQ_STONE_SLAB = registerSlab("squidcoast", "stone_slab", "_side", "_top", () -> new SlabBlock(Block.Properties.from(Blocks.STONE)));
    public static final RegistryEntry<SlabBlock> SQ_STONEFLOOR3_SLAB = registerSlabSingleTexture("squidcoast", "stonefloor3_slab",
            DungeonMaster.getLocation("block/squidcoast/stonefloor3"),
            () -> new SlabBlock(Block.Properties.from(Blocks.COBBLESTONE)));
    public static final RegistryEntry<SlabBlock> SQ_STONEFLOOR4_SLAB = registerSlabSingleTexture("squidcoast", "stonefloor4_slab",
            DungeonMaster.getLocation("block/squidcoast/stonefloor4"),
            () -> new SlabBlock(Block.Properties.from(Blocks.COBBLESTONE)));
    public static final RegistryEntry<SlabBlock> SQ_STONEFLOOR5_SLAB = registerSlabSingleTexture("squidcoast", "stonefloor5_slab",
            DungeonMaster.getLocation("block/squidcoast/stonefloor5"),
            () -> new SlabBlock(Block.Properties.from(Blocks.COBBLESTONE)));
    public static final RegistryEntry<SlabBlock> SQ_STONEFLOOR8_SLAB = registerSlabSingleTexture("squidcoast", "stonefloor8_slab",
            DungeonMaster.getLocation("block/squidcoast/stonefloor8"),
            () -> new SlabBlock(Block.Properties.from(Blocks.DIRT)));
    public static final RegistryEntry<SlabBlock> SQ_DIRT_SLAB = registerSlabSingleTexture("squidcoast", "dirt_slab",
            DungeonMaster.getLocation("block/squidcoast/dirt"),
            () -> new SlabBlock(Block.Properties.from(Blocks.DIRT)));
    public static final RegistryEntry<SlabBlock> SQ_COBBLESTONE_SLAB = registerSlabSingleTexture("squidcoast", "cobblestone_slab",
            DungeonMaster.getLocation("block/squidcoast/cobblestone"),
            () -> new SlabBlock(Block.Properties.from(Blocks.COBBLESTONE)));
    public static final RegistryEntry<SlabBlock> SQ_SANDSTONE = registerSlabTextured("squidcoast", "sandstone",
            DungeonMaster.getLocation("block/squidcoast/cobblestone"),
            DungeonMaster.getLocation("block/squidcoast/sandstone_top"),
            DungeonMaster.getLocation("block/squidcoast/cobblestone"),
            () -> new SlabBlock(Block.Properties.from(Blocks.COBBLESTONE)));
    // ------------------------ Squid Coast End ------------------------
    //Registry

    private static <T extends Block> RegistryEntry<T> registerFullBlock(String name, Supplier<T> block) {
        return registrate
                .object(name)
                .block(properties -> block.get())
                .blockstate((ctx, provider) -> provider.simpleBlock(ctx.getEntry()))
                .properties(prop -> prop)
                .item()
                .model((ctx, provider) -> {
                    provider.withExistingParent("item/" + ctx.getName(), DungeonMaster.getLocation("block/" + ctx.getName()));
                })
                .build().register();
    }

    private static <T extends Block> RegistryEntry<T> registerFullBlock(String levelname, String name, Supplier<T> block) {
        return registrate
                .object(levelname + "/" + name)
                .block(properties -> block.get())
                .blockstate((ctx, provider) -> {
                    ModelFile modelFile = provider.models().withExistingParent("block/" + ctx.getName(), new ResourceLocation("block/cube_all"))
                            .texture("all", DungeonMaster.getLocation("block/" + levelname + "/" + name));
                    provider.simpleBlock(ctx.getEntry(), modelFile);
                })
                .item()
                .model((ctx, provider) -> {
                    provider.withExistingParent("item/" + ctx.getName(), DungeonMaster.getLocation("block/" + ctx.getName()));
                })
                .build().register();
    }

    private static <T extends Block> RegistryEntry<T> registerFullBlockSuffix(String levelname, String name, String suffix, Supplier<T> block) {
        return registrate
                .object(levelname + "/" + name)
                .block(properties -> block.get())
                .blockstate((ctx, provider) -> {
                    ModelFile modelFile = provider.models().withExistingParent("block/" + ctx.getName(), new ResourceLocation("block/cube_all"))
                            .texture("all", DungeonMaster.getLocation("block/" + levelname + "/" + name + suffix));
                    provider.simpleBlock(ctx.getEntry(), modelFile);
                })
                .item()
                .model((ctx, provider) -> {
                    provider.withExistingParent("item/" + ctx.getName(), DungeonMaster.getLocation("block/" + ctx.getName()));
                })
                .build().register();
    }

    private static <T extends Block> RegistryEntry<T> registerSidedBlock(String levelname, String name, Supplier<T> block) {
        return registrate
                .object(levelname + "/" + name)
                .block(properties -> block.get())
                .blockstate((ctx, provider) -> {
                    ModelFile modelFile = provider.models().withExistingParent("block/" + ctx.getName(), new ResourceLocation("block/cube_bottom_top"))
                            .texture("particle", DungeonMaster.getLocation("block/" + ctx.getName() + "_side"))
                            .texture("side", DungeonMaster.getLocation("block/" + ctx.getName() + "_side"))
                            .texture("top", DungeonMaster.getLocation("block/" + ctx.getName() + "_top"));
                    provider.simpleBlock(ctx.getEntry(), modelFile);
                })
                .item()
                .model((ctx, provider) -> {
                    provider.withExistingParent("item/" + ctx.getName(), DungeonMaster.getLocation("block/" + ctx.getName()));
                })
                .build().register();
    }

    private static <T extends RotatedPillarBlock> RegistryEntry<T> registerSidedPillarBlock(String levelname, String name, Supplier<T> block) {
        return registrate
                .object(levelname + "/" + name)
                .block(properties -> block.get())
                .blockstate((ctx, provider) -> {
                    ModelFile modelFile = provider.models().withExistingParent("block/" + ctx.getName(), new ResourceLocation("block/cube_column"))
                            .texture("end", DungeonMaster.getLocation("block/" + ctx.getName() + "_top"))
                            .texture("side", DungeonMaster.getLocation("block/" + ctx.getName()));
                    provider.axisBlock(ctx.getEntry(), modelFile);
                })
                .item()
                .model((ctx, provider) -> {
                    provider.withExistingParent("item/" + ctx.getName(), DungeonMaster.getLocation("block/" + ctx.getName()));
                })
                .build().register();
    }

    private static <T extends SlabBlock> RegistryEntry<T> registerSlab(String levelname, String name, String sideTextureSuffix, String topTextureSuffix, Supplier<T> block) {
        return registrate
                .object(levelname + "/" + name)
                .block(properties -> block.get())
                .blockstate((ctx, provider) -> {
                    ModelFile topSlab = provider.models().withExistingParent("block/" + ctx.getName() + "_top", new ResourceLocation("block/slab_top"))
                            .texture("particle", DungeonMaster.getLocation("block/" + ctx.getName() + sideTextureSuffix))
                            .texture("bottom", DungeonMaster.getLocation("block/" + ctx.getName() + topTextureSuffix))
                            .texture("side", DungeonMaster.getLocation("block/" + ctx.getName() + sideTextureSuffix))
                            .texture("top", DungeonMaster.getLocation("block/" + ctx.getName() + topTextureSuffix));

                    ModelFile bottomSlab = provider.models().withExistingParent("block/" + ctx.getName() + "_bottom", new ResourceLocation("block/slab"))
                            .texture("particle", DungeonMaster.getLocation("block/" + ctx.getName() + sideTextureSuffix))
                            .texture("bottom", DungeonMaster.getLocation("block/" + ctx.getName() + topTextureSuffix))
                            .texture("side", DungeonMaster.getLocation("block/" + ctx.getName() + sideTextureSuffix))
                            .texture("top", DungeonMaster.getLocation("block/" + ctx.getName() + topTextureSuffix));

                    ModelFile doubleSlab = provider.models().withExistingParent("block/" + ctx.getName() + "_double", new ResourceLocation("block/cube_bottom_top"))
                            .texture("particle", DungeonMaster.getLocation("block/" + ctx.getName() + sideTextureSuffix))
                            .texture("bottom", DungeonMaster.getLocation("block/" + ctx.getName() + topTextureSuffix))
                            .texture("side", DungeonMaster.getLocation("block/" + ctx.getName() + sideTextureSuffix))
                            .texture("top", DungeonMaster.getLocation("block/" + ctx.getName() + topTextureSuffix));

                    provider.slabBlock(ctx.getEntry(), bottomSlab, topSlab, doubleSlab);
                })
                .properties(prop -> prop)
                .item()
                .model((ctx, provider) -> {
                    provider.withExistingParent("item/" + ctx.getName(), DungeonMaster.getLocation("block/" + ctx.getName() + "_bottom"));
                })
                .build().register();
    }

    private static <T extends SlabBlock> RegistryEntry<T> registerSlabSingleTexture(String levelname, String name, ResourceLocation texture, Supplier<T> block) {
        return registrate
                .object(levelname + "/" + name)
                .block(properties -> block.get())
                .blockstate((ctx, provider) -> {
                    ModelFile topSlab = provider.models().withExistingParent("block/" + ctx.getName() + "_top", new ResourceLocation("block/slab_top"))
                            .texture("particle", texture)
                            .texture("bottom", texture)
                            .texture("side", texture)
                            .texture("top", texture);

                    ModelFile bottomSlab = provider.models().withExistingParent("block/" + ctx.getName() + "_bottom", new ResourceLocation("block/slab"))
                            .texture("particle", texture)
                            .texture("bottom", texture)
                            .texture("side", texture)
                            .texture("top", texture);

                    ModelFile doubleSlab = provider.models().withExistingParent("block/" + ctx.getName() + "_double", new ResourceLocation("block/cube_bottom_top"))
                            .texture("particle", texture)
                            .texture("bottom", texture)
                            .texture("side", texture)
                            .texture("top", texture);

                    provider.slabBlock(ctx.getEntry(), bottomSlab, topSlab, doubleSlab);
                })
                .properties(prop -> prop)
                .item()
                .model((ctx, provider) -> {
                    provider.withExistingParent("item/" + ctx.getName(), DungeonMaster.getLocation("block/" + ctx.getName() + "_bottom"));
                })
                .build().register();
    }

    private static <T extends SlabBlock> RegistryEntry<T> registerSlabTextured(String levelname, String name, ResourceLocation side, ResourceLocation top, ResourceLocation bottom, Supplier<T> block) {
        return registrate
                .object(levelname + "/" + name)
                .block(properties -> block.get())
                .blockstate((ctx, provider) -> {
                    ModelFile topSlab = provider.models().withExistingParent("block/" + ctx.getName() + "_top", new ResourceLocation("block/slab_top"))
                            .texture("particle", side)
                            .texture("bottom", bottom)
                            .texture("side", side)
                            .texture("top", top);

                    ModelFile bottomSlab = provider.models().withExistingParent("block/" + ctx.getName() + "_bottom", new ResourceLocation("block/slab"))
                            .texture("particle", side)
                            .texture("bottom", bottom)
                            .texture("side", side)
                            .texture("top", top);

                    ModelFile doubleSlab = provider.models().withExistingParent("block/" + ctx.getName() + "_double", new ResourceLocation("block/cube_bottom_top"))
                            .texture("particle", side)
                            .texture("bottom", bottom)
                            .texture("side", side)
                            .texture("top", top);

                    provider.slabBlock(ctx.getEntry(), bottomSlab, topSlab, doubleSlab);
                })
                .properties(prop -> prop)
                .item()
                .model((ctx, provider) -> {
                    provider.withExistingParent("item/" + ctx.getName(), DungeonMaster.getLocation("block/" + ctx.getName() + "_bottom"));
                })
                .build().register();
    }

    private static <T extends Block> RegistryEntry<T> registerPathBlockSided(String levelname, String name, Supplier<T> block) {
        return registrate
                .object(levelname + "/" + name)
                .block(properties -> block.get())
                .blockstate((ctx, provider) -> {
                    ModelFile modelFile = provider.models().withExistingParent("block/" + ctx.getName(), new ResourceLocation("block/grass_path"))
                            .texture("particle", DungeonMaster.getLocation("block/" + ctx.getName() + "_side"))
                            .texture("bottom", DungeonMaster.getLocation("block/" + ctx.getName() + "_top"))
                            .texture("side", DungeonMaster.getLocation("block/" + ctx.getName() + "_side"))
                            .texture("top", DungeonMaster.getLocation("block/" + ctx.getName() + "_top"));
                    provider.simpleBlock(ctx.getEntry(), modelFile);
                })
                .item()
                .model((ctx, provider) -> {
                    provider.withExistingParent("item/" + ctx.getName(), DungeonMaster.getLocation("block/" + ctx.getName()));
                })
                .build().register();
    }

    private static <T extends Block> RegistryEntry<T> registerPathBlockTextured(String levelname, String name, String textureName, Supplier<T> block) {
        return registrate
                .object(levelname + "/" + name)
                .block(properties -> block.get())
                .blockstate((ctx, provider) -> {
                    ModelFile modelFile = provider.models().withExistingParent("block/" + ctx.getName(), new ResourceLocation("block/grass_path"))
                            .texture("particle", DungeonMaster.getLocation("block/" + levelname + "/" + textureName))
                            .texture("bottom", DungeonMaster.getLocation("block/" + levelname + "/" + textureName))
                            .texture("side", DungeonMaster.getLocation("block/" + levelname + "/" + textureName))
                            .texture("top", DungeonMaster.getLocation("block/" + levelname + "/" + textureName));
                    provider.simpleBlock(ctx.getEntry(), modelFile);
                })
                .item()
                .model((ctx, provider) -> {
                    provider.withExistingParent("item/" + ctx.getName(), DungeonMaster.getLocation("block/" + ctx.getName()));
                })
                .build().register();
    }

    private static <T extends WallBlock> RegistryEntry<T> registerWallSingleTexture(String levelname, String name, ResourceLocation texture, Supplier<T> block) {
        return registrate
                .object(levelname + "/" + name)
                .block(properties -> block.get())
                .blockstate((ctx, provider) -> {
                    ModelFile postWall = provider.models().withExistingParent("block/" + ctx.getName() + "_post", new ResourceLocation("block/template_wall_post"))
                            .texture("wall", texture);
                    ModelFile sideWall = provider.models().withExistingParent("block/" + ctx.getName() + "_side", new ResourceLocation("block/template_wall_side"))
                            .texture("wall", texture);
                    ModelFile inventoryWall = provider.models().withExistingParent("block/" + ctx.getName() + "_inventory", new ResourceLocation("block/wall_inventory"))
                            .texture("wall", texture);
                    inventoryWall.assertExistence();
                    provider.wallBlock(ctx.getEntry(), postWall, sideWall);
                })
                .tag(BlockTags.WALLS)
                .properties(prop -> prop)
                .item()
                .model((ctx, provider) -> {
                    provider.withExistingParent("item/" + ctx.getName(), DungeonMaster.getLocation("block/" + ctx.getName() + "_inventory"));
                })
                .build().register();
    }

    private static <T extends CrossBlock> RegistryEntry<T> registerCrossBlock(String levelname, String name, Supplier<T> block) {
        return registrate
                .object(levelname + "/" + name)
                .block(properties -> block.get())
                .blockstate((ctx, provider) -> {
                    ModelFile modelFile = provider.models().withExistingParent("block/" + ctx.getName(), new ResourceLocation("block/cross"))
                            .texture("cross", DungeonMaster.getLocation("block/" + ctx.getName()));
                    provider.simpleBlock(ctx.getEntry(), modelFile);
                })
                .tag(BlockTags.WALLS)
                .properties(prop -> prop)
                .item()
                .model((ctx, provider) -> {
                    provider.withExistingParent("item/" + ctx.getName(), DungeonMaster.getLocation("block/" + ctx.getName()));
                })
                .build().register();
    }

    public static void load() {
    }

}
