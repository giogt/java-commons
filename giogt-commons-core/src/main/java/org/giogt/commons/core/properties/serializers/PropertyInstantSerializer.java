package org.giogt.commons.core.properties.serializers;

import org.giogt.commons.core.properties.MappingContext;
import org.giogt.commons.core.properties.PropertySerializationException;
import org.giogt.commons.core.properties.PropertySerializer;

import java.time.DateTimeException;
import java.time.Instant;

public class PropertyInstantSerializer implements PropertySerializer<Instant> {

    @Override
    public Class<? extends Instant> handledType() {
        return null;
    }

    @Override
    public Instant fromString(
            MappingContext context,
            String stringValue)
            throws PropertySerializationException {

        if (stringValue == null) {
            return null;
        }

        try {
            return Instant.parse(stringValue);
        } catch (DateTimeException e) {
            throw new PropertySerializationException(
                    "cannot parse property value <" + stringValue + "> to instant",
                    e);
        }
    }

    @Override
    public String toString(
            MappingContext context,
            Instant value)
            throws PropertySerializationException {

        if (value == null) {
            return null;
        }

        return value.toString();
    }

}
