package org.giogt.commons.core.properties.serializers;

import org.giogt.commons.core.properties.MappingContext;
import org.giogt.commons.core.properties.PropertySerializationException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PropertyBigDecimalSerializerTest {

    @Test
    public void fromString_whenPropertyValueIsNull_mustReturnNull() {
        String propertyValue = null;

        PropertyBigDecimalSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        BigDecimal result = iut.fromString(context, propertyValue);

        assertThat(result, is(nullValue()));
    }

    @Test
    public void fromString_whenPropertyValueIsAValidBigDecimalStringRepresentation_mustReturnTheBigDecimal() {
        String propertyValue = "12345678901234567890.1234567890";
        BigDecimal expected = new BigDecimal("12345678901234567890.1234567890");

        PropertyBigDecimalSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        BigDecimal result = iut.fromString(context, propertyValue);

        assertThat(result, is(expected));
    }

    @Test
    public void fromString_whenPropertyValueIsANonValidBigDecimalStringRepresentation_mustThrowSerializationException() {
        String propertyValue = "!?12345678901234567890abcde.fghij1234567890?!";

        PropertyBigDecimalSerializer iut = createSerializer();
        MappingContext context = createMappingContext();

        assertThrows(PropertySerializationException.class, () ->
                iut.fromString(context, propertyValue)
        );
    }


    @Test
    public void toString_whenValueIsNull_mustReturnNull() {
        BigDecimal value = null;

        PropertyBigDecimalSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        String result = iut.toString(context, value);

        assertThat(result, is(nullValue()));
    }

    @Test
    public void toString_whenValueIsAValidBigDecimal_mustReturnTheBigDecimalStringRepresentation() {
        BigDecimal value = new BigDecimal("12345678901234567890.1234567890");
        String expected = "12345678901234567890.1234567890";

        PropertyBigDecimalSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        String result = iut.toString(context, value);

        assertThat(result, is(expected));
    }

    PropertyBigDecimalSerializer createSerializer() {
        return new PropertyBigDecimalSerializer();
    }

    MappingContext createMappingContext() {
        MappingContext context = new MappingContext();
        context.setMappingType(BigDecimal.class);
        return context;
    }

}