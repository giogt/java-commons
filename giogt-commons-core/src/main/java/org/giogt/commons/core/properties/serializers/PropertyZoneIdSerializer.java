package org.giogt.commons.core.properties.serializers;

import org.giogt.commons.core.properties.MappingContext;
import org.giogt.commons.core.properties.PropertySerializationException;
import org.giogt.commons.core.properties.PropertySerializer;

import java.time.DateTimeException;
import java.time.ZoneId;

public class PropertyZoneIdSerializer implements PropertySerializer<ZoneId> {

    @Override
    public Class<? extends ZoneId> handledType() {
        return ZoneId.class;
    }

    @Override
    public ZoneId fromString(
            MappingContext context,
            String stringValue)
            throws PropertySerializationException {

        if (stringValue == null) {
            return null;
        }

        try {
            return ZoneId.of(stringValue);
        } catch (DateTimeException e) {
            throw new PropertySerializationException(
                    "cannot parse property value <" + stringValue + "> to zone id",
                    e);
        }
    }

    @Override
    public String toString(
            MappingContext context,
            ZoneId value)
            throws PropertySerializationException {

        if (value == null) {
            return null;
        }

        return value.toString();
    }
}
