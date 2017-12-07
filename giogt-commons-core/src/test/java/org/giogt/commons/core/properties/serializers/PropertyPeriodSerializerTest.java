package org.giogt.commons.core.properties.serializers;

import org.giogt.commons.core.properties.MappingContext;
import org.giogt.commons.core.properties.PropertySerializationException;
import org.junit.jupiter.api.Test;

import java.time.Period;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PropertyPeriodSerializerTest {

    @Test
    public void fromString_whenPropertyValueIsNull_mustReturnNull() {
        String propertyValue = null;

        PropertyPeriodSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        Period result = iut.fromString(context, propertyValue);

        assertThat(result, is(nullValue()));
    }

    @Test
    public void fromString_whenPropertyValueIsAValidPeriodStringRepresentation_mustReturnThePeriod() {
        String propertyValue = "P1Y2M3D";
        Period expected = Period.ZERO
                .plusYears(1)
                .plusMonths(2)
                .plusDays(3);

        PropertyPeriodSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        Period result = iut.fromString(context, propertyValue);

        assertThat(result, is(expected));
    }

    @Test
    public void fromString_whenPropertyValueIsANonValidPeriodStringRepresentation_mustThrowSerializationException() {
        String propertyValue = "!?P1Y2M3D?!";

        PropertyPeriodSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        assertThrows(PropertySerializationException.class, () ->
                iut.fromString(context, propertyValue)
        );
    }


    @Test
    public void toString_whenValueIsNull_mustReturnNull() {
        Period value = null;

        PropertyPeriodSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        String result = iut.toString(context, value);

        assertThat(result, is(nullValue()));
    }

    @Test
    public void toString_whenValueIsAValidPeriod_mustReturnThePeriodStringRepresentation() {
        Period value = Period.ZERO
                .plusYears(1)
                .plusMonths(2)
                .plusDays(3);
        String expected = "P1Y2M3D";

        PropertyPeriodSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        String result = iut.toString(context, value);

        assertThat(result, is(expected));
    }

    PropertyPeriodSerializer createSerializer() {
        return new PropertyPeriodSerializer();
    }

    MappingContext createMappingContext() {
        MappingContext context = new MappingContext();
        context.setMappingType(Period.class);
        return context;
    }

}