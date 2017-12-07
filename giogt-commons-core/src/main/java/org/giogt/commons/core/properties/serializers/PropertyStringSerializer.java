package org.giogt.commons.core.properties.serializers;

import org.giogt.commons.core.properties.MappingContext;
import org.giogt.commons.core.properties.PropertySerializationException;
import org.giogt.commons.core.properties.PropertySerializer;

public class PropertyStringSerializer implements PropertySerializer<String> {

    @Override
    public Class<? extends String> handledType() {
        return String.class;
    }

    @Override
    public String fromString(
            MappingContext context,
            String stringValue)
            throws PropertySerializationException {

        return stringValue;
    }

    @Override
    public String toString(
            MappingContext context,
            String value)
            throws PropertySerializationException {

        return value;
    }
}
