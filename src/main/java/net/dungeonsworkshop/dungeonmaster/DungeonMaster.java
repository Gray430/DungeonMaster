package net.dungeonsworkshop.dungeonmaster;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static net.dungeonsworkshop.dungeonmaster.DungeonMaster.MOD_ID;

@Mod(MOD_ID)
public class DungeonMaster {

    public static final String MOD_ID = "dungeonmaster";
    private static final Logger LOGGER = LogManager.getLogger();

    public DungeonMaster(){
        //FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        MinecraftForge.EVENT_BUS.register(this);
    }

}
