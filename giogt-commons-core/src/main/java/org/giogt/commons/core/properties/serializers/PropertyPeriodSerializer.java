package org.giogt.commons.core.properties.serializers;

import org.giogt.commons.core.properties.MappingContext;
import org.giogt.commons.core.properties.PropertySerializationException;
import org.giogt.commons.core.properties.PropertySerializer;

import java.time.DateTimeException;
import java.time.Period;

public class PropertyPeriodSerializer implements PropertySerializer<Period> {

    @Override
    public Class<? extends Period> handledType() {
        return Period.class;
    }

    @Override
    public Period fromString(
            MappingContext context,
            String stringValue)
            throws PropertySerializationException {

        if (stringValue == null) {
            return null;
        }

        try {
            return Period.parse(stringValue);
        } catch (DateTimeException e) {
            throw new PropertySerializationException(
                    "cannot parse property value <" + stringValue + "> to period",
                    e);
        }
    }

    @Override
    public String toString(
            MappingContext context,
            Period value)
            throws PropertySerializationException {

        if (value == null) {
            return null;
        }

        return value.toString();
    }
}
