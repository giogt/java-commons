package org.giogt.commons.core;

public class CommonsRuntimeException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CommonsRuntimeException() {
    }

    public CommonsRuntimeException(String message) {
        super(message);
    }

    public CommonsRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommonsRuntimeException(Throwable cause) {
        super(cause);
    }

    public CommonsRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
