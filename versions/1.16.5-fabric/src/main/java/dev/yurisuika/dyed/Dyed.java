package dev.yurisuika.dyed;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;

public class Dyed implements ModInitializer {

    @Override
    public void onInitialize() {
        LogManager.getLogger("dyed").info("Now dyeing leather horse armor properly!");
    }

}