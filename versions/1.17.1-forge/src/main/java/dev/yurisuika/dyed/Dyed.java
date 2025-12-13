package dev.yurisuika.dyed;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod("dyed")
public class Dyed {

    public static final Logger LOGGER = LoggerFactory.getLogger("dyed");

    public Dyed(FMLJavaModLoadingContext context) {
        Dyed.LOGGER.info("Now dyeing leather horse armor properly!");
    }

}