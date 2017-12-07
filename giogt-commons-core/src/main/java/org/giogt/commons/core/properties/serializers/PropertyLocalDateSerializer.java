package org.giogt.commons.core.properties.serializers;

import org.giogt.commons.core.properties.MappingContext;
import org.giogt.commons.core.properties.PropertySerializationException;
import org.giogt.commons.core.properties.PropertySerializer;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class PropertyLocalDateSerializer implements PropertySerializer<LocalDate> {

    @Override
    public Class<? extends LocalDate> handledType() {
        return LocalDate.class;
    }

    @Override
    public LocalDate fromString(
            MappingContext context,
            String stringValue)
            throws PropertySerializationException {

        if (stringValue == null) {
            return null;
        }

        try {
            return LocalDate.parse(stringValue);
        } catch (DateTimeParseException e) {
            throw new PropertySerializationException(
                    "cannot parse property value <" + stringValue + "> to local date",
                    e);
        }
    }

    @Override
    public String toString(
            MappingContext context,
            LocalDate value)
            throws PropertySerializationException {

        if (value == null) {
            return null;
        }

        return value.toString();
    }
}
