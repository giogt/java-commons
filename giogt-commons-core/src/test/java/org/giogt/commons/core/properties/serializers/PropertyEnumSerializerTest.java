package org.giogt.commons.core.properties.serializers;

import org.giogt.commons.core.properties.MappingContext;
import org.giogt.commons.core.properties.PropertySerializationException;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PropertyEnumSerializerTest {

    enum TestEnum {
        FOO,
        BAR
    }

    @Test
    public void fromString_whenPropertyValueIsNull_mustReturnNull()
            throws PropertySerializationException {

        String propertyValue = null;

        PropertyEnumSerializer iut = createInstanceUnderTest();
        MappingContext context = createMappingContext();
        TestEnum result = (TestEnum) iut.fromString(context, propertyValue);

        assertNull(result);
    }

    @Test
    public void fromString_whenPropertyValueIsAValidEnumStringRepresentation_mustReturnTheEnum()
            throws PropertySerializationException {

        String propertyValue = TestEnum.FOO.toString();
        TestEnum expected = TestEnum.FOO;

        PropertyEnumSerializer iut = createInstanceUnderTest();
        MappingContext context = createMappingContext();
        TestEnum result = (TestEnum) iut.fromString(context, propertyValue);

        assertThat(result, is(expected));
    }

    @Test
    public void toString_whenValueIsNull_mustReturnNull()
            throws PropertySerializationException {

        TestEnum value = null;

        PropertyEnumSerializer iut = createInstanceUnderTest();
        MappingContext context = createMappingContext();
        String result = iut.toString(context, value);

        assertNull(result);
    }

    @Test
    public void toString_whenValueIsAValidEnum_mustReturnTheEnumStringRepresentation()
            throws PropertySerializationException {

        TestEnum value = TestEnum.BAR;
        String expected = TestEnum.BAR.toString();

        PropertyEnumSerializer iut = createInstanceUnderTest();
        MappingContext context = createMappingContext();
        String result = iut.toString(context, value);

        assertThat(result, is(expected));
    }

    PropertyEnumSerializer createInstanceUnderTest() {
        PropertyEnumSerializer propertyEnumSerializer = new PropertyEnumSerializer();
        return propertyEnumSerializer;
    }

    MappingContext createMappingContext() {
        MappingContext context = new MappingContext();
        context.setMappingType(TestEnum.class);
        return context;
    }

}