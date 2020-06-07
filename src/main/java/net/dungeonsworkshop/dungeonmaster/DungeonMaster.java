package net.dungeonsworkshop.dungeonmaster;

import com.tterrag.registrate.Registrate;
import net.dungeonsworkshop.dungeonmaster.client.ClientEventHandler;
import net.dungeonsworkshop.dungeonmaster.client.entity.render.TileBlockTERenderer;
import net.dungeonsworkshop.dungeonmaster.common.command.ExportTileCommand;
import net.dungeonsworkshop.dungeonmaster.common.command.GetBedrockInfoCommand;
import net.dungeonsworkshop.dungeonmaster.common.command.ReloadMappingsCommand;
import net.dungeonsworkshop.dungeonmaster.common.command.SpawnTileCommand;
import net.dungeonsworkshop.dungeonmaster.common.init.DungeonBlocks;
import net.dungeonsworkshop.dungeonmaster.common.init.DungeonEntities;
import net.dungeonsworkshop.dungeonmaster.common.init.DungeonItems;
import net.dungeonsworkshop.dungeonmaster.common.network.DungeonsMessageHandler;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static net.dungeonsworkshop.dungeonmaster.DungeonMaster.MOD_ID;

@Mod(MOD_ID)
public class DungeonMaster {

    public static final String MOD_ID = "dungeonmaster";
    public static final Logger LOGGER = LogManager.getLogger();
    public static final ItemGroup GROUP = new ItemGroup(MOD_ID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(Items.GRASS_BLOCK);
        }
    };
    public static Registrate registrate;

    public DungeonMaster() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener(this::onCommonSetup);
        modBus.addListener(this::onClientSetupEvent);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new ClientEventHandler());

        registrate = Registrate.create(MOD_ID);
        registrate.itemGroup(() -> GROUP);

        DungeonItems.ITEMS.register(modBus);
        DungeonBlocks.load();
        DungeonEntities.TILE_ENTITIES.register(modBus);
    }

    public static ResourceLocation getLocation(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    @SubscribeEvent
    public void onCommonSetup(FMLCommonSetupEvent event) {
        DungeonsMessageHandler.init();
    }

    @SubscribeEvent
    public void onServerStartingEvent(FMLServerStartingEvent event) {
        ReloadMappingsCommand.register(event.getCommandDispatcher());
        ExportTileCommand.register(event.getCommandDispatcher());
        SpawnTileCommand.register(event.getCommandDispatcher());
        GetBedrockInfoCommand.register(event.getCommandDispatcher());
    }

    @SubscribeEvent
    public void onClientSetupEvent(FMLClientSetupEvent event) {

        ClientEventHandler.init(event);
        ClientRegistry.bindTileEntityRenderer(DungeonEntities.TILE_BLOCK.get(), TileBlockTERenderer::new);
    }

}
