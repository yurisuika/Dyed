package dev.yurisuika.dyed;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("dyed")
public class Dyed {

    public static final Logger LOGGER = LogManager.getLogger("dyed");

    public Dyed(FMLJavaModLoadingContext context) {
        Dyed.LOGGER.info("Now dyeing leather horse armor properly!");
    }

}