package org.giogt.commons.core.properties.exceptions;


public class CannotCreatePropertiesFileException extends PropertiesStoreException {
    private static final long serialVersionUID = 1L;

    public CannotCreatePropertiesFileException() {
    }

    public CannotCreatePropertiesFileException(String message) {
        super(message);
    }

    public CannotCreatePropertiesFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public CannotCreatePropertiesFileException(Throwable cause) {
        super(cause);
    }
}
