package net.dungeonsworkshop.dungeonmaster;

import com.tterrag.registrate.Registrate;
import net.dungeonsworkshop.dungeonmaster.client.ClientEventHandler;
import net.dungeonsworkshop.dungeonmaster.common.command.GetBedrockInfoCommand;
import net.dungeonsworkshop.dungeonmaster.common.command.SpawnTileCommand;
import net.dungeonsworkshop.dungeonmaster.common.init.DungeonBlocks;
import net.dungeonsworkshop.dungeonmaster.common.init.DungeonItems;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
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
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static net.dungeonsworkshop.dungeonmaster.DungeonMaster.MOD_ID;

@Mod(MOD_ID)
public class DungeonMaster {

    public static final String MOD_ID = "dungeonmaster";
    public static Registrate registrate;
    private static final Logger LOGGER = LogManager.getLogger();

    public static final ItemGroup GROUP = new ItemGroup(MOD_ID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(Items.GRASS_BLOCK);
        }
    };

    public DungeonMaster(){
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener(this::onClientSetupEvent);

        MinecraftForge.EVENT_BUS.register(this);

        registrate = Registrate.create(MOD_ID);
        registrate.itemGroup(() -> GROUP);

        DungeonBlocks.load();
    }

    @SubscribeEvent
    public void onServerStartingEvent(FMLServerStartingEvent event)
    {
        SpawnTileCommand.register(event.getCommandDispatcher());
        GetBedrockInfoCommand.register(event.getCommandDispatcher());
    }

    @SubscribeEvent
    public void onClientSetupEvent(FMLClientSetupEvent event){
        ClientEventHandler.init(event);
    }

    public static ResourceLocation getLocation(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

}
