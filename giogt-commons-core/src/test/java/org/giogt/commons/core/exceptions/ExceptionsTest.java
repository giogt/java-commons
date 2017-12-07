package org.giogt.commons.core.exceptions;

import org.giogt.commons.core.CommonsException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ExceptionsTest {

    @Test
    public void getStackTraceAsString_forNullException_mustThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            Throwable t = null;
            Exceptions.getStackTraceAsString(t);
        });
    }

    @Test
    public void getStackTraceAsString_forException_mustReturnExceptionStringRepresentation() {
        String errorMessage = "this is my error message";
        Throwable t = new CommonsException(errorMessage);
        String stackTraceString = Exceptions.getStackTraceAsString(t);

        assertThat(stackTraceString, is(notNullValue()));
        assertThat(stackTraceString, containsString(CommonsException.class.getName()));
        assertThat(stackTraceString, containsString(errorMessage));
        assertThat(stackTraceString, containsString(ExceptionsTest.class.getName()));
    }


    @Test
    public void isExceptionInCausesChain_forNullException_mustThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            Class<? extends Throwable> exceptionClass = IOException.class;
            Throwable occurredException = null;

            Exceptions.isExceptionInCausesChain(exceptionClass, occurredException);
        });
    }

    @Test
    public void isExceptionInCausesChain_forDifferentException_mustReturnFalse() {
        Class<? extends Throwable> exceptionClass = IOException.class;
        Throwable occurredException = new CommonsException();

        boolean result = Exceptions.isExceptionInCausesChain(exceptionClass, occurredException);
        assertThat(result, is(false));
    }

    @Test
    public void isExceptionInCausesChain_forSameException_mustReturnTrue() {
        Class<? extends Throwable> exceptionClass = CommonsException.class;
        Throwable occurredException = new CommonsException();

        boolean result = Exceptions.isExceptionInCausesChain(exceptionClass, occurredException);
        assertThat(result, is(true));
    }

    @Test
    public void isExceptionInCausesChain_forExceptionNotInCausesChain_mustReturnFalse() {
        Class<? extends Throwable> exceptionClass = IOException.class;
        RuntimeException occurredException = new RuntimeException(new CommonsException());

        boolean result = Exceptions.isExceptionInCausesChain(exceptionClass, occurredException);
        assertThat(result, is(false));
    }

    @Test
    public void isExceptionInCausesChain_forExceptionInCausesChain_mustReturnTrue() {
        Class<? extends Throwable> exceptionClass = CommonsException.class;
        RuntimeException occurredException = new RuntimeException(new CommonsException());

        boolean result = Exceptions.isExceptionInCausesChain(exceptionClass, occurredException);
        assertThat(result, is(true));
    }

}