package com.ltwextra.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RenderDataCache {
    // Smart Cache: Caches math data based on composite keys to avoid recalculations per frame
    private static final Map<String, Object> genericCache = new ConcurrentHashMap<>();
    private static long lastClearTime = System.currentTimeMillis();

    @SuppressWarnings("unchecked")
    public static <T> T getOrCreate(String key, java.util.function.Supplier<T> supplier) {
        // Prevent memory leaks by periodic automated cleanup every 30 seconds
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastClearTime > 30000) {
            genericCache.clear();
            lastClearTime = currentTime;
        }
        return (T) genericCache.computeIfAbsent(key, k -> supplier.get());
    }
}
