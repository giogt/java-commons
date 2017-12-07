package org.giogt.commons.core.properties.serializers;

import org.giogt.commons.core.properties.MappingContext;
import org.giogt.commons.core.properties.PropertySerializationException;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PropertyBigIntegerSerializerTest {

    @Test
    public void fromString_whenPropertyValueIsNull_mustReturnNull()
            throws PropertySerializationException {

        String propertyValue = null;

        PropertyBigIntegerSerializer iut = createInstanceUnderTest();
        MappingContext context = createMappingContext();
        BigInteger result = iut.fromString(context, propertyValue);

        assertNull(result);
    }

    @Test
    public void fromString_whenPropertyValueIsAValidBigIntegerStringRepresentation_mustReturnTheBigInteger()
            throws PropertySerializationException {

        String propertyValue = "123456789012345678901234567890";
        BigInteger expected = new BigInteger("123456789012345678901234567890");

        PropertyBigIntegerSerializer iut = createInstanceUnderTest();
        MappingContext context = createMappingContext();
        BigInteger result = iut.fromString(context, propertyValue);

        assertThat(result, is(expected));
    }

    @Test
    public void toString_whenValueIsNull_mustReturnNull()
            throws PropertySerializationException {

        BigInteger value = null;

        PropertyBigIntegerSerializer iut = createInstanceUnderTest();
        MappingContext context = createMappingContext();
        String result = iut.toString(context, value);

        assertNull(result);
    }

    @Test
    public void toString_whenValueIsAValidBigInteger_mustReturnTheBigIntegerStringRepresentation()
            throws PropertySerializationException {

        BigInteger value = new BigInteger("123456789012345678901234567890");
        String expected = "123456789012345678901234567890";

        PropertyBigIntegerSerializer iut = createInstanceUnderTest();
        MappingContext context = createMappingContext();
        String result = iut.toString(context, value);

        assertThat(result, is(expected));
    }

    PropertyBigIntegerSerializer createInstanceUnderTest() {
        return new PropertyBigIntegerSerializer();
    }

    MappingContext createMappingContext() {
        MappingContext context = new MappingContext();
        context.setMappingType(BigInteger.class);
        return context;
    }

}