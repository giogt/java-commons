package org.giogt.commons.core.properties.stores;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class JavaPropertiesStoreTest {

    @Test
    public void buildKeyWithPrefix_whenPrefixIsNotPresent_mustReturnTheSameKey() {
        String key = "testKey";
        String expected = key;

        JavaPropertiesStore iut = createInstanceWithoutPrefix();
        String result = iut.buildKeyWithPrefix(key);

        assertThat(result, is(expected));
    }

    @Test
    public void buildKeyWithPrefix_whenPrefixIsPresent_mustReturnTheKeyWithPrefix() {
        String prefix = "testPrefix.";
        String key = "testKey";
        String expected = prefix + key;

        JavaPropertiesStore iut = createInstanceWithPrefix(prefix);
        String result = iut.buildKeyWithPrefix(key);

        assertThat(result, is(expected));
    }

    private JavaPropertiesStore createInstanceWithoutPrefix() {
        return new JavaPropertiesStore(null);
    }

    private JavaPropertiesStore createInstanceWithPrefix(String prefix) {
        return new JavaPropertiesStore(prefix);
    }

}