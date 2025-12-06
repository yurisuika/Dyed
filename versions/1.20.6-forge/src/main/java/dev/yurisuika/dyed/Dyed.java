package dev.yurisuika.dyed;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.LoggerFactory;

@Mod("dyed")
public class Dyed {

    public Dyed(FMLJavaModLoadingContext context) {
        LoggerFactory.getLogger("dyed").info("Now dyeing leather horse armor properly!");
    }

}