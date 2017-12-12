package org.giogt.commons.core.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ListBuilder<T> {

    private List<T> list;

    public ListBuilder() {
        this(new ArrayList<>());
    }

    public ListBuilder(List<T> list) {
        this.list = list;
    }

    public ListBuilder<T> add(T elem) {
        list.add(elem);
        return this;
    }

    public ListBuilder<T> addAll(Collection<? extends T> elem) {
        list.addAll(elem);
        return this;
    }

    public List<T> build() {
        return list;
    }

}
