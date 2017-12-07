package org.giogt.commons.core.collections;

import java.util.Map;

public class Maps {

    // prevent instantiation
    private Maps() {
        throw new AssertionError("Class <" + Maps.class + "> cannot be instantiated");
    }

    public static <K, V> MapBuilder<K, V> newMap() {
        return new MapBuilder<>();
    }


    public static <K, V> Map.Entry<K, V> newEntry(K key) {
        return new Entry<>(key);
    }

    public static <K, V> Map.Entry<K, V> newEntry(K key, V value) {
        return new Entry<>(key, value);
    }


    public static <K, V> Map.Entry<K, V> newImmutableEntry(K key) {
        return new ImmutableEntry<>(key);
    }

    public static <K, V> Map.Entry<K, V> newImmutableEntry(K key, V value) {
        return new ImmutableEntry<>(key, value);
    }

}
