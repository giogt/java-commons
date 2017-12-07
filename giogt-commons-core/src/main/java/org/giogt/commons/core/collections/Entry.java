package org.giogt.commons.core.collections;

import java.io.Serializable;

public class Entry<K, V> extends AbstractMapEntry<K, V> implements Serializable {
    private static final long serialVersionUID = 1L;

    private final K key;
    private V value;

    public Entry(K key) {
        this(key, null);
    }

    public Entry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public V setValue(V value) {
        V oldValue = this.value;
        this.value = value;
        return oldValue;
    }
}
