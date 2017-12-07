package org.giogt.commons.core.properties.exceptions;


public class CannotLoadPropertiesFileException extends PropertiesStoreException {
    private static final long serialVersionUID = 1L;

    public CannotLoadPropertiesFileException() {
    }

    public CannotLoadPropertiesFileException(String message) {
        super(message);
    }

    public CannotLoadPropertiesFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public CannotLoadPropertiesFileException(Throwable cause) {
        super(cause);
    }
}
