package org.giogt.commons.core.properties;

import org.giogt.commons.core.CommonsRuntimeException;

public class PropertySerializationException extends CommonsRuntimeException {
    private static final long serialVersionUID = 1L;

    public PropertySerializationException() {
    }

    public PropertySerializationException(String message) {
        super(message);
    }

    public PropertySerializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public PropertySerializationException(Throwable cause) {
        super(cause);
    }
}
