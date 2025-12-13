package dev.yurisuika.dyed;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Dyed implements ModInitializer {

    public static final String MOD_ID = "dyed";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        Dyed.LOGGER.info("Now dyeing leather horse armor properly!");
    }

}