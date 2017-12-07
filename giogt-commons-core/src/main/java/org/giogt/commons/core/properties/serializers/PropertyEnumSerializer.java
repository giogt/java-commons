package org.giogt.commons.core.properties.serializers;

import org.giogt.commons.core.properties.MappingContext;
import org.giogt.commons.core.properties.PropertySerializationException;
import org.giogt.commons.core.properties.PropertySerializer;

public class PropertyEnumSerializer implements PropertySerializer<Enum> {

    @Override
    public Class<? extends Enum> handledType() {
        return Enum.class;
    }

    @Override
    public Enum fromString(
            MappingContext context,
            String stringValue)
            throws PropertySerializationException {

        if (stringValue == null) {
            return null;
        }

        Class<? extends Enum> enumType = getEnumType(context);

        try {
            return Enum.valueOf(enumType, stringValue);
        } catch (IllegalArgumentException e) {
            throw new PropertySerializationException(
                    "cannot convert string property value to <" + enumType + ">: " +
                            "value = " + stringValue);
        }
    }

    private Class<? extends Enum> getEnumType(MappingContext context) {
        if (!Enum.class.isAssignableFrom(context.getMappingType())) {
            throw new PropertySerializationException("unexpected requested type <" +
                    context.getMappingType() +
                    "> (a Java enumeration was expected)");
        }
        return (Class<? extends Enum>) context.getMappingType();
    }

    @Override
    public String toString(
            MappingContext context,
            Enum value)
            throws PropertySerializationException {

        if (value == null) {
            return null;
        }

        return String.valueOf(value);
    }
}
