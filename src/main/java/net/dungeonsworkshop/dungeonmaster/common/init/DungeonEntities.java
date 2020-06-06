package net.dungeonsworkshop.dungeonmaster.common.init;

import com.tterrag.registrate.util.RegistryEntry;
import net.dungeonsworkshop.dungeonmaster.DungeonMaster;
import net.dungeonsworkshop.dungeonmaster.common.entity.TileBlockTE;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

import static net.dungeonsworkshop.dungeonmaster.DungeonMaster.registrate;

public class DungeonEntities {

    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, DungeonMaster.MOD_ID);

    public static final RegistryObject<TileEntityType<TileBlockTE>> TILE_BLOCK = TILE_ENTITIES.register("tileblock",() -> TileEntityType.Builder.<TileBlockTE>create(TileBlockTE::new, DungeonBlocks.TILE_BLOCK.get()).build(null));

}
