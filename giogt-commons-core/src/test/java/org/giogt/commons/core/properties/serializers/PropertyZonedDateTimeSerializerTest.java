package org.giogt.commons.core.properties.serializers;

import org.giogt.commons.core.properties.MappingContext;
import org.giogt.commons.core.properties.PropertySerializationException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PropertyZonedDateTimeSerializerTest {

    @Test
    public void fromString_whenPropertyValueIsNull_mustReturnNull() {
        String propertyValue = null;

        PropertyZonedDateTimeSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        ZonedDateTime result = iut.fromString(context, propertyValue);

        assertThat(result, is(nullValue()));
    }

    @Test
    public void fromString_whenPropertyValueIsAValidZonedDateTimeStringRepresentationWithNoNanos_mustReturnTheZonedDateTime() {
        String propertyValue = "2000-07-22T10:11:12+02:00[Europe/Rome]";
        ZonedDateTime expected = ZonedDateTime.of(
                LocalDateTime.of(
                        2000,
                        Month.JULY,
                        22,
                        10,
                        11,
                        12),
                ZoneId.of("Europe/Rome"));

        PropertyZonedDateTimeSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        ZonedDateTime result = iut.fromString(context, propertyValue);

        assertThat(result, is(expected));
    }

    @Test
    public void fromString_whenPropertyValueIsAValidZonedDateTimeStringRepresentationWithNanos_mustReturnTheZonedDateTime() {
        String propertyValue = "2000-07-22T10:11:12.123456789+02:00[Europe/Rome]";
        ZonedDateTime expected = ZonedDateTime.of(
                LocalDateTime.of(
                        2000,
                        Month.JULY,
                        22,
                        10,
                        11,
                        12,
                        123456789),
                ZoneId.of("Europe/Rome"));

        PropertyZonedDateTimeSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        ZonedDateTime result = iut.fromString(context, propertyValue);

        assertThat(result, is(expected));
    }

    @Test
    public void fromString_whenPropertyValueIsANonValidZonedDateTimeStringRepresentation_mustThrowSerializationException() {
        String propertyValue = "!?2000-07-22T10:11:12+02:00[Europe/Rome]?!";

        PropertyZonedDateTimeSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        assertThrows(PropertySerializationException.class, () ->
                iut.fromString(context, propertyValue)
        );
    }


    @Test
    public void toString_whenValueIsNull_mustReturnNull() {
        ZonedDateTime value = null;

        PropertyZonedDateTimeSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        String result = iut.toString(context, value);

        assertThat(result, is(nullValue()));
    }

    @Test
    public void toString_whenValueIsAValidZonedDateTimeWithNoNanos_mustReturnTheZonedDateTimeStringRepresentation() {
        ZonedDateTime value = ZonedDateTime.of(
                LocalDateTime.of(
                        2000,
                        Month.JULY,
                        22,
                        10,
                        11,
                        12),
                ZoneId.of("Europe/Rome"));
        String expected = "2000-07-22T10:11:12+02:00[Europe/Rome]";

        PropertyZonedDateTimeSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        String result = iut.toString(context, value);

        assertThat(result, is(expected));
    }

    @Test
    public void toString_whenValueIsAValidZonedDateTimeWithNanos_mustReturnTheZonedDateTimeStringRepresentation() {
        ZonedDateTime value = ZonedDateTime.of(
                LocalDateTime.of(
                        2000,
                        Month.JULY,
                        22,
                        10,
                        11,
                        12,
                        123456789),
                ZoneId.of("Europe/Rome"));
        String expected = "2000-07-22T10:11:12.123456789+02:00[Europe/Rome]";

        PropertyZonedDateTimeSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        String result = iut.toString(context, value);

        assertThat(result, is(expected));
    }

    PropertyZonedDateTimeSerializer createSerializer() {
        return new PropertyZonedDateTimeSerializer();
    }

    MappingContext createMappingContext() {
        MappingContext context = new MappingContext();
        context.setMappingType(ZonedDateTime.class);
        return context;
    }

}