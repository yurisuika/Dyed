package dev.yurisuika.dyed;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Dyed implements ModInitializer {

    public static final Logger LOGGER = LogManager.getLogger("dyed");

    @Override
    public void onInitialize() {
        Dyed.LOGGER.info("Now dyeing leather horse armor properly!");
    }

}