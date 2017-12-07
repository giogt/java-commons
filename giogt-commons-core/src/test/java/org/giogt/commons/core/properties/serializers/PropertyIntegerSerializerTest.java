package org.giogt.commons.core.properties.serializers;

import org.giogt.commons.core.properties.MappingContext;
import org.giogt.commons.core.properties.PropertySerializationException;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PropertyIntegerSerializerTest {

    @Test
    public void fromString_whenPropertyValueIsNull_mustReturnNull()
            throws PropertySerializationException {

        String propertyValue = null;

        PropertyIntegerSerializer iut = createInstanceUnderTest();
        MappingContext context = createMappingContext();
        Integer result = iut.fromString(context, propertyValue);

        assertNull(result);
    }

    @Test
    public void fromString_whenPropertyValueIsAValidIntegerStringRepresentation_mustReturnTheInteger()
            throws PropertySerializationException {

        String propertyValue = "12345";
        Integer expected = 12345;

        PropertyIntegerSerializer iut = createInstanceUnderTest();
        MappingContext context = createMappingContext();
        Integer result = iut.fromString(context, propertyValue);

        assertThat(result, is(expected));
    }

    @Test
    public void toString_whenValueIsNull_mustReturnNull()
            throws PropertySerializationException {

        Integer value = null;

        PropertyIntegerSerializer iut = createInstanceUnderTest();
        MappingContext context = createMappingContext();
        String result = iut.toString(context, value);

        assertNull(result);
    }

    @Test
    public void toString_whenValueIsAValidInteger_mustReturnTheIntegerStringRepresentation()
            throws PropertySerializationException {

        Integer value = 12345;
        String expected = "12345";

        PropertyIntegerSerializer iut = createInstanceUnderTest();
        MappingContext context = createMappingContext();
        String result = iut.toString(context, value);

        assertThat(result, is(expected));
    }

    PropertyIntegerSerializer createInstanceUnderTest() {
        return new PropertyIntegerSerializer();
    }

    MappingContext createMappingContext() {
        MappingContext context = new MappingContext();
        context.setMappingType(Integer.class);
        return context;
    }

}