package org.giogt.commons.core.properties.serializers;

import org.giogt.commons.core.properties.MappingContext;
import org.giogt.commons.core.properties.PropertySerializationException;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PropertyEnumSerializerTest {

    enum TestEnum {
        FOO,
        BAR
    }

    @Test
    public void fromString_whenPropertyValueIsNull_mustReturnNull() {
        String propertyValue = null;

        PropertyEnumSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        TestEnum result = (TestEnum) iut.fromString(context, propertyValue);

        assertThat(result, is(nullValue()));
    }

    @Test
    public void fromString_whenPropertyValueIsAValidEnumStringRepresentation_mustReturnTheEnum() {
        String propertyValue = TestEnum.FOO.toString();
        TestEnum expected = TestEnum.FOO;

        PropertyEnumSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        TestEnum result = (TestEnum) iut.fromString(context, propertyValue);

        assertThat(result, is(expected));
    }

    @Test
    public void fromString_whenPropertyValueIsANonValidEnumStringRepresentation_mustThrowSerializationException() {
        String propertyValue = "NON_VALID_ENUM_STRING_REPRESENTATION";

        PropertyEnumSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        assertThrows(PropertySerializationException.class, () ->
                iut.fromString(context, propertyValue)
        );
    }


    @Test
    public void toString_whenValueIsNull_mustReturnNull() {
        TestEnum value = null;

        PropertyEnumSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        String result = iut.toString(context, value);

        assertThat(result, is(nullValue()));
    }

    @Test
    public void toString_whenValueIsAValidEnum_mustReturnTheEnumStringRepresentation() {
        TestEnum value = TestEnum.BAR;
        String expected = TestEnum.BAR.toString();

        PropertyEnumSerializer iut = createSerializer();
        MappingContext context = createMappingContext();
        String result = iut.toString(context, value);

        assertThat(result, is(expected));
    }

    PropertyEnumSerializer createSerializer() {
        PropertyEnumSerializer propertyEnumSerializer = new PropertyEnumSerializer();
        return propertyEnumSerializer;
    }

    MappingContext createMappingContext() {
        MappingContext context = new MappingContext();
        context.setMappingType(TestEnum.class);
        return context;
    }

}