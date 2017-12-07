package org.giogt.commons.core.properties.serializers;

import org.giogt.commons.core.properties.MappingContext;
import org.giogt.commons.core.properties.PropertySerializationException;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PropertyIntegerSerializerTest {

    @Test
    public void fromString_whenPropertyValueIsNull_mustReturnNull() {
        String propertyValue = null;

        PropertyIntegerSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        Integer result = iut.fromString(context, propertyValue);

        assertThat(result, is(nullValue()));
    }

    @Test
    public void fromString_whenPropertyValueIsAValidIntegerStringRepresentation_mustReturnTheInteger() {
        String propertyValue = "12345";
        Integer expected = 12345;

        PropertyIntegerSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        Integer result = iut.fromString(context, propertyValue);

        assertThat(result, is(expected));
    }

    @Test
    public void fromString_whenPropertyValueIsANonValidIntegerStringRepresentation_mustThrowSerializationException() {
        String propertyValue = "!?12345?!";

        PropertyIntegerSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        assertThrows(PropertySerializationException.class, () ->
                iut.fromString(context, propertyValue)
        );
    }


    @Test
    public void toString_whenValueIsNull_mustReturnNull() {
        Integer value = null;

        PropertyIntegerSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        String result = iut.toString(context, value);

        assertThat(result, is(nullValue()));
    }

    @Test
    public void toString_whenValueIsAValidInteger_mustReturnTheIntegerStringRepresentation() {
        Integer value = 12345;
        String expected = "12345";

        PropertyIntegerSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        String result = iut.toString(context, value);

        assertThat(result, is(expected));
    }

    PropertyIntegerSerializer createSerializer() {
        return new PropertyIntegerSerializer();
    }

    MappingContext createMappingContext() {
        MappingContext context = new MappingContext();
        context.setMappingType(Integer.class);
        return context;
    }

}