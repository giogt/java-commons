package org.giogt.commons.core.properties.serializers;

import org.giogt.commons.core.properties.MappingContext;
import org.giogt.commons.core.properties.PropertySerializationException;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PropertyZoneIdSerializerTest {

    @Test
    public void fromString_whenPropertyValueIsNull_mustReturnNull() {
        String propertyValue = null;

        PropertyZoneIdSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        ZoneId result = iut.fromString(context, propertyValue);

        assertThat(result, is(nullValue()));
    }

    @Test
    public void fromString_whenPropertyValueIsAValidZoneIdStringRepresentation_mustReturnTheZoneId() {
        String propertyValue = "Europe/Rome";
        ZoneId expected = ZoneId.of("Europe/Rome");

        PropertyZoneIdSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        ZoneId result = iut.fromString(context, propertyValue);

        assertThat(result, is(expected));
    }

    @Test
    public void fromString_whenPropertyValueIsANonValidZoneIdStringRepresentation_mustThrowSerializationException() {
        String propertyValue = "NonExistingContinent/NonExistingCountry";

        PropertyZoneIdSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        assertThrows(PropertySerializationException.class, () ->
                iut.fromString(context, propertyValue)
        );
    }


    @Test
    public void toString_whenValueIsNull_mustReturnNull() {
        ZoneId value = null;

        PropertyZoneIdSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        String result = iut.toString(context, value);

        assertThat(result, is(nullValue()));
    }

    @Test
    public void toString_whenValueIsAValidZoneId_mustReturnTheZoneIdStringRepresentation() {
        ZoneId value = ZoneId.of("Europe/Rome");
        String expected = "Europe/Rome";

        PropertyZoneIdSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        String result = iut.toString(context, value);

        assertThat(result, is(expected));
    }

    PropertyZoneIdSerializer createSerializer() {
        return new PropertyZoneIdSerializer();
    }

    MappingContext createMappingContext() {
        MappingContext context = new MappingContext();
        context.setMappingType(ZoneId.class);
        return context;
    }

}