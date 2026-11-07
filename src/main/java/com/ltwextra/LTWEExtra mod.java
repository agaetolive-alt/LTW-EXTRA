package com.ltwextra;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LTWEExtraMod implements ModInitializer {
    public static final String MOD_ID = "ltwextra";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("LTWE Extra loaded!");
    }
}
