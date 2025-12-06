package dev.yurisuika.dyed;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.slf4j.LoggerFactory;

@Mod("dyed")
public class Dyed {

    public Dyed(IEventBus eventBus) {
        LoggerFactory.getLogger("dyed").info("Now dyeing leather horse armor properly!");
    }

}