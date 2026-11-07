package com.ltwextra;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LTWEExtraMod implements ClientModInitializer {
    public static final String MOD_ID = "ltw-extra";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

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
        
        // Sab systems ko reset karke start karo
        SmartFilter.reset();
        SmartMerge.reset();
        SmartCache.clear();
        
        LOGGER.info("[LTW EXTRA] All 3 systems connected and ready!");
    }

    // Har frame me ye chalega
    public static void onFrameStart() {
        // Kuch nahi, frame start
    }

    // Har frame ke end me ye chalega
    public static void onFrameEnd() {
        if (SMART_FILTER) {
            SmartFilter.onFrameEnd();
        }
        if (SMART_MERGE) {
            SmartMerge.flush();
        }
    }

    // Koi bhi draw call aaye to yaha se jayega
    public static boolean handleDrawCall(String callKey) {
        if (SMART_FILTER) {
            if (!SmartFilter.shouldDraw(callKey)) {
                return false; // Duplicate tha, skip karo
            }
        }
        if (SMART_MERGE) {
            SmartMerge.addToBatch(callKey);
        }
        return true;
    }
}
