package org.giogt.commons.core.properties.serializers;

import org.giogt.commons.core.properties.MappingContext;
import org.giogt.commons.core.properties.PropertySerializationException;
import org.giogt.commons.core.properties.PropertySerializer;

import java.time.DateTimeException;
import java.time.LocalTime;

public class PropertyLocalTimeSerializer implements PropertySerializer<LocalTime> {

    @Override
    public Class<? extends LocalTime> handledType() {
        return LocalTime.class;
    }

    @Override
    public LocalTime fromString(
            MappingContext context,
            String stringValue)
            throws PropertySerializationException {

        if (stringValue == null) {
            return null;
        }

        try {
            return LocalTime.parse(stringValue);
        } catch (DateTimeException e) {
            throw new PropertySerializationException(
                    "cannot parse property value <" + stringValue + "> to local time",
                    e);
        }
    }

    @Override
    public String toString(
            MappingContext context,
            LocalTime value)
            throws PropertySerializationException {

        if (value == null) {
            return null;
        }

        return value.toString();
    }
}
