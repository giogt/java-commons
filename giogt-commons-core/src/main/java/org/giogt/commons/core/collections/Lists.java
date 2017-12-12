package org.giogt.commons.core.collections;

public class Lists {

    // prevent instantiation
    private Lists() {
        throw new AssertionError("Class <" + Lists.class + "> cannot be instantiated");
    }

    public static <T> ListBuilder<T> newList() {
        return new ListBuilder<>();
    }

}
