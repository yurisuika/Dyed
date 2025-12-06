package dev.yurisuika.dyed;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;

@Mod("dyed")
public class Dyed {

    public Dyed(FMLJavaModLoadingContext context) {
        LogManager.getLogger("dyed").info("Now dyeing leather horse armor properly!");
    }

}