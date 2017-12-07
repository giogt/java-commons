package org.giogt.commons.core.properties.serializers;

import org.giogt.commons.core.properties.MappingContext;
import org.giogt.commons.core.properties.PropertySerializationException;
import org.giogt.commons.core.properties.PropertySerializer;

import java.time.DateTimeException;
import java.time.ZoneOffset;

public class PropertyZoneOffsetSerializer implements PropertySerializer<ZoneOffset> {

    @Override
    public Class<? extends ZoneOffset> handledType() {
        return ZoneOffset.class;
    }

    @Override
    public ZoneOffset fromString(
            MappingContext context,
            String stringValue)
            throws PropertySerializationException {

        if (stringValue == null) {
            return null;
        }

        try {
            return ZoneOffset.of(stringValue);
        } catch (DateTimeException e) {
            throw new PropertySerializationException(
                    "cannot parse property value <" + stringValue + "> to zone offset",
                    e);
        }
    }

    @Override
    public String toString(
            MappingContext context,
            ZoneOffset value)
            throws PropertySerializationException {

        if (value == null) {
            return null;
        }

        return value.toString();
    }
}
