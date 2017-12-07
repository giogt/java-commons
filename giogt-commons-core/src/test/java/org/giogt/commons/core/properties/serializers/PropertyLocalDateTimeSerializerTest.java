package org.giogt.commons.core.properties.serializers;

import org.giogt.commons.core.properties.MappingContext;
import org.giogt.commons.core.properties.PropertySerializationException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PropertyLocalDateTimeSerializerTest {

    @Test
    public void fromString_whenPropertyValueIsNull_mustReturnNull() {
        String propertyValue = null;

        PropertyLocalDateTimeSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        LocalDateTime result = iut.fromString(context, propertyValue);

        assertThat(result, is(nullValue()));
    }

    @Test
    public void fromString_whenPropertyValueIsAValidLocalDateTimeStringRepresentationWithNoNanos_mustReturnTheLocalDateTime() {
        String propertyValue = "2000-07-22T10:11:12";
        LocalDateTime expected = LocalDateTime.of(
                2000,
                Month.JULY,
                22,
                10,
                11,
                12);

        PropertyLocalDateTimeSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        LocalDateTime result = iut.fromString(context, propertyValue);

        assertThat(result, is(expected));
    }

    @Test
    public void fromString_whenPropertyValueIsAValidLocalDateTimeStringRepresentationWithNanos_mustReturnTheLocalDateTime() {
        String propertyValue = "2000-07-22T10:11:12.123456789";
        LocalDateTime expected = LocalDateTime.of(
                2000,
                Month.JULY,
                22,
                10,
                11,
                12,
                123456789);

        PropertyLocalDateTimeSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        LocalDateTime result = iut.fromString(context, propertyValue);

        assertThat(result, is(expected));
    }

    @Test
    public void fromString_whenPropertyValueIsANonValidLocalDateTimeStringRepresentation_mustThrowSerializationException() {
        String propertyValue = "!?2000-07-22T10:11:12?!";

        PropertyLocalDateTimeSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        assertThrows(PropertySerializationException.class, () ->
                iut.fromString(context, propertyValue)
        );
    }


    @Test
    public void toString_whenValueIsNull_mustReturnNull() {
        LocalDateTime value = null;

        PropertyLocalDateTimeSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        String result = iut.toString(context, value);

        assertThat(result, is(nullValue()));
    }

    @Test
    public void toString_whenValueIsAValidLocalDateTimeWithNoNanos_mustReturnTheLocalDateTimeStringRepresentation() {
        LocalDateTime value = LocalDateTime.of(
                2000,
                Month.JULY,
                22,
                10,
                11,
                12);
        String expected = "2000-07-22T10:11:12";

        PropertyLocalDateTimeSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        String result = iut.toString(context, value);

        assertThat(result, is(expected));
    }

    @Test
    public void toString_whenValueIsAValidLocalDateTimeWithNanos_mustReturnTheLocalDateTimeStringRepresentation() {
        LocalDateTime value = LocalDateTime.of(
                2000,
                Month.JULY,
                22,
                10,
                11,
                12,
                123456789);
        String expected = "2000-07-22T10:11:12.123456789";

        PropertyLocalDateTimeSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        String result = iut.toString(context, value);

        assertThat(result, is(expected));
    }

    PropertyLocalDateTimeSerializer createSerializer() {
        return new PropertyLocalDateTimeSerializer();
    }

    MappingContext createMappingContext() {
        MappingContext context = new MappingContext();
        context.setMappingType(LocalDateTime.class);
        return context;
    }

}