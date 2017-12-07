package org.giogt.commons.core.collections;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;

public class MapBuilderTest {

    @Test
    public void mapBuilder_forNoEntries_mustReturnEmptyMap() {
        MapBuilder<String, String> iut = createInstanceUnderTest();
        Map<String, String> result = iut.build();

        assertThat(result, is(notNullValue()));
        assertThat(result.size(), is(0));
    }

    @Test
    public void mapBuilder_forOneEntry_mustReturnMapWithTheEntry() {
        String key1 = "key1";
        String value1 = "value1";

        MapBuilder<String, String> iut = createInstanceUnderTest();
        iut.addEntry(key1, value1);
        Map<String, String> result = iut.build();

        assertThat(result, is(notNullValue()));
        assertThat(result.size(), is(1));
        assertThat(result.get(key1), is(value1));
    }

    @Test
    public void mapBuilder_forMultipleEntriesAddedWithAddEntry_mustReturnMapWithTheEntries() {
        String key1 = "key1";
        String value1 = "value1";

        String key2 = "key2";
        String value2 = "value2";

        String key3 = "key3";
        String value3 = "value3";

        MapBuilder<String, String> iut = createInstanceUnderTest();
        Map<String, String> result = iut
                .addEntry(key1, value1)
                .addEntry(key2, value2)
                .addEntry(key3, value3)
                .build();

        assertThat(result, is(notNullValue()));
        assertThat(result.size(), is(3));
        assertThat(result.get(key1), is(value1));
        assertThat(result.get(key2), is(value2));
        assertThat(result.get(key3), is(value3));
    }

    @Test
    public void mapBuilder_forMultipleEntriesAddedWithAddEntries_mustReturnMapWithTheEntries() {
        String key1 = "key1";
        String value1 = "value1";

        String key2 = "key2";
        String value2 = "value2";

        String key3 = "key3";
        String value3 = "value3";

        Map<String, String> entries = new HashMap<>();
        entries.put(key1, value1);
        entries.put(key2, value2);
        entries.put(key3, value3);

        MapBuilder<String, String> iut = createInstanceUnderTest();
        iut.addEntries(entries);
        Map<String, String> result = iut.build();

        assertThat(result, is(notNullValue()));
        assertThat(result.size(), is(3));
        assertThat(result.get(key1), is(value1));
        assertThat(result.get(key2), is(value2));
        assertThat(result.get(key3), is(value3));
    }

    private MapBuilder<String, String> createInstanceUnderTest() {
        return new MapBuilder<>();
    }

}