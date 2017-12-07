package org.giogt.commons.core.collections;

import org.giogt.commons.core.Objects;

import java.util.Map;
import java.util.Map.Entry;

public abstract class AbstractMapEntry<K, V> implements Map.Entry<K, V> {

    @Override
    public abstract K getKey();

    @Override
    public abstract V getValue();

    @Override
    public abstract V setValue(V value);

    @Override
    public boolean equals(Object object) {
        if (object instanceof Entry) {
            Entry<?, ?> that = (Entry<?, ?>) object;
            return Objects.equal(this.getKey(), that.getKey())
                    && Objects.equal(this.getValue(), that.getValue());
        }
        return false;
    }

    @Override
    public int hashCode() {
        K k = getKey();
        V v = getValue();
        return ((k == null) ? 0 : k.hashCode()) ^ ((v == null) ? 0 : v.hashCode());
    }

    /**
     * Returns an entry string representation of the form {@code {key}={value}}.
     *
     * @return the entry string representation
     */
    @Override
    public String toString() {
        return getKey() + "=" + getValue();
    }

}
