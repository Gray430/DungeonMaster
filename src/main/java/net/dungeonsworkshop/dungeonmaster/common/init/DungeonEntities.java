package net.dungeonsworkshop.dungeonmaster.common.init;

import net.dungeonsworkshop.dungeonmaster.DungeonMaster;
import net.dungeonsworkshop.dungeonmaster.common.entity.TileBlockTE;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class DungeonEntities {

    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, DungeonMaster.MOD_ID);

    public static final RegistryObject<TileEntityType<TileBlockTE>> TILE_BLOCK = TILE_ENTITIES.register("tileblock", () -> TileEntityType.Builder.create(TileBlockTE::new, DungeonBlocks.TILE_BLOCK.get()).build(null));

}
