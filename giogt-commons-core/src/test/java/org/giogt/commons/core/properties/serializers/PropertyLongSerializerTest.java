package org.giogt.commons.core.properties.serializers;

import org.giogt.commons.core.properties.MappingContext;
import org.giogt.commons.core.properties.PropertySerializationException;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PropertyLongSerializerTest {

    @Test
    public void fromString_whenPropertyValueIsNull_mustReturnNull()
            throws PropertySerializationException {

        String propertyValue = null;

        PropertyLongSerializer iut = createInstanceUnderTest();
        MappingContext context = createMappingContext();
        Long result = iut.fromString(context, propertyValue);

        assertNull(result);
    }

    @Test
    public void fromString_whenPropertyValueIsAValidLongStringRepresentation_mustReturnTheLong()
            throws PropertySerializationException {

        String propertyValue = "12345";
        Long expected = 12345L;

        PropertyLongSerializer iut = createInstanceUnderTest();
        MappingContext context = createMappingContext();
        Long result = iut.fromString(context, propertyValue);

        assertThat(result, is(expected));
    }

    @Test
    public void toString_whenValueIsNull_mustReturnNull()
            throws PropertySerializationException {

        Long value = null;

        PropertyLongSerializer iut = createInstanceUnderTest();
        MappingContext context = createMappingContext();
        String result = iut.toString(context, value);

        assertNull(result);
    }

    @Test
    public void toString_whenValueIsAValidLong_mustReturnTheLongStringRepresentation()
            throws PropertySerializationException {

        Long value = 12345L;
        String expected = "12345";

        PropertyLongSerializer iut = createInstanceUnderTest();
        MappingContext context = createMappingContext();
        String result = iut.toString(context, value);

        assertThat(result, is(expected));
    }

    PropertyLongSerializer createInstanceUnderTest() {
        return new PropertyLongSerializer();
    }

    MappingContext createMappingContext() {
        MappingContext context = new MappingContext();
        context.setMappingType(Long.class);
        return context;
    }

}