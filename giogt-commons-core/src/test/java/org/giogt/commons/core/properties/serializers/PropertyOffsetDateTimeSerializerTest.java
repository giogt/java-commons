package org.giogt.commons.core.properties.serializers;

import org.giogt.commons.core.properties.MappingContext;
import org.giogt.commons.core.properties.PropertySerializationException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PropertyOffsetDateTimeSerializerTest {

    @Test
    public void fromString_whenPropertyValueIsNull_mustReturnNull() {
        String propertyValue = null;

        PropertyOffsetDateTimeSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        OffsetDateTime result = iut.fromString(context, propertyValue);

        assertThat(result, is(nullValue()));
    }

    @Test
    public void fromString_whenPropertyValueIsAValidOffsetDateTimeStringRepresentationWithNoNanos_mustReturnTheOffsetDateTime() {
        String propertyValue = "2000-07-22T10:11:12+08:00";
        OffsetDateTime expected = OffsetDateTime.of(
                LocalDateTime.of(
                        2000,
                        Month.JULY,
                        22,
                        10,
                        11,
                        12),
                ZoneOffset.of("+08:00"));

        PropertyOffsetDateTimeSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        OffsetDateTime result = iut.fromString(context, propertyValue);

        assertThat(result, is(expected));
    }

    @Test
    public void fromString_whenPropertyValueIsAValidOffsetDateTimeStringRepresentationWithNanos_mustReturnTheOffsetDateTime() {
        String propertyValue = "2000-07-22T10:11:12.123456789+08:00";
        OffsetDateTime expected = OffsetDateTime.of(
                LocalDateTime.of(
                        2000,
                        Month.JULY,
                        22,
                        10,
                        11,
                        12,
                        123456789),
                ZoneOffset.of("+08:00"));

        PropertyOffsetDateTimeSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        OffsetDateTime result = iut.fromString(context, propertyValue);

        assertThat(result, is(expected));
    }

    @Test
    public void fromString_whenPropertyValueIsANonValidOffsetDateTimeStringRepresentation_mustThrowSerializationException() {
        String propertyValue = "!?2000-07-22T10:11:12+08:00?!";

        PropertyOffsetDateTimeSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        assertThrows(PropertySerializationException.class, () ->
                iut.fromString(context, propertyValue)
        );
    }


    @Test
    public void toString_whenValueIsNull_mustReturnNull() {
        OffsetDateTime value = null;

        PropertyOffsetDateTimeSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        String result = iut.toString(context, value);

        assertThat(result, is(nullValue()));
    }

    @Test
    public void toString_whenValueIsAValidOffsetDateTimeWithNoNanos_mustReturnTheOffsetDateTimeStringRepresentation() {
        OffsetDateTime value = OffsetDateTime.of(
                LocalDateTime.of(
                        2000,
                        Month.JULY,
                        22,
                        10,
                        11,
                        12),
                ZoneOffset.of("+08:00"));
        String expected = "2000-07-22T10:11:12+08:00";

        PropertyOffsetDateTimeSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        String result = iut.toString(context, value);

        assertThat(result, is(expected));
    }

    @Test
    public void toString_whenValueIsAValidOffsetDateTimeWithNanos_mustReturnTheOffsetDateTimeStringRepresentation() {
        OffsetDateTime value = OffsetDateTime.of(
                LocalDateTime.of(
                        2000,
                        Month.JULY,
                        22,
                        10,
                        11,
                        12,
                        123456789),
                ZoneOffset.of("+08:00"));
        String expected = "2000-07-22T10:11:12.123456789+08:00";

        PropertyOffsetDateTimeSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        String result = iut.toString(context, value);

        assertThat(result, is(expected));
    }

    PropertyOffsetDateTimeSerializer createSerializer() {
        return new PropertyOffsetDateTimeSerializer();
    }

    MappingContext createMappingContext() {
        MappingContext context = new MappingContext();
        context.setMappingType(OffsetDateTime.class);
        return context;
    }

}