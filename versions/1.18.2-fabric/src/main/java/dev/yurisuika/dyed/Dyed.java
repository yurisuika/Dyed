package dev.yurisuika.dyed;

import net.fabricmc.api.ModInitializer;
import org.slf4j.LoggerFactory;

public class Dyed implements ModInitializer {

    @Override
    public void onInitialize() {
        LoggerFactory.getLogger("dyed").info("Now dyeing leather horse armor properly!");
    }

}