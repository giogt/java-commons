package org.giogt.commons.core.properties.serializers;

import org.giogt.commons.core.properties.MappingContext;
import org.giogt.commons.core.properties.PropertySerializationException;
import org.giogt.commons.core.properties.PropertySerializer;

import java.time.DateTimeException;
import java.time.OffsetDateTime;

public class PropertyOffsetDateTimeSerializer implements PropertySerializer<OffsetDateTime> {

    @Override
    public Class<? extends OffsetDateTime> handledType() {
        return OffsetDateTime.class;
    }

    @Override
    public OffsetDateTime fromString(
            MappingContext context,
            String stringValue)
            throws PropertySerializationException {

        if (stringValue == null) {
            return null;
        }

        try {
            return OffsetDateTime.parse(stringValue);
        } catch (DateTimeException e) {
            throw new PropertySerializationException(
                    "cannot parse property value <" + stringValue + "> to offset date time",
                    e);
        }
    }

    @Override
    public String toString(
            MappingContext context,
            OffsetDateTime value)
            throws PropertySerializationException {

        if (value == null) {
            return null;
        }

        return value.toString();
    }
}
