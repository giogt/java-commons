package org.giogt.commons.core.properties.serializers;

import org.giogt.commons.core.properties.MappingContext;
import org.giogt.commons.core.properties.PropertySerializationException;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PropertyDurationSerializerTest {

    @Test
    public void fromString_whenPropertyValueIsNull_mustReturnNull() {
        String propertyValue = null;

        PropertyDurationSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        Duration result = iut.fromString(context, propertyValue);

        assertThat(result, is(nullValue()));
    }

    @Test
    public void fromString_whenPropertyValueIsAValidDurationStringRepresentation_mustReturnTheDuration() {
        String propertyValue = "P123DT10H11M12S";
        Duration expected = Duration.ZERO
                .plusDays(123L)
                .plusHours(10)
                .plusMinutes(11)
                .plusSeconds(12);

        PropertyDurationSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        Duration result = iut.fromString(context, propertyValue);

        assertThat(result, is(expected));
    }

    @Test
    public void fromString_whenPropertyValueIsANonValidDurationStringRepresentation_mustThrowSerializationException() {
        String propertyValue = "!?P123DT10H11M12S?!";

        PropertyDurationSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        assertThrows(PropertySerializationException.class, () ->
                iut.fromString(context, propertyValue)
        );
    }


    @Test
    public void toString_whenValueIsNull_mustReturnNull() {
        Duration value = null;

        PropertyDurationSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        String result = iut.toString(context, value);

        assertThat(result, is(nullValue()));
    }

    @Test
    public void toString_whenValueIsAValidDuration_mustReturnTheDurationStringRepresentation() {
        Duration value = Duration.ZERO
                .plusDays(123L)
                .plusHours(10)
                .plusMinutes(11)
                .plusSeconds(12);
        String expected = "PT2962H11M12S";

        PropertyDurationSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        String result = iut.toString(context, value);

        assertThat(result, is(expected));
    }

    PropertyDurationSerializer createSerializer() {
        return new PropertyDurationSerializer();
    }

    MappingContext createMappingContext() {
        MappingContext context = new MappingContext();
        context.setMappingType(Duration.class);
        return context;
    }

}