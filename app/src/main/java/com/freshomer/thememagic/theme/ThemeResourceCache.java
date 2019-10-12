package com.freshomer.thememagic.theme;

import android.content.res.Resources;
import android.util.LruCache;

import java.util.HashMap;
import java.util.Objects;

/**
 * Since Resources.getIdentifier(name) is discouraged because of its bad performance compared to
 * retrieving resources by identifier, we need to reduce the lookup time to improve the performance.
 * This is what this class do.
 */

class ThemeResourceCache {

    private static int DEFAULT_CACHE_SIZE = 256;

    private HashMap<Theme, LruCache<Integer, Integer>> mThemeMap = new HashMap<>();

    public void add(Theme theme, Resources resources, int resId, int value) {
        LruCache<Integer, Integer> cache = mThemeMap.get(theme);
        if (cache == null) {
            cache = new LruCache<Integer, Integer>(DEFAULT_CACHE_SIZE);
            mThemeMap.put(theme, cache);
        }

        cache.put(getCacheKey(resources, resId), value);
    }

    public int get(Theme theme, Resources resources, int resId) {
        LruCache<Integer, Integer> cache = mThemeMap.get(theme);
        if (cache == null) {
            return 0;
        } else {
            Integer value = cache.get(getCacheKey(resources, resId));
            return value == null ? 0 : value.intValue();
        }

    }

    // There is very very little chance we encounter a hash conflict. But we can ignore this due to
    // resources number scale.
    private int getCacheKey(Resources resources, int resId) {
        return Objects.hash(resources, resId);
    }
}
