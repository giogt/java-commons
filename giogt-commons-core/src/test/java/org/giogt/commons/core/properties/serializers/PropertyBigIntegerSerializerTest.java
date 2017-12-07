package org.giogt.commons.core.properties.serializers;

import org.giogt.commons.core.properties.MappingContext;
import org.giogt.commons.core.properties.PropertySerializationException;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PropertyBigIntegerSerializerTest {

    @Test
    public void fromString_whenPropertyValueIsNull_mustReturnNull() {
        String propertyValue = null;

        PropertyBigIntegerSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        BigInteger result = iut.fromString(context, propertyValue);

        assertThat(result, is(nullValue()));
    }

    @Test
    public void fromString_whenPropertyValueIsAValidBigIntegerStringRepresentation_mustReturnTheBigInteger() {
        String propertyValue = "123456789012345678901234567890";
        BigInteger expected = new BigInteger("123456789012345678901234567890");

        PropertyBigIntegerSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        BigInteger result = iut.fromString(context, propertyValue);

        assertThat(result, is(expected));
    }

    @Test
    public void fromString_whenPropertyValueIsANonValidBigIntegerStringRepresentation_mustThrowSerializationException() {
        String propertyValue = "!?12345678901234567890abcde.fghij1234567890?!";

        PropertyBigIntegerSerializer iut = createSerializer();
        MappingContext context = createMappingContext();

        assertThrows(PropertySerializationException.class, () ->
                iut.fromString(context, propertyValue)
        );
    }


    @Test
    public void toString_whenValueIsNull_mustReturnNull() {
        BigInteger value = null;

        PropertyBigIntegerSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        String result = iut.toString(context, value);

        assertThat(result, is(nullValue()));
    }

    @Test
    public void toString_whenValueIsAValidBigInteger_mustReturnTheBigIntegerStringRepresentation() {
        BigInteger value = new BigInteger("123456789012345678901234567890");
        String expected = "123456789012345678901234567890";

        PropertyBigIntegerSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        String result = iut.toString(context, value);

        assertThat(result, is(expected));
    }

    PropertyBigIntegerSerializer createSerializer() {
        return new PropertyBigIntegerSerializer();
    }

    MappingContext createMappingContext() {
        MappingContext context = new MappingContext();
        context.setMappingType(BigInteger.class);
        return context;
    }

}