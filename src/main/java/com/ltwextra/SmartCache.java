package com.ltwextra;

import java.util.HashMap;

public class SmartCache {
    // Frame ka hash -> uska result
    private static HashMap<Integer, String> frameCache = new HashMap<>();
    private static final int MAX_CACHE_SIZE = 500;

    public static int cacheHits = 0;
    public static int cacheMisses = 0;

    // Frame pehle se cached hai kya?
    public static boolean hasCachedFrame(int frameHash) {
        return frameCache.containsKey(frameHash);
    }

    // Cached frame le lo
    public static String getCachedFrame(int frameHash) {
        if (hasCachedFrame(frameHash)) {
            cacheHits++;
            return frameCache.get(frameHash);
        }
        cacheMisses++;
        return null;
    }

    // Naya frame cache me daalo
    public static void saveFrame(int frameHash, String result) {
        // Cache bahut bada ho gaya to aadha saaf kar do
        if (frameCache.size() >= MAX_CACHE_SIZE) {
            frameCache.clear();
            LTWEExtraMod.LOGGER.info("[LTW EXTRA] Cache cleared to save RAM");
        }
        frameCache.put(frameHash, result);
    }

    public static String getStats() {
        int total = cacheHits + cacheMisses;
        if (total == 0) return "Cache empty";
        int hitRate = (cacheHits * 100) / total;
        return "Cache Hit Rate: " + hitRate + "% | Size: " + frameCache.size();
    }

    public static void clear() {
        frameCache.clear();
        cacheHits = 0;
        cacheMisses = 0;
    }
}
