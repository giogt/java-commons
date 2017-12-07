package org.giogt.commons.core.properties.serializers;

import org.giogt.commons.core.properties.MappingContext;
import org.giogt.commons.core.properties.PropertySerializationException;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PropertyStringSerializerTest {

    @Test
    public void fromString_whenPropertyValueIsNull_mustReturnNull()
            throws PropertySerializationException {

        String propertyValue = null;

        PropertyStringSerializer iut = createInstanceUnderTest();
        MappingContext context = createMappingContext();
        String result = iut.fromString(context, propertyValue);

        assertNull(result);
    }

    @Test
    public void fromString_whenPropertyValueIsAString_mustReturnTheSameString()
            throws PropertySerializationException {

        String propertyValue = "12345";
        String expected = propertyValue;

        PropertyStringSerializer iut = createInstanceUnderTest();
        MappingContext context = createMappingContext();
        String result = iut.fromString(context, propertyValue);

        assertThat(result, is(expected));
    }

    @Test
    public void toString_whenValueIsNull_mustReturnNull()
            throws PropertySerializationException {

        String value = null;

        PropertyStringSerializer iut = createInstanceUnderTest();
        MappingContext context = createMappingContext();
        String result = iut.toString(context, value);

        assertNull(result);
    }

    @Test
    public void toString_whenValueIsAString_mustReturnTheSameString()
            throws PropertySerializationException {

        String value = "12345";
        String expected = "12345";

        PropertyStringSerializer iut = createInstanceUnderTest();
        MappingContext context = createMappingContext();
        String result = iut.toString(context, value);

        assertThat(result, is(expected));
    }

    PropertyStringSerializer createInstanceUnderTest() {
        return new PropertyStringSerializer();
    }

    MappingContext createMappingContext() {
        MappingContext context = new MappingContext();
        context.setMappingType(String.class);
        return context;
    }

}