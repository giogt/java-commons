package org.giogt.commons.core.properties.serializers;

import org.giogt.commons.core.properties.MappingContext;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;

public class PropertyStringSerializerTest {

    @Test
    public void fromString_whenPropertyValueIsNull_mustReturnNull() {
        String propertyValue = null;

        PropertyStringSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        String result = iut.fromString(context, propertyValue);

        assertThat(result, is(nullValue()));
    }

    @Test
    public void fromString_whenPropertyValueIsAString_mustReturnTheSameString() {
        String propertyValue = "12345";
        String expected = propertyValue;

        PropertyStringSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        String result = iut.fromString(context, propertyValue);

        assertThat(result, is(expected));
    }


    @Test
    public void toString_whenValueIsNull_mustReturnNull() {
        String value = null;

        PropertyStringSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        String result = iut.toString(context, value);

        assertThat(result, is(nullValue()));
    }

    @Test
    public void toString_whenValueIsAString_mustReturnTheSameString() {
        String value = "12345";
        String expected = "12345";

        PropertyStringSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        String result = iut.toString(context, value);

        assertThat(result, is(expected));
    }

    PropertyStringSerializer createSerializer() {
        return new PropertyStringSerializer();
    }

    MappingContext createMappingContext() {
        MappingContext context = new MappingContext();
        context.setMappingType(String.class);
        return context;
    }

}