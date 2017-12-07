package org.giogt.commons.core.properties.serializers;

import org.giogt.commons.core.properties.MappingContext;
import org.giogt.commons.core.properties.PropertySerializationException;
import org.giogt.commons.core.properties.PropertySerializer;

import java.time.DateTimeException;
import java.time.ZonedDateTime;

public class PropertyZonedDateTimeSerializer implements PropertySerializer<ZonedDateTime> {

    @Override
    public Class<? extends ZonedDateTime> handledType() {
        return ZonedDateTime.class;
    }

    @Override
    public ZonedDateTime fromString(
            MappingContext context,
            String stringValue)
            throws PropertySerializationException {

        if (stringValue == null) {
            return null;
        }

        try {
            return ZonedDateTime.parse(stringValue);
        } catch (DateTimeException e) {
            throw new PropertySerializationException(
                    "cannot parse property value <" + stringValue + "> to zoned date time",
                    e);
        }
    }

    @Override
    public String toString(
            MappingContext context,
            ZonedDateTime value)
            throws PropertySerializationException {

        if (value == null) {
            return null;
        }

        return value.toString();
    }
}
