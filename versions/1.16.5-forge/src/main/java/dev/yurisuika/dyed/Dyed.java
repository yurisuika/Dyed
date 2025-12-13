package dev.yurisuika.dyed;

import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Dyed.MOD_ID)
public class Dyed {

    public static final String MOD_ID = "dyed";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public Dyed() {
        Dyed.LOGGER.info("Now dyeing leather horse armor properly!");
    }

}