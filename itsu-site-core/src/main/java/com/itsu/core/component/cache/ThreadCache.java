package com.itsu.core.component.cache;

import java.util.HashMap;

/**
 * @author Jerry.Su
 * @Date 2021/7/14
 */
public class ThreadCache {

    private static final ThreadLocal<String> STRING_THREAD_CACHE = ThreadLocal.withInitial(() -> "");

    private static final ThreadLocal<HashMap<String, Object>> HASH_MAP_THREAD_CACHE = ThreadLocal.withInitial(HashMap::new);

    private ThreadCache() {

    }

    public static void saveString(String value) {
        STRING_THREAD_CACHE.set(value);
    }


    public static String getString() {
        return STRING_THREAD_CACHE.get();
    }

    public static void removeString() {
        STRING_THREAD_CACHE.remove();
    }

    public static void save(String key, String value) {
        HashMap<String, Object> map = HASH_MAP_THREAD_CACHE.get();
        map.put(key, value);
    }

    public static Object get(String key) {
        HashMap<String, Object> map = HASH_MAP_THREAD_CACHE.get();
        return map.get(key);
    }

    public static Object remove(String key) {
        HashMap<String, Object> map = HASH_MAP_THREAD_CACHE.get();
        return map.remove(key);
    }

    public static void clean() {
        HASH_MAP_THREAD_CACHE.remove();
    }
}
