package com.ltwextra;

import java.util.HashSet;

public class SmartFilter {
    // Last frame ke calls ko yaad rakhega
    private static HashSet<String> lastFrameCalls = new HashSet<>();
    private static HashSet<String> currentFrameCalls = new HashSet<>();

    public static int totalCalls = 0;
    public static int filteredCalls = 0;

    // Har draw call se pehle ye check hoga
    public static boolean shouldDraw(String callKey) {
        totalCalls++;

        // Agar ye call isi frame me pehle ho chuka hai, to skip karo
        if (currentFrameCalls.contains(callKey)) {
            filteredCalls++;
            return false;
        }

        currentFrameCalls.add(callKey);
        return true;
    }

    // Har frame ke end me call hoga
    public static void onFrameEnd() {
        lastFrameCalls.clear();
        lastFrameCalls.addAll(currentFrameCalls);
        currentFrameCalls.clear();
    }

    public static void reset() {
        lastFrameCalls.clear();
        currentFrameCalls.clear();
        totalCalls = 0;
        filteredCalls = 0;
    }

    public static String getStats() {
        if (totalCalls == 0) return "No calls yet";
        int saved = (filteredCalls * 100) / totalCalls;
        return "Saved " + saved + "% calls | " + filteredCalls + " / " + totalCalls;
    }
}
