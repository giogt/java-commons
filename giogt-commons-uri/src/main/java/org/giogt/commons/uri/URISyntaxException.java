package org.giogt.commons.uri;

import org.giogt.commons.core.CommonsRuntimeException;

import java.text.MessageFormat;

public class URISyntaxException extends CommonsRuntimeException {
    private static final long serialVersionUID = 1L;

    private static final String MESSAGE = "Illegal URI syntax: {0}";

    public URISyntaxException() {
    }

    public URISyntaxException(String message) {
        super(message);
    }

    public URISyntaxException(String message, Throwable cause) {
        super(message, cause);
    }

    public URISyntaxException(Throwable cause) {
        super(cause);
    }

    public static URISyntaxException forURI(String uri) {
        return new URISyntaxException(
                MessageFormat.format(MESSAGE, uri));
    }

    public static URISyntaxException forURI(
            String uri,
            Throwable cause) {

        return new URISyntaxException(
                MessageFormat.format(MESSAGE, uri),
                cause);
    }

}
