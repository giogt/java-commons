package org.giogt.commons.core.properties.exceptions;

import org.giogt.commons.core.CommonsRuntimeException;

public class PropertiesStoreException extends CommonsRuntimeException {
    private static final long serialVersionUID = 1L;

    public PropertiesStoreException() {
    }

    public PropertiesStoreException(String message) {
        super(message);
    }

    public PropertiesStoreException(String message, Throwable cause) {
        super(message, cause);
    }

    public PropertiesStoreException(Throwable cause) {
        super(cause);
    }
}
