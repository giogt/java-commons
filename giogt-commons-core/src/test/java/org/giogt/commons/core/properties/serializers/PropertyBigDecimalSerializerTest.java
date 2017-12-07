package org.giogt.commons.core.properties.serializers;

import org.giogt.commons.core.properties.MappingContext;
import org.giogt.commons.core.properties.PropertySerializationException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PropertyBigDecimalSerializerTest {

    @Test
    public void fromString_whenPropertyValueIsNull_mustReturnNull()
            throws PropertySerializationException {

        String propertyValue = null;

        PropertyBigDecimalSerializer iut = createInstanceUnderTest();
        MappingContext context = createMappingContext();
        BigDecimal result = iut.fromString(context, propertyValue);

        assertNull(result);
    }

    @Test
    public void fromString_whenPropertyValueIsAValidBigDecimalStringRepresentation_mustReturnTheBigDecimal()
            throws PropertySerializationException {

        String propertyValue = "12345678901234567890.1234567890";
        BigDecimal expected = new BigDecimal("12345678901234567890.1234567890");

        PropertyBigDecimalSerializer iut = createInstanceUnderTest();
        MappingContext context = createMappingContext();
        BigDecimal result = iut.fromString(context, propertyValue);

        assertThat(result, is(expected));
    }

    @Test
    public void toString_whenValueIsNull_mustReturnNull()
            throws PropertySerializationException {

        BigDecimal value = null;

        PropertyBigDecimalSerializer iut = createInstanceUnderTest();
        MappingContext context = createMappingContext();
        String result = iut.toString(context, value);

        assertNull(result);
    }

    @Test
    public void toString_whenValueIsAValidBigDecimal_mustReturnTheBigDecimalStringRepresentation()
            throws PropertySerializationException {

        BigDecimal value = new BigDecimal("12345678901234567890.1234567890");
        String expected = "12345678901234567890.1234567890";

        PropertyBigDecimalSerializer iut = createInstanceUnderTest();
        MappingContext context = createMappingContext();
        String result = iut.toString(context, value);

        assertThat(result, is(expected));
    }

    PropertyBigDecimalSerializer createInstanceUnderTest() {
        return new PropertyBigDecimalSerializer();
    }

    MappingContext createMappingContext() {
        MappingContext context = new MappingContext();
        context.setMappingType(BigDecimal.class);
        return context;
    }

}