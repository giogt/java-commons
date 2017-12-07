package org.giogt.commons.uri;

import org.giogt.commons.uri.test.util.TestCharacters;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URISyntaxException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class URIsTest {

    private static final String PATH_CHARS_TO_ESCAPE = "#?[] ";
    private static final String PATH_CHARS_ESCAPED = "%23%3F%5B%5D%20";

    private static final String PATH_SEGMENT_CHARS_TO_ESCAPE = "#/?[] ";
    private static final String PATH_SEGMENT_CHARS_ESCAPED = "%23%2F%3F%5B%5D%20";

    private static final String QUERY_PARAM_VALUE_CHARS_TO_ESCAPE = "=& ";
    private static final String QUERY_PARAM_VALUE_CHARS_ESCAPED = "%3D%26%20";

    private static final String SPACE_PERCENT_ENCODED = "%20";

    /*
     * ------------------------------------------------------------------------
     * escapePath()
     * ------------------------------------------------------------------------
     */

    @Test
    public void escapePath_forNullInput_mustThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            String input = null;
            URIs.escapePath(input);
        });
    }

    @Test
    public void escapePath_forEmptyStringInput_mustThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            String input = "";
            URIs.escapePath(input);
        });
    }

    @Test
    public void escapePath_forNonAbsolutePathInput_mustThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            String input = "non/absolute/path.txt";
            URIs.escapePath(input);
        });
    }

    @Test
    public void escapePath_forPathInputWithoutCharsToEscape_mustReturnTheSameInputString() {
        String input = "/home/user/foo.txt";
        String expected = input;

        String result = URIs.escapePath(input);
        assertThat(result, is(notNullValue()));
        assertThat(result, is(expected));
    }

    @Test
    public void escapePath_forAbsolutePathInputWithCharsToEscape_mustEscapeCharacters() {
        String input = "/home/user/foo" + PATH_CHARS_TO_ESCAPE + ".txt";
        String expected = "/home/user/foo" + PATH_CHARS_ESCAPED + ".txt";

        String result = URIs.escapePath(input);
        assertThat(result, is(notNullValue()));
        assertThat(result, is(expected));
    }

    /*
     * ------------------------------------------------------------------------
     * escapePathSegment()
     * ------------------------------------------------------------------------
     */

    @Test
    public void escapePathSegment_forNullInput_mustThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            String input = null;
            URIs.escapePathSegment(input);
        });
    }

    @Test
    public void escapePathSegment_forEmptyStringInput_mustReturnEmptyString() {
        String input = "";
        String expected = input;

        String result = URIs.escapePathSegment(input);
        assertThat(result, is(notNullValue()));
        assertThat(result, is(expected));
    }

    @Test
    public void escapePathSegment_forAlphaNumericInput_mustReturnTheSameInputString() {
        String input = "foo123bar";
        String expected = input;

        String result = URIs.escapePathSegment(input);
        assertThat(result, is(notNullValue()));
        assertThat(result, is(expected));
    }

    @Test
    public void escapePathSegment_forInputContainingCharsToEscape_mustEscapeChars() {
        String input = "foo" + PATH_SEGMENT_CHARS_TO_ESCAPE + "bar";
        String expected = "foo" + PATH_SEGMENT_CHARS_ESCAPED + "bar";

        String result = URIs.escapePathSegment(input);
        assertThat(result, is(notNullValue()));
        assertThat(result, is(expected));
    }

    /*
     * ------------------------------------------------------------------------
     * escapeQueryParam()
     * ------------------------------------------------------------------------
     */

    @Test
    public void escapeQueryValue_forNullInput_mustThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            String input = null;
            URIs.escapeQueryParam(input);
        });
    }

    @Test
    public void escapeQueryValue_forEmptyStringInput_mustReturnEmptyString() {
        String input = "";
        URIs.escapeQueryParam(input);
    }

    @Test
    public void escapeQueryValue_forAlphaNumericInput_mustReturnTheSameInputString() {
        String input = "foo123bar";
        String expected = input;

        String result = URIs.escapeQueryParam(input);
        assertThat(result, is(notNullValue()));
        assertThat(result, is(expected));
    }

    @Test
    public void escapeQueryValue_forInputContainingCharsToEscape_mustEscapeChars() {
        String input = "foo" + QUERY_PARAM_VALUE_CHARS_TO_ESCAPE + "bar";
        String expected = "foo" + QUERY_PARAM_VALUE_CHARS_ESCAPED + "bar";

        String result = URIs.escapeQueryParam(input);
        assertThat(result, is(notNullValue()));
        assertThat(result, is(expected));
    }

    /*
     * ------------------------------------------------------------------------
     * toFile()
     * ------------------------------------------------------------------------
     */

    @Test
    public void toFile_forNullURIString_mustThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            String input = null;
            URIs.toFile(input);
        });
    }

    @Test
    public void toFile_forEmptyURIString_mustThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            String input = "";
            URIs.toFile(input);
        });
    }

    @Test
    public void toFile_forValidFileURIString_mustAdaptToFile() {
        String filePath = "/home/user/foo.txt";
        String input = "file://" + filePath;
        File expected = new File(filePath);

        File result = URIs.toFile(input);
        assertThat(result, is(expected));
    }

    @Test
    public void toFile_forValidFileURIStringWithSpecialCharacters_mustAdaptToFile()
            throws URISyntaxException {

        String filePath = "/home/user/foo" + TestCharacters.SPECIAL_CHARS + ".txt";
        String input = URIs.newURI()
                .setScheme("file")
                .setPath(filePath)
                .build()
                .toString();
        File expected = new File(filePath);

        File result = URIs.toFile(input);
        assertThat(result, is(expected));
    }

    @Test
    public void toFile_forValidFileURIStringWithoutScheme_mustAdaptToFile() {
        String filePath = "/home/user/foo.txt";
        String input = filePath;
        File expected = new File(filePath);

        File result = URIs.toFile(input);
        assertThat(result, is(expected));
    }

    @Test
    public void replaceSpacesWithPercentEncoding_forNullInput_mustFail() {
        assertThrows(IllegalArgumentException.class, () -> {
            URIs.replaceSpacesWithPercentEncoding(null);
        });
    }

    @Test
    public void replaceSpacesWithPercentEncoding_mustReplaceSpacesWithTheURIEncodedSpace() {
        String input = "/home/ghibli/test ghibli test.txt";
        String expected = "/home/ghibli/test" + SPACE_PERCENT_ENCODED + "ghibli" + SPACE_PERCENT_ENCODED + "test.txt";

        String result = URIs.replaceSpacesWithPercentEncoding(input);

        assertThat(result, is(notNullValue()));
        assertThat(result, is(expected));
    }

    /*
     * ------------------------------------------------------------------------
     * fromFile()
     * ------------------------------------------------------------------------
     */

    @Test
    public void fromFile_forNullInput_mustThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            String input = null;
            URIs.fromFile(input);
        });
    }

    @Test
    public void fromFile_forEmptyStringInput_mustThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            String input = "";
            URIs.fromFile(input);
        });
    }

    @Test
    public void fromFile_forNonAbsolutePathInput_mustThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            String input = "non/absolute/path.txt";
            URIs.fromFile(input);
        });
    }

    @Test
    public void fromFile_forPathInputWithoutCharsToEscape_mustReturnTheSameInputString() {
        String input = "/home/user/foo.txt";
        String expected = URIScheme.FILE.getScheme() + "://" + input;

        String result = URIs.fromFile(input);
        assertThat(result, is(notNullValue()));
        assertThat(result, is(expected));
    }

    @Test
    public void fromFile_forAbsolutePathInputWithCharsToEscape_mustReturnTheSameInputString() {
        String input = "/home/user/foo" + PATH_CHARS_TO_ESCAPE + ".txt";
        String expected = URIScheme.FILE.getScheme() + "://" + "/home/user/foo" + PATH_CHARS_ESCAPED + ".txt";

        String result = URIs.fromFile(input);
        assertThat(result, is(notNullValue()));
        assertThat(result, is(expected));
    }

    @Test
    public void fromFile_whenIncludeSchemaIsDisabled_mustNotPrependSchema() {
        String input = "/home/user/foo" + PATH_CHARS_TO_ESCAPE + ".txt";
        String expected = "/home/user/foo" + PATH_CHARS_ESCAPED + ".txt";

        String result = URIs.fromFile(input, false);
        assertThat(result, is(notNullValue()));
        assertThat(result, is(expected));
    }

    /*
     * =========================================================================
     * addParameter() standard URI tests
     * =========================================================================
     */

    @Test
    public void addParameterToURI_withAddMode_whenParameterDoesNotExistYet_mustAddParameter() {
        String paramKey = "param1";
        String paramValue = "value1";

        String uri = "file:///foo/bar.txt";
        String expected = uri + "?" + paramKey + "=" + paramValue;

        String result = URIs.addParameterToURI(
                uri,
                paramKey,
                paramValue,
                URIs.AddParameterMode.ADD);

        assertThat(result, is(notNullValue()));
        assertThat(result, is(expected));
    }

    @Test
    public void addParameterToURI_withOverrideMode_whenParameterDoesNotExistYet_mustAddParameter() {
        String paramKey = "param1";
        String paramValue = "value1";

        String uri = "file:///foo/bar.txt";
        String expected = uri + "?" + paramKey + "=" + paramValue;

        String result = URIs.addParameterToURI(
                uri,
                paramKey,
                paramValue,
                URIs.AddParameterMode.OVERRIDE);

        assertThat(result, is(notNullValue()));
        assertThat(result, is(expected));
    }

    @Test
    public void addParameterToURI_withKeepPreviousValueMode_whenParameterDoesNotExistYet_mustAddParameter() {
        String paramKey = "param1";
        String paramValue = "value1";

        String uri = "file:///foo/bar.txt";
        String expected = uri + "?" + paramKey + "=" + paramValue;

        String result = URIs.addParameterToURI(
                uri,
                paramKey,
                paramValue,
                URIs.AddParameterMode.KEEP_PREVIOUS_VALUE);

        assertThat(result, is(notNullValue()));
        assertThat(result, is(expected));
    }

    @Test
    public void addParameterToURI_withAddMode_whenParameterAlreadyExist_mustAddParameter() {
        String paramKey = "param1";
        String paramValue = "value1";
        String previousValue = "previousValue1";

        String uri = "file:///foo/bar.txt?" + paramKey + "=" + previousValue;
        String expected = uri + "&" + paramKey + "=" + paramValue;

        String result = URIs.addParameterToURI(
                uri,
                paramKey,
                paramValue,
                URIs.AddParameterMode.ADD);

        assertThat(result, is(notNullValue()));
        assertThat(result, is(expected));
    }

    @Test
    public void addParameterToURI_withOverrideMode_whenParameterAlreadyExist_mustReplaceParameterValue() {
        String paramKey = "param1";
        String paramValue = "value1";
        String previousValue = "previousValue1";

        String uri = "file:///foo/bar.txt?" + paramKey + "=" + previousValue;
        String expected = "file:///foo/bar.txt?" + paramKey + "=" + paramValue;

        String result = URIs.addParameterToURI(
                uri,
                paramKey,
                paramValue,
                URIs.AddParameterMode.OVERRIDE);

        assertThat(result, is(notNullValue()));
        assertThat(result, is(expected));
    }

    @Test
    public void addParameterToURI_withKeepPreviousValueMode_whenParameterAlreadyExist_mustNotModifyURI() {
        String paramKey = "param1";
        String paramValue = "value1";
        String previousValue = "previousValue1";

        String uri = "file:///foo/bar.txt?" + paramKey + "=" + previousValue;
        String expected = uri;

        String result = URIs.addParameterToURI(
                uri,
                paramKey,
                paramValue,
                URIs.AddParameterMode.KEEP_PREVIOUS_VALUE);

        assertThat(result, is(notNullValue()));
        assertThat(result, is(expected));
    }

}
