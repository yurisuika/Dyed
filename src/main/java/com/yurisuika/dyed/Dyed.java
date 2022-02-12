package com.yurisuika.dyed;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Dyed implements ModInitializer {

	public static final String MOD_ID = "dyed";

	public static final Logger LOGGER = LogManager.getLogger("Dyed");

	@Override
	public void onInitialize() {
		LOGGER.info("Loading Dyed!");
	}

}