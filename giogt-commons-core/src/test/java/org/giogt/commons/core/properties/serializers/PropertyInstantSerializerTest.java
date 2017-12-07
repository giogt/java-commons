package org.giogt.commons.core.properties.serializers;

import org.giogt.commons.core.properties.MappingContext;
import org.giogt.commons.core.properties.PropertySerializationException;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PropertyInstantSerializerTest {

    @Test
    public void fromString_whenPropertyValueIsNull_mustReturnNull() {
        String propertyValue = null;

        PropertyInstantSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        Instant result = iut.fromString(context, propertyValue);

        assertThat(result, is(nullValue()));
    }

    @Test
    public void fromString_whenPropertyValueIsAValidInstantStringRepresentation_mustReturnTheInstant() {
        String propertyValue = "2000-07-22T10:11:12Z";
        Instant expected = Instant.ofEpochMilli(964260672000L);

        PropertyInstantSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        Instant result = iut.fromString(context, propertyValue);

        assertThat(result, is(expected));
    }

    @Test
    public void fromString_whenPropertyValueIsANonValidInstantStringRepresentation_mustThrowSerializationException() {
        String propertyValue = "!?2000-07-22T10:11:12Z?!";

        PropertyInstantSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        assertThrows(PropertySerializationException.class, () ->
                iut.fromString(context, propertyValue)
        );
    }


    @Test
    public void toString_whenValueIsNull_mustReturnNull() {
        Instant value = null;

        PropertyInstantSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        String result = iut.toString(context, value);

        assertThat(result, is(nullValue()));
    }

    @Test
    public void toString_whenValueIsAValidInstant_mustReturnTheInstantStringRepresentation() {
        Instant value = Instant.ofEpochMilli(964260672000L);
        String expected = "2000-07-22T10:11:12Z";

        PropertyInstantSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        String result = iut.toString(context, value);

        assertThat(result, is(expected));
    }

    PropertyInstantSerializer createSerializer() {
        return new PropertyInstantSerializer();
    }

    MappingContext createMappingContext() {
        MappingContext context = new MappingContext();
        context.setMappingType(Instant.class);
        return context;
    }

}