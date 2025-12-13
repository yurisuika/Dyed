package dev.yurisuika.dyed;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod("dyed")
public class Dyed {

    public static final Logger LOGGER = LoggerFactory.getLogger("dyed");

    public Dyed(IEventBus eventBus) {
        Dyed.LOGGER.info("Now dyeing leather horse armor properly!");
    }

}