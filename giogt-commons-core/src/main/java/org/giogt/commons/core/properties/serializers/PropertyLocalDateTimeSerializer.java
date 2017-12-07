package org.giogt.commons.core.properties.serializers;

import org.giogt.commons.core.properties.MappingContext;
import org.giogt.commons.core.properties.PropertySerializationException;
import org.giogt.commons.core.properties.PropertySerializer;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class PropertyLocalDateTimeSerializer implements PropertySerializer<LocalDateTime> {

    @Override
    public Class<? extends LocalDateTime> handledType() {
        return LocalDateTime.class;
    }

    @Override
    public LocalDateTime fromString(
            MappingContext context,
            String stringValue)
            throws PropertySerializationException {

        if (stringValue == null) {
            return null;
        }

        try {
            return LocalDateTime.parse(stringValue);
        } catch (DateTimeParseException e) {
            throw new PropertySerializationException(
                    "cannot parse property value <" + stringValue + "> to local date time",
                    e);
        }
    }

    @Override
    public String toString(
            MappingContext context,
            LocalDateTime value)
            throws PropertySerializationException {

        if (value == null) {
            return null;
        }

        return value.toString();
    }
}
