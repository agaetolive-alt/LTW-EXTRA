package com.ltwextra;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LtwExtraClient implements ClientModInitializer {
    public static final String MOD_ID = "ltw-extra";
    public static final Logger LOGGER = LoggerFactory.getLogger("LTW EXTRA");

    @Override
    public void onInitializeClient() {
        LOGGER.info("LTW EXTRA [Client Optimization] initialized successfully!");
    }
}
