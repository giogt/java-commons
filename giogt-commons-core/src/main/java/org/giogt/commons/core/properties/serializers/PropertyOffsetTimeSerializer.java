package org.giogt.commons.core.properties.serializers;

import org.giogt.commons.core.properties.MappingContext;
import org.giogt.commons.core.properties.PropertySerializationException;
import org.giogt.commons.core.properties.PropertySerializer;

import java.time.DateTimeException;
import java.time.OffsetTime;

public class PropertyOffsetTimeSerializer implements PropertySerializer<OffsetTime> {

    @Override
    public Class<? extends OffsetTime> handledType() {
        return OffsetTime.class;
    }

    @Override
    public OffsetTime fromString(
            MappingContext context,
            String stringValue)
            throws PropertySerializationException {

        if (stringValue == null) {
            return null;
        }

        try {
            return OffsetTime.parse(stringValue);
        } catch (DateTimeException e) {
            throw new PropertySerializationException(
                    "cannot parse property value <" + stringValue + "> to offset time",
                    e);
        }
    }

    @Override
    public String toString(
            MappingContext context,
            OffsetTime value)
            throws PropertySerializationException {

        if (value == null) {
            return null;
        }

        return value.toString();
    }
}
