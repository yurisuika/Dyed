package dev.yurisuika.dyed;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.LoggerFactory;

public class Dyed {

    public static class Client implements ClientModInitializer {

        @Override
        public void onInitializeClient() {
            LoggerFactory.getLogger("dyed").info("Now dyeing leather horse armor properly!");
        }

    }

}