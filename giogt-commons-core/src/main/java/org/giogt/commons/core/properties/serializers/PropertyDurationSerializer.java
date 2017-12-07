package org.giogt.commons.core.properties.serializers;

import org.giogt.commons.core.properties.MappingContext;
import org.giogt.commons.core.properties.PropertySerializationException;
import org.giogt.commons.core.properties.PropertySerializer;

import java.time.DateTimeException;
import java.time.Duration;
import java.time.Instant;

public class PropertyDurationSerializer implements PropertySerializer<Duration> {

    @Override
    public Class<? extends Duration> handledType() {
        return Duration.class;
    }

    @Override
    public Duration fromString(
            MappingContext context,
            String stringValue)
            throws PropertySerializationException {

        if (stringValue == null) {
            return null;
        }

        try {
            return Duration.parse(stringValue);
        } catch (DateTimeException e) {
            throw new PropertySerializationException(
                    "cannot parse property value <" + stringValue + "> to duration",
                    e);
        }
    }

    @Override
    public String toString(
            MappingContext context,
            Duration value)
            throws PropertySerializationException {

        if (value == null) {
            return null;
        }

        return value.toString();
    }
}
