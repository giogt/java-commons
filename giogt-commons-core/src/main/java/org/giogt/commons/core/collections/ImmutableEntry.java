package org.giogt.commons.core.collections;

import java.io.Serializable;

public class ImmutableEntry<K, V> extends AbstractMapEntry<K, V> implements Serializable {
    private static final long serialVersionUID = 1L;

    private final K key;
    private V value;

    public ImmutableEntry(K key) {
        this(key, null);
    }

    public ImmutableEntry(K key, V value) {
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
        throw new UnsupportedOperationException("<" + this.getClass() +
                "> value cannot be changed");
    }
}
