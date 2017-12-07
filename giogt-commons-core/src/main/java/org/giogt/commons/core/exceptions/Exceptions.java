package org.giogt.commons.core.exceptions;

import org.giogt.commons.core.CommonsRuntimeException;
import org.giogt.commons.core.Preconditions;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class Exceptions {

    // prevent instantiation
    private Exceptions() {
        throw new AssertionError("Class <" + Exceptions.class + "> cannot be instantiated");
    }

    public static String getStackTraceAsString(Throwable t) {
        Preconditions.notNull(t, "t");

        try (StringWriter stringWriter = new StringWriter();
             PrintWriter printWriter = new PrintWriter(stringWriter)) {

            t.printStackTrace(printWriter);
            return stringWriter.getBuffer().toString();
        } catch (IOException e) {
            throw new CommonsRuntimeException("cannot close stream", e);
        }

    }

    public static boolean isExceptionInCausesChain(
            Class<? extends Throwable> exceptionClass,
            Throwable occurredException) {

        Preconditions.notNull(occurredException, "occurredException");

        Throwable currentException = occurredException;
        while (currentException != null) {
            if (exceptionClass.isAssignableFrom(currentException.getClass())) {
                return true;
            }
            currentException = currentException.getCause();
        }

        return false;
    }

}
