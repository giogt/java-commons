package org.giogt.commons.core.properties.serializers;

import org.giogt.commons.core.properties.MappingContext;
import org.giogt.commons.core.properties.PropertySerializationException;
import org.junit.jupiter.api.Test;

import java.time.ZoneOffset;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PropertyZoneOffsetSerializerTest {

    @Test
    public void fromString_whenPropertyValueIsNull_mustReturnNull() {
        String propertyValue = null;

        PropertyZoneOffsetSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        ZoneOffset result = iut.fromString(context, propertyValue);

        assertThat(result, is(nullValue()));
    }

    @Test
    public void fromString_whenPropertyValueIsAValidZoneOffsetStringRepresentation_mustReturnTheZoneOffset() {
        String propertyValue = "+02:00";
        ZoneOffset expected = ZoneOffset.of("+02:00");

        PropertyZoneOffsetSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        ZoneOffset result = iut.fromString(context, propertyValue);

        assertThat(result, is(expected));
    }

    @Test
    public void fromString_whenPropertyValueIsANonValidZoneOffsetStringRepresentation_mustThrowSerializationException() {
        String propertyValue = "-99:99";

        PropertyZoneOffsetSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        assertThrows(PropertySerializationException.class, () ->
                iut.fromString(context, propertyValue)
        );
    }


    @Test
    public void toString_whenValueIsNull_mustReturnNull() {
        ZoneOffset value = null;

        PropertyZoneOffsetSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        String result = iut.toString(context, value);

        assertThat(result, is(nullValue()));
    }

    @Test
    public void toString_whenValueIsAValidZoneOffset_mustReturnTheZoneOffsetStringRepresentation() {
        ZoneOffset value = ZoneOffset.of("+02:00");
        String expected = "+02:00";

        PropertyZoneOffsetSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        String result = iut.toString(context, value);

        assertThat(result, is(expected));
    }

    PropertyZoneOffsetSerializer createSerializer() {
        return new PropertyZoneOffsetSerializer();
    }

    MappingContext createMappingContext() {
        MappingContext context = new MappingContext();
        context.setMappingType(ZoneOffset.class);
        return context;
    }

}