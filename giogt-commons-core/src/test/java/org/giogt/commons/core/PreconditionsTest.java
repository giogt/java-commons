package org.giogt.commons.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

class PreconditionsTest {

    @Test
    public void notNull_forNotNullReference_mustReturnTheReference() {
        String reference = "abcde";
        String result = Preconditions.notNull(reference, "reference");
        assertThat(result, is(reference));
    }

    @Test
    public void notNull_forNullReference_mustThrowIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            String reference = null;
            Preconditions.notNull(reference, "reference");
        });
    }



    @Test
    public void notNullWithSupplier_forNotNullReference_mustReturnTheReference() {
        String reference = "abcde";
        String result = Preconditions.notNull(reference, () -> "reference cannot be null");
        assertThat(result, is(reference));
    }

    @Test
    public void notNullWithSupplier_forNullReference_mustThrowIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            String reference = null;
            Preconditions.notNull(reference, () -> "reference cannot be null");
        });
    }

}