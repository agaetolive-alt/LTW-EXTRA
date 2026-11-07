package com.ltwextra;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LTWEExtraMod implements ClientModInitializer {
    public static final String MOD_ID = "ltw-extra";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    // LTW EXTRA - 3 Smart Features
    public static boolean SMART_FILTER = true;
    public static boolean SMART_MERGE = true;
    public static boolean SMART_CACHE = true;

    @Override
    public void onInitializeClient() {
        LOGGER.info("[LTW EXTRA] Started!");
        LOGGER.info("[LTW EXTRA] Smart Filter: ON");
        LOGGER.info("[LTW EXTRA] Smart Merge: ON");
        LOGGER.info("[LTW EXTRA] Smart Cache: ON");
        LOGGER.info("[LTW EXTRA] Working with Sodium, not against it!");
    }
}
