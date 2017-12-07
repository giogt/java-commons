package org.giogt.commons.core.collections;

import java.util.HashMap;
import java.util.Map;

public class MapBuilder<K, V> {

    private final Map<K, V> map;

    public MapBuilder() {
        this(new HashMap<>());
    }

    public MapBuilder(Map<K, V> map) {
        this.map = map;
    }

    public MapBuilder<K, V> addEntry(K key, V value) {
        map.put(key, value);
        return this;
    }

    public MapBuilder<K, V> addEntries(Map<K, V> entries) {
        map.putAll(entries);
        return this;
    }

    public Map<K, V> build() {
        return map;
    }

}
