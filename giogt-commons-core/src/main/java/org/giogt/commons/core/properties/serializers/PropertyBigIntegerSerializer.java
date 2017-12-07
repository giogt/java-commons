package org.giogt.commons.core.properties.serializers;

import org.giogt.commons.core.properties.MappingContext;
import org.giogt.commons.core.properties.PropertySerializationException;
import org.giogt.commons.core.properties.PropertySerializer;

import java.math.BigInteger;

public class PropertyBigIntegerSerializer implements PropertySerializer<BigInteger> {

    @Override
    public Class<? extends BigInteger> handledType() {
        return BigInteger.class;
    }

    @Override
    public BigInteger fromString(
            MappingContext context,
            String stringValue)
            throws PropertySerializationException {

        if (stringValue == null) {
            return null;
        }

        try {
            return new BigInteger(stringValue);
        } catch (NumberFormatException e) {
            throw new PropertySerializationException(
                    "cannot parse property value <" + stringValue + "> to big decimal",
                    e);
        }
    }

    @Override
    public String toString(
            MappingContext context,
            BigInteger value)
            throws PropertySerializationException {

        if (value == null) {
            return null;
        }
        return value.toString();
    }
}
