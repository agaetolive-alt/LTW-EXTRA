package com.ltwextra;

import java.util.ArrayList;
import java.util.List;

public class SmartMerge {
    private static List<String> pendingCalls = new ArrayList<>();
    private static final int MAX_BATCH = 32;

    public static int mergedBatches = 0;
    public static int totalMergedCalls = 0;

    // Chhota call aaya to turant draw mat karo, queue me rakho
    public static boolean addToBatch(String call) {
        pendingCalls.add(call);
        totalMergedCalls++;

        // Agar 32 ho gaye, to ek saath bhej do
        if (pendingCalls.size() >= MAX_BATCH) {
            flush();
            return true;
        }
        return false;
    }

    // Saare pending calls ko ek saath bhej do
    public static void flush() {
        if (pendingCalls.isEmpty()) return;

        // Yaha pe asli merge hoga - abhi ke liye hum log kar rahe hai
        LTWEExtraMod.LOGGER.info("[LTW EXTRA] Merged " + pendingCalls.size() + " calls into 1 batch");
        mergedBatches++;
        pendingCalls.clear();
    }

    public static int getPendingCount() {
        return pendingCalls.size();
    }

    public static void reset() {
        pendingCalls.clear();
        mergedBatches = 0;
        totalMergedCalls = 0;
    }
}
