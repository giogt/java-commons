package org.giogt.commons.core.properties.serializers;

import org.giogt.commons.core.properties.MappingContext;
import org.giogt.commons.core.properties.PropertySerializationException;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PropertyBooleanSerializerTest {

    @Test
    public void fromString_whenPropertyValueIsNull_mustReturnNull()
            throws PropertySerializationException {

        String propertyValue = null;

        PropertyBooleanSerializer instance = createInstanceUnderTest();
        MappingContext context = createMappingContext();
        Boolean result = instance.fromString(context, propertyValue);

        assertNull(result);
    }

    @Test
    public void fromString_whenPropertyValueIsStringTrue_mustReturnTrue()
            throws PropertySerializationException {

        String propertyValue = BooleanValues.TRUE_STRING;
        Boolean expected = Boolean.TRUE;

        PropertyBooleanSerializer iut = createInstanceUnderTest();
        MappingContext context = createMappingContext();
        Boolean result = iut.fromString(context, propertyValue);

        assertThat(result, is(expected));
    }

    @Test
    public void fromString_whenPropertyValueIsStringFalse_mustReturnFalse()
            throws PropertySerializationException {

        String propertyValue = BooleanValues.FALSE_STRING;
        Boolean expected = Boolean.FALSE;

        PropertyBooleanSerializer iut = createInstanceUnderTest();
        MappingContext context = createMappingContext();
        Boolean result = iut.fromString(context, propertyValue);

        assertThat(result, is(expected));
    }

    @Test
    public void toString_whenValueIsNull_mustReturnNull()
            throws PropertySerializationException {

        Boolean value = null;

        PropertyBooleanSerializer iut = createInstanceUnderTest();
        MappingContext context = createMappingContext();
        String result = iut.toString(context, value);

        assertNull(result);
    }

    @Test
    public void toString_whenValueIsTrue_mustReturnStringTrue()
            throws PropertySerializationException {

        Boolean value = Boolean.TRUE;
        String expected = BooleanValues.TRUE_STRING;

        PropertyBooleanSerializer iut = createInstanceUnderTest();
        MappingContext context = createMappingContext();
        String result = iut.toString(context, value);

        assertThat(result, is(expected));
    }

    @Test
    public void toString_whenPropertyValueIsFalse_mustReturnStringFalse()
            throws PropertySerializationException {

        Boolean value = Boolean.FALSE;
        String expected = BooleanValues.FALSE_STRING;

        PropertyBooleanSerializer iut = createInstanceUnderTest();
        MappingContext context = createMappingContext();
        String result = iut.toString(context, value);

        assertThat(result, is(expected));
    }

    PropertyBooleanSerializer createInstanceUnderTest() {
        return new PropertyBooleanSerializer();
    }

    MappingContext createMappingContext() {
        MappingContext context = new MappingContext();
        context.setMappingType(Boolean.class);
        return context;
    }

}