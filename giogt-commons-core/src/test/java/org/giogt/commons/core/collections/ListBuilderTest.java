package org.giogt.commons.core.collections;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;

class ListBuilderTest {

    @Test
    public void listBuilder_forNoEntries_mustReturnEmptyList() {
        ListBuilder<String> iut = createInstanceUnderTest();
        List<String> result = iut.build();

        assertThat(result, is(notNullValue()));
        assertThat(result.size(), is(0));
    }

    @Test
    public void listBuilder_forOneElement_mustReturnListWithTheElement() {
        String elem1 = "elem1";

        ListBuilder<String> iut = createInstanceUnderTest();
        iut.add(elem1);
        List<String> result = iut.build();

        assertThat(result, is(notNullValue()));
        assertThat(result.size(), is(1));
        assertThat(result.get(0), is(elem1));
    }

    @Test
    public void listBuilder_forMultipleElementsAddedWithAdd_mustReturnListWithTheElementsInOrder() {
        String elem1 = "elem1";
        String elem2 = "elem2";
        String elem3 = "elem3";

        ListBuilder<String> iut = createInstanceUnderTest();
        List<String> result = iut
                .add(elem1)
                .add(elem2)
                .add(elem3)
                .build();

        assertThat(result, is(notNullValue()));
        assertThat(result.size(), is(3));
        assertThat(result.get(0), is(elem1));
        assertThat(result.get(1), is(elem2));
        assertThat(result.get(2), is(elem3));
    }

    @Test
    public void listBuilder_forMultipleElementsAddedWithAddAll_mustReturnListWithTheElementsInOrder() {
        String elem1 = "elem1";
        String elem2 = "elem2";
        String elem3 = "elem3";

        List<String> elements = new ArrayList<>();
        elements.add(elem1);
        elements.add(elem2);
        elements.add(elem3);

        ListBuilder<String> iut = createInstanceUnderTest();
        iut.addAll(elements);
        List<String> result = iut.build();

        assertThat(result, is(notNullValue()));
        assertThat(result.size(), is(3));
        assertThat(result.get(0), is(elem1));
        assertThat(result.get(1), is(elem2));
        assertThat(result.get(2), is(elem3));
    }

    private ListBuilder<String> createInstanceUnderTest() {
        return new ListBuilder<>();
    }

}