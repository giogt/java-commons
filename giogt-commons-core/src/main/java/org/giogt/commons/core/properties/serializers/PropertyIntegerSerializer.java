package org.giogt.commons.core.properties.serializers;

import org.giogt.commons.core.properties.MappingContext;
import org.giogt.commons.core.properties.PropertySerializationException;
import org.giogt.commons.core.properties.PropertySerializer;

public class PropertyIntegerSerializer implements PropertySerializer<Integer> {

    @Override
    public Class<? extends Integer> handledType() {
        return Integer.class;
    }

    @Override
    public Integer fromString(
            MappingContext context,
            String stringValue)
            throws PropertySerializationException {

        if (stringValue == null) {
            return null;
        }

        try {
            return Integer.valueOf(stringValue);
        } catch (NumberFormatException e) {
            throw new PropertySerializationException(
                    "cannot parse property value <" + stringValue + "> to integer",
                    e);
        }
    }

    @Override
    public String toString(
            MappingContext context,
            Integer value)
            throws PropertySerializationException {

        if (value == null) {
            return null;
        }
        return Integer.toString(value);
    }
}
