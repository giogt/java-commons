package org.giogt.commons.core.collections;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MultivaluedHashMapTest {

    @Test
    public void testMap_empty() {
        MultivaluedHashMap<String, String> map = buildTestMap();
        assertThat(map, is(notNullValue()));
        assertThat(map.size(), is(0));

        assertEmptyCollection(map.entrySet());
        assertEmptyCollection(map.keySet());
        assertEmptyCollection(map.values());
    }

    @Test
    public void testMap_withOneSingleElement() {
        MultivaluedHashMap<String, String> map = buildTestMap();
        assertThat(map, is(notNullValue()));
        assertThat(map.size(), is(0));

        String key1 = "key1";
        String value1 = "value1";

        map.add(key1, value1);
        assertThat(map.size(), is(1));

        // assert entries, key set and values

        Set<Map.Entry<String, List<String>>> entries = map.entrySet();
        assertThat(entries, is(notNullValue()));
        assertThat(entries.size(), is(1));
        assertThat(entries, containsInAnyOrder(
                Maps.newImmutableEntry(key1, Arrays.asList(value1))));

        Set<String> keySet = map.keySet();
        assertThat(keySet, is(notNullValue()));
        assertThat(keySet.size(), is(1));
        assertThat(keySet, containsInAnyOrder(key1));

        Collection<List<String>> values = map.values();
        assertThat(values, is(notNullValue()));
        assertThat(values.size(), is(1));
        assertThat(values, containsInAnyOrder(
                Arrays.asList(value1)));

        // assert iterators

        assertThat(entries.iterator().hasNext(), is(true));
        assertThat(entries.iterator().next().getKey(), is(key1));
        assertThat(entries.iterator().next().getValue(), is(Arrays.asList(value1)));

        assertThat(keySet.iterator().hasNext(), is(true));
        assertThat(keySet.iterator().next(), is(key1));

        assertThat(values.iterator().hasNext(), is(true));
        assertThat(values.iterator().next(), is(Arrays.asList(value1)));
    }

    @Test
    public void testMap_withOneMultipleElement() {
        MultivaluedHashMap<String, String> map = buildTestMap();
        assertThat(map, is(notNullValue()));
        assertThat(map.size(), is(0));

        String key1 = "key1";
        String value1_1 = "value1_1";
        String value1_2 = "value1_2";

        map.add(key1, value1_1);
        map.add(key1, value1_2);
        assertThat(map.size(), is(1));

        // assert entries, key set and values

        Set<Map.Entry<String, List<String>>> entries = map.entrySet();
        assertThat(entries, is(notNullValue()));
        assertThat(entries.size(), is(1));
        assertThat(entries, containsInAnyOrder(
                Maps.newImmutableEntry(key1, Arrays.asList(value1_1, value1_2))));

        Set<String> keySet = map.keySet();
        assertThat(keySet, is(notNullValue()));
        assertThat(keySet.size(), is(1));
        assertThat(keySet, containsInAnyOrder(key1));

        Collection<List<String>> values = map.values();
        assertThat(values, is(notNullValue()));
        assertThat(values.size(), is(1));
        assertThat(values, containsInAnyOrder(
                Arrays.asList(value1_1, value1_2)));

        // assert iterators

        assertThat(entries.iterator().hasNext(), is(true));
        assertThat(entries.iterator().next().getKey(), is(key1));
        assertThat(entries.iterator().next().getValue(), is(Arrays.asList(value1_1, value1_2)));

        assertThat(keySet.iterator().hasNext(), is(true));
        assertThat(keySet.iterator().next(), is(key1));

        assertThat(values.iterator().hasNext(), is(true));
        assertThat(values.iterator().next(), is(Arrays.asList(value1_1, value1_2)));
    }

    @Test
    public void testMap_withTwoElements() {
        MultivaluedHashMap<String, String> map = buildTestMap();
        assertThat(map, is(notNullValue()));
        assertThat(map.size(), is(0));

        String key1 = "key1";
        String value1_1 = "value1_1";
        String value1_2 = "value1_2";

        String key2 = "key2";
        String value2 = "value2";

        map.add(key1, value1_1);
        map.add(key1, value1_2);
        map.add(key2, value2);
        assertThat(map.size(), is(2));

        // assert entries, key set and values

        Set<Map.Entry<String, List<String>>> entries = map.entrySet();
        assertThat(entries, is(notNullValue()));
        assertThat(entries.size(), is(2));
        assertThat(entries, containsInAnyOrder(
                Maps.newImmutableEntry(key1, Arrays.asList(value1_1, value1_2)),
                Maps.newImmutableEntry(key2, Arrays.asList(value2))));

        Set<String> keySet = map.keySet();
        assertThat(keySet, is(notNullValue()));
        assertThat(keySet.size(), is(2));
        assertThat(keySet, containsInAnyOrder(key1, key2));

        Collection<List<String>> values = map.values();
        assertThat(values, is(notNullValue()));
        assertThat(values.size(), is(2));
        assertThat(values, containsInAnyOrder(
                Arrays.asList(value1_1, value1_2),
                Arrays.asList(value2)));
    }

    private void assertEmptyCollection(Collection<?> c) {
        assertThat(c, is(notNullValue()));
        assertThat(c.size(), is(0));
        assertThat(c.iterator(), is(notNullValue()));
        assertThat(c.iterator().hasNext(), is(false));
        assertThrows(NoSuchElementException.class,
                () -> c.iterator().next());
    }

    private MultivaluedHashMap<String, String> buildTestMap() {
        return new MultivaluedHashMap<>();
    }

}