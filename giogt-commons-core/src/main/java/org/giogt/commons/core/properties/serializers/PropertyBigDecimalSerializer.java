package org.giogt.commons.core.properties.serializers;

import org.giogt.commons.core.properties.MappingContext;
import org.giogt.commons.core.properties.PropertySerializationException;
import org.giogt.commons.core.properties.PropertySerializer;

import java.math.BigDecimal;

public class PropertyBigDecimalSerializer implements PropertySerializer<BigDecimal> {

    @Override
    public Class<? extends BigDecimal> handledType() {
        return BigDecimal.class;
    }

    @Override
    public BigDecimal fromString(
            MappingContext context,
            String stringValue)
            throws PropertySerializationException {

        if (stringValue == null) {
            return null;
        }

        try {
            return new BigDecimal(stringValue);
        } catch (NumberFormatException e) {
            throw new PropertySerializationException(
                    "cannot parse property value <" + stringValue + "> to big decimal",
                    e);
        }
    }

    @Override
    public String toString(
            MappingContext context,
            BigDecimal value)
            throws PropertySerializationException {

        if (value == null) {
            return null;
        }
        return value.toString();
    }
}
