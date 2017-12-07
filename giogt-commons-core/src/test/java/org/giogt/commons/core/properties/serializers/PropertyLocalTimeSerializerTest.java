package org.giogt.commons.core.properties.serializers;

import org.giogt.commons.core.properties.MappingContext;
import org.giogt.commons.core.properties.PropertySerializationException;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.time.Month;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PropertyLocalTimeSerializerTest {

    @Test
    public void fromString_whenPropertyValueIsNull_mustReturnNull() {
        String propertyValue = null;

        PropertyLocalTimeSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        LocalTime result = iut.fromString(context, propertyValue);

        assertThat(result, is(nullValue()));
    }

    @Test
    public void fromString_whenPropertyValueIsAValidLocalTimeStringRepresentationWithNoNanos_mustReturnTheLocalTime() {
        String propertyValue = "10:11:12";
        LocalTime expected = LocalTime.of(10, 11, 12);

        PropertyLocalTimeSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        LocalTime result = iut.fromString(context, propertyValue);

        assertThat(result, is(expected));
    }

    @Test
    public void fromString_whenPropertyValueIsAValidLocalTimeStringRepresentationWithNanos_mustReturnTheLocalTime() {
        String propertyValue = "10:11:12.123456789";
        LocalTime expected = LocalTime.of(10, 11, 12, 123456789);

        PropertyLocalTimeSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        LocalTime result = iut.fromString(context, propertyValue);

        assertThat(result, is(expected));
    }

    @Test
    public void fromString_whenPropertyValueIsANonValidLocalTimeStringRepresentation_mustThrowSerializationException() {
        String propertyValue = "!?10:11:12?!";

        PropertyLocalTimeSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        assertThrows(PropertySerializationException.class, () ->
                iut.fromString(context, propertyValue)
        );
    }


    @Test
    public void toString_whenValueIsNull_mustReturnNull() {
        LocalTime value = null;

        PropertyLocalTimeSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        String result = iut.toString(context, value);

        assertThat(result, is(nullValue()));
    }

    @Test
    public void toString_whenValueIsAValidLocalTimeWithNoNanos_mustReturnTheLocalTimeStringRepresentation() {
        LocalTime value = LocalTime.of(10, 11, 12);
        String expected = "10:11:12";

        PropertyLocalTimeSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        String result = iut.toString(context, value);

        assertThat(result, is(expected));
    }

    @Test
    public void toString_whenValueIsAValidLocalTimeWithNanos_mustReturnTheLocalTimeStringRepresentation() {
        LocalTime value = LocalTime.of(10, 11, 12, 123456789);
        String expected = "10:11:12.123456789";

        PropertyLocalTimeSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        String result = iut.toString(context, value);

        assertThat(result, is(expected));
    }

    PropertyLocalTimeSerializer createSerializer() {
        return new PropertyLocalTimeSerializer();
    }

    MappingContext createMappingContext() {
        MappingContext context = new MappingContext();
        context.setMappingType(LocalTime.class);
        return context;
    }

}