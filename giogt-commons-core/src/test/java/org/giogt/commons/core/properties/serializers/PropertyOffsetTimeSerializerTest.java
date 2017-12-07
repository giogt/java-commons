package org.giogt.commons.core.properties.serializers;

import org.giogt.commons.core.properties.MappingContext;
import org.giogt.commons.core.properties.PropertySerializationException;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PropertyOffsetTimeSerializerTest {

    @Test
    public void fromString_whenPropertyValueIsNull_mustReturnNull() {
        String propertyValue = null;

        PropertyOffsetTimeSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        OffsetTime result = iut.fromString(context, propertyValue);

        assertThat(result, is(nullValue()));
    }

    @Test
    public void fromString_whenPropertyValueIsAValidOffsetTimeStringRepresentationWithNoNanos_mustReturnTheOffsetTime() {
        String propertyValue = "10:11:12+08:00";
        OffsetTime expected = OffsetTime.of(
                LocalTime.of(10, 11, 12),
                ZoneOffset.of("+08:00"));

        PropertyOffsetTimeSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        OffsetTime result = iut.fromString(context, propertyValue);

        assertThat(result, is(expected));
    }

    @Test
    public void fromString_whenPropertyValueIsAValidOffsetTimeStringRepresentationWithNanos_mustReturnTheOffsetTime() {
        String propertyValue = "10:11:12.123456789+08:00";
        OffsetTime expected = OffsetTime.of(
                LocalTime.of(10, 11, 12, 123456789),
                ZoneOffset.of("+08:00"));

        PropertyOffsetTimeSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        OffsetTime result = iut.fromString(context, propertyValue);

        assertThat(result, is(expected));
    }

    @Test
    public void fromString_whenPropertyValueIsANonValidOffsetTimeStringRepresentation_mustThrowSerializationException() {
        String propertyValue = "!?10:11:12+08:00?!";

        PropertyOffsetTimeSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        assertThrows(PropertySerializationException.class, () ->
                iut.fromString(context, propertyValue)
        );
    }


    @Test
    public void toString_whenValueIsNull_mustReturnNull() {
        OffsetTime value = null;

        PropertyOffsetTimeSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        String result = iut.toString(context, value);

        assertThat(result, is(nullValue()));
    }

    @Test
    public void toString_whenValueIsAValidOffsetTimeWithNoNanos_mustReturnTheOffsetTimeStringRepresentation() {
        OffsetTime value = OffsetTime.of(
                LocalTime.of(10, 11, 12),
                ZoneOffset.of("+08:00"));
        String expected = "10:11:12+08:00";

        PropertyOffsetTimeSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        String result = iut.toString(context, value);

        assertThat(result, is(expected));
    }

    @Test
    public void toString_whenValueIsAValidOffsetTimeWithNanos_mustReturnTheOffsetTimeStringRepresentation() {
        OffsetTime value = OffsetTime.of(
                LocalTime.of(10, 11, 12, 123456789),
                ZoneOffset.of("+08:00"));
        String expected = "10:11:12.123456789+08:00";

        PropertyOffsetTimeSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        String result = iut.toString(context, value);

        assertThat(result, is(expected));
    }

    PropertyOffsetTimeSerializer createSerializer() {
        return new PropertyOffsetTimeSerializer();
    }

    MappingContext createMappingContext() {
        MappingContext context = new MappingContext();
        context.setMappingType(OffsetTime.class);
        return context;
    }

}