package org.giogt.commons.core.properties.serializers;

import org.giogt.commons.core.properties.MappingContext;
import org.giogt.commons.core.properties.PropertySerializationException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PropertyLocalDateSerializerTest {

    @Test
    public void fromString_whenPropertyValueIsNull_mustReturnNull() {
        String propertyValue = null;

        PropertyLocalDateSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        LocalDate result = iut.fromString(context, propertyValue);

        assertThat(result, is(nullValue()));
    }

    @Test
    public void fromString_whenPropertyValueIsAValidLocalDateStringRepresentation_mustReturnTheLocalDate() {
        String propertyValue = "2000-07-22";
        LocalDate expected = LocalDate.of(2000, Month.JULY, 22);

        PropertyLocalDateSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        LocalDate result = iut.fromString(context, propertyValue);

        assertThat(result, is(expected));
    }

    @Test
    public void fromString_whenPropertyValueIsANonValidLocalDateStringRepresentation_mustThrowSerializationException() {
        String propertyValue = "!?2000-07-22?!";

        PropertyLocalDateSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        assertThrows(PropertySerializationException.class, () ->
                iut.fromString(context, propertyValue)
        );
    }


    @Test
    public void toString_whenValueIsNull_mustReturnNull() {
        LocalDate value = null;

        PropertyLocalDateSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        String result = iut.toString(context, value);

        assertThat(result, is(nullValue()));
    }

    @Test
    public void toString_whenValueIsAValidLocalDate_mustReturnTheLocalDateStringRepresentation() {
        LocalDate value = LocalDate.of(2000, Month.JULY, 22);
        String expected = "2000-07-22";

        PropertyLocalDateSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        String result = iut.toString(context, value);

        assertThat(result, is(expected));
    }

    PropertyLocalDateSerializer createSerializer() {
        return new PropertyLocalDateSerializer();
    }

    MappingContext createMappingContext() {
        MappingContext context = new MappingContext();
        context.setMappingType(LocalDate.class);
        return context;
    }

}