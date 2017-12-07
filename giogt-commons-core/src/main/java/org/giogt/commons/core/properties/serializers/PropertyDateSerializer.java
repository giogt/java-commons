package org.giogt.commons.core.properties.serializers;

import org.giogt.commons.core.properties.MappingContext;
import org.giogt.commons.core.properties.PropertySerializationException;
import org.giogt.commons.core.properties.PropertySerializer;

import java.util.Date;

public class PropertyDateSerializer implements PropertySerializer<Date> {

    @Override
    public Class<? extends Date> handledType() {
        return Date.class;
    }

    @Override
    public Date fromString(
            MappingContext context,
            String stringValue) throws PropertySerializationException {

        if (stringValue == null) {
            return null;
        }
//        DateTime dateTime = fromStringToDateTime(stringValue);
//        return dateTime.toDate();

        throw new UnsupportedOperationException("Not yet implemented!");
    }

//    DateTime fromStringToDateTime(String stringValue) {
//        DateTime value;
//        try {
//            value = DateTime.parse(stringValue);
//        } catch (UnsupportedOperationException | IllegalArgumentException e) {
//            throw new PropertySerializationException(
//                    "cannot parse property value <" + stringValue + "> to date time",
//                    e);
//        }
//        return value;
//    }

    @Override
    public String toString(
            MappingContext context,
            Date value) throws PropertySerializationException {

        if (value == null) {
            return null;
        }
//        DateTime dateTime = new DateTime(value);
//        return dateTime.toString();

        throw new UnsupportedOperationException("Not yet implemented!");
    }

}
