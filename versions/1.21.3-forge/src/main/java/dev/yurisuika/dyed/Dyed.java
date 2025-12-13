package dev.yurisuika.dyed;

import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(Dyed.MOD_ID)
public class Dyed {

    public static final String MOD_ID = "dyed";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public Dyed() {
        Dyed.LOGGER.info("Now dyeing leather horse armor properly!");
    }

}