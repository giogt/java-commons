package org.giogt.commons.core.properties.serializers;

import org.giogt.commons.core.properties.MappingContext;
import org.giogt.commons.core.properties.PropertySerializationException;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PropertyBooleanSerializerTest {

    @Test
    public void fromString_whenPropertyValueIsNull_mustReturnNull() {
        String propertyValue = null;

        PropertyBooleanSerializer instance = createSerializer();
        MappingContext context = createMappingContext();
        Boolean result = instance.fromString(context, propertyValue);

        assertThat(result, is(nullValue()));
    }

    @Test
    public void fromString_whenPropertyValueIsStringTrue_mustReturnTrue() {
        String propertyValue = BooleanValues.TRUE_STRING;
        Boolean expected = Boolean.TRUE;

        PropertyBooleanSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        Boolean result = iut.fromString(context, propertyValue);

        assertThat(result, is(expected));
    }

    @Test
    public void fromString_whenPropertyValueIsStringFalse_mustReturnFalse() {
        String propertyValue = BooleanValues.FALSE_STRING;
        Boolean expected = Boolean.FALSE;

        PropertyBooleanSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        Boolean result = iut.fromString(context, propertyValue);

        assertThat(result, is(expected));
    }

    @Test
    public void fromString_whenPropertyValueIsANonValidBooleanStringRepresentation_mustThrowSerializationException() {
        String propertyValue = "non-valid-boolean-representation";

        PropertyBooleanSerializer iut = createSerializer();
        MappingContext context = createMappingContext();

        assertThrows(PropertySerializationException.class, () ->
                iut.fromString(context, propertyValue)
        );
    }


    @Test
    public void toString_whenValueIsNull_mustReturnNull() {
        Boolean value = null;

        PropertyBooleanSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        String result = iut.toString(context, value);

        assertThat(result, is(nullValue()));
    }

    @Test
    public void toString_whenValueIsTrue_mustReturnStringTrue() {
        Boolean value = Boolean.TRUE;
        String expected = BooleanValues.TRUE_STRING;

        PropertyBooleanSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        String result = iut.toString(context, value);

        assertThat(result, is(expected));
    }

    @Test
    public void toString_whenPropertyValueIsFalse_mustReturnStringFalse() {
        Boolean value = Boolean.FALSE;
        String expected = BooleanValues.FALSE_STRING;

        PropertyBooleanSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        String result = iut.toString(context, value);

        assertThat(result, is(expected));
    }

    PropertyBooleanSerializer createSerializer() {
        return new PropertyBooleanSerializer();
    }

    MappingContext createMappingContext() {
        MappingContext context = new MappingContext();
        context.setMappingType(Boolean.class);
        return context;
    }

}