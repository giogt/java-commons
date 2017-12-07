package org.giogt.commons.core.properties.serializers;

import org.giogt.commons.core.properties.MappingContext;
import org.giogt.commons.core.properties.PropertySerializationException;
import org.giogt.commons.core.properties.PropertySerializer;

public class PropertyLongSerializer implements PropertySerializer<Long> {

    @Override
    public Class<? extends Long> handledType() {
        return Long.class;
    }

    @Override
    public Long fromString(
            MappingContext context,
            String stringValue)
            throws PropertySerializationException {

        if (stringValue == null) {
            return null;
        }

        try {
            return Long.valueOf(stringValue);
        } catch (NumberFormatException e) {
            throw new PropertySerializationException(
                    "cannot parse property value <" + stringValue + "> to long",
                    e);
        }
    }

    @Override
    public String toString(
            MappingContext context,
            Long value)
            throws PropertySerializationException {

        if (value == null) {
            return null;
        }
        return Long.toString(value);
    }
}
