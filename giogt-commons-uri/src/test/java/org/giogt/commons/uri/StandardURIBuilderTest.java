package org.giogt.commons.uri;

import org.giogt.commons.core.collections.MultivaluedMap;
import org.giogt.commons.uri.entity.QueryParameter;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StandardURIBuilderTest {

    @Test
    public void emptyConstructor_mustCreateANewStandardURIBuilder() {
        new StandardURIBuilder();
    }

    @Test
    public void constructorWithURI_forFileURIWithSchemaAndAbsolutePath_mustParseURICorrectly() {
        String path = "/foo/bar.txt";
        String param1Key = "param1";
        String param1Value = "value1";
        String fileUri = "file://" + path + "?" + param1Key + "=" + param1Value;

        StandardURIBuilder result = new StandardURIBuilder(fileUri);

        assertThat(result, is(notNullValue()));
        assertThat(result.getPath(), is(path));
        assertThat(result.build().toString(), is(fileUri));
    }

    @Test
    public void constructorWithURI_forFileURIWithAbsolutePath_mustParseURICorrectly() {
        String path = "/foo/bar.txt";
        String param1Key = "param1";
        String param1Value = "value1";
        String fileUri = path + "?" + param1Key + "=" + param1Value;

        StandardURIBuilder result = new StandardURIBuilder(fileUri);

        assertThat(result, is(notNullValue()));
        assertThat(result.getPath(), is(path));
        assertThat(result.build().toString(), is(fileUri));
    }

    @Test
    public void constructorWithURI_forFileURIWithSchemaAndRootDirPath_mustParseURICorrectly() {
        String path = "/";
        String fileUri = "file://" + path;

        StandardURIBuilder result = new StandardURIBuilder(fileUri);

        assertThat(result, is(notNullValue()));
        assertThat(result.getPath(), is("/"));
        assertThat(result.build().toString(), is(fileUri));
    }

    @Test
    public void constructorWithURI_forURIWithRootDirPath_mustParseURICorrectly() {
        String path = "/";
        String fileUri = path;

        StandardURIBuilder result = new StandardURIBuilder(fileUri);

        assertThat(result, is(notNullValue()));
        assertThat(result.getPath(), is("/"));
        assertThat(result.build().toString(), is(fileUri));
    }

    @Test
    public void constructorWithURI_forFileURIWithoutPathWithParameters_mustParseURICorrectly() {
        String fileUri = "file://?param1=value1";
        StandardURIBuilder uriBuilder = new StandardURIBuilder(fileUri);

        assertThat(uriBuilder, is(notNullValue()));
        assertThat(uriBuilder.build().toString(), is(fileUri));
        assertThat(uriBuilder.getScheme(), is("file"));
        assertThat(uriBuilder.getPath(), is(""));

        List<QueryParameter> queryParameters = uriBuilder.getQueryParameters();
        assertThat(queryParameters.size(), is(1));
        assertThat(queryParameters.get(0).getName(), is("param1"));
        assertThat(queryParameters.get(0).getValue(), is("value1"));
    }

    @Test
    public void constructorWithURI_forNullURIString_mustFail() {
        assertThrows(IllegalArgumentException.class, () -> {
            String nullUri = null;
            new StandardURIBuilder(nullUri);
        });
    }

    @Test
    public void constructorWithURI_forEmptyURIString_mustFail() {
        assertThrows(URISyntaxException.class, () -> {
            String emptyUri = "";
            new StandardURIBuilder(emptyUri);
        });
    }

    @Test
    public void constructorWithURI_forIllegalURIString_mustFail() {
        assertThrows(URISyntaxException.class, () -> {
            String illegalUri = "|\\/?|";
            new StandardURIBuilder(illegalUri);
        });
    }

    @Test
    public void constructorWithURI_forFileURIWithoutPath_mustFail() {
        assertThrows(URISyntaxException.class, () -> {
            String fileUri = "file://";
            new StandardURIBuilder(fileUri);
        });
    }

    @Test
    public void setPath_forAbsolutePath_mustAddExpectedPathToURI() {
        String path = "/foo/bar.txt";
        String expected = "file://" + path;

        String result = new StandardURIBuilder()
                .setScheme("file")
                .setPath(path)
                .build()
                .toString();

        assertThat(result, is(notNullValue()));
        assertThat(result, is(expected));
    }

    @Test
    public void setPath_forRelativePath_mustAddExpectedPathToURI() {
        String path = "foo/bar.txt";
        String expected = "file://" + path;

        String result = new StandardURIBuilder()
                .setScheme("file")
                .setPath(path)
                .build()
                .toString();

        assertThat(result, is(notNullValue()));
        assertThat(result, is(expected));
    }

    @Test
    public void addParameter_forOneParameter_mustAddParameterToURI() {
        String path = "/foo/bar.txt";

        String param1Key = "param1";
        String param1Value = "value1";

        String expected = "file://" + path + "?" +
                param1Key + "=" + param1Value;

        String result = new StandardURIBuilder()
                .setScheme("file")
                .setPath(path)
                .addQueryParameter(param1Key, param1Value)
                .build()
                .toString();

        assertThat(result, is(notNullValue()));
        assertThat(result, is(expected));
    }

    @Test
    public void addParameter_forTwoParameters_mustAddParametersToURI() {
        String path = "/foo/bar.txt";

        String param1Key = "param1";
        String param1Value = "value1";
        String param2Key = "param2";
        String param2Value = "value2";

        String expected = "file://" + path + "?" +
                param1Key + "=" + param1Value + "&" +
                param2Key + "=" + param2Value;

        String result = new StandardURIBuilder()
                .setScheme("file")
                .setPath(path)
                .addQueryParameter(param1Key, param1Value)
                .addQueryParameter(param2Key, param2Value)
                .build()
                .toString();

        assertThat(result, is(notNullValue()));
        assertThat(result, is(expected));
    }

    @Test
    public void addParameter_forTwoParametersWithTheSameKey_mustAddParametersToURI() {
        String path = "/foo/bar.txt";

        String param1Key = "param1";
        String param1Value = "value1";
        String param2Key = param1Key;
        String param2Value = "value2";

        String expected = "file://" + path + "?" +
                param1Key + "=" + param1Value + "&" +
                param2Key + "=" + param2Value;

        String result = new StandardURIBuilder()
                .setScheme("file")
                .setPath(path)
                .addQueryParameter(param1Key, param1Value)
                .addQueryParameter(param2Key, param2Value)
                .build()
                .toString();

        assertThat(result, is(notNullValue()));
        assertThat(result, is(expected));
    }

    @Test
    public void setParameter_forOneParameter_mustAddParameterToURI() {
        String scheme = "file";
        String path = "/foo/bar.txt";

        String param1Key = "param1";
        String param1Value = "value1";

        String expected = scheme + "://" + path + "?" +
                param1Key + "=" + param1Value;

        String result = new StandardURIBuilder()
                .setScheme(scheme)
                .setPath(path)
                .setQueryParameter(param1Key, param1Value)
                .build()
                .toString();

        assertThat(result, is(notNullValue()));
        assertThat(result, is(expected));
    }

    @Test
    public void setParameter_forTwoParameters_mustAddParametersToURI() {
        String scheme = "file";
        String path = "/foo/bar.txt";

        String param1Key = "param1";
        String param1Value = "value1";
        String param2Key = "param2";
        String param2Value = "value2";

        String expected = scheme + "://" + path + "?" +
                param1Key + "=" + param1Value + "&" +
                param2Key + "=" + param2Value;

        String result = new StandardURIBuilder()
                .setScheme(scheme)
                .setPath(path)
                .setQueryParameter(param1Key, param1Value)
                .setQueryParameter(param2Key, param2Value)
                .build()
                .toString();

        assertThat(result, is(notNullValue()));
        assertThat(result, is(expected));
    }

    @Test
    public void setParameter_forTwoParametersWithTheSameKey_mustReplacePreviousParameterValueInTheURI() {
        String scheme = "file";
        String path = "/foo/bar.txt";

        String param1Key = "param1";
        String param1Value = "value1";
        String param2Key = param1Key;
        String param2Value = "value2";

        String expected = scheme + "://" + path + "?" +
                param1Key + "=" + param2Value;

        String result = new StandardURIBuilder()
                .setScheme(scheme)
                .setPath(path)
                .setQueryParameter(param1Key, param1Value)
                .setQueryParameter(param2Key, param2Value)
                .build()
                .toString();

        assertThat(result, is(notNullValue()));
        assertThat(result, is(expected));
    }

    @Test
    public void forAllURIPartsWithNoSpecialCharacters_buildStringMustReturnExpectedURI_allGettersMustReturnProperValue() {
        String scheme = "ftp";
        String userInfo = "username:***";
        String host = "hostname";
        Integer port = 2121;
        String path = "/foo/bar.txt";
        String param1Name = "param1";
        String param1Value = "value1";
        String param2Name = "param2";
        String param2Value = "value2";
        String fragment = "fragment";

        String expected = scheme + "://" +
                userInfo + "@" +
                host + ":" + port +
                path + "?" +
                param1Name + "=" + param1Value +
                "&" + param2Name + "=" + param2Value +
                "#" + fragment;

        URIBuilder uriBuilder = new StandardURIBuilder()
                .setScheme(scheme)
                .setUserInfo(userInfo)
                .setHost(host)
                .setPort(port)
                .setPath(path)
                .addQueryParameter(param1Name, param1Value)
                .addQueryParameter(param2Name, param2Value)
                .setFragment(fragment);
        String uriString = uriBuilder
                .buildString();

        assertThat(uriString, is(notNullValue()));
        assertThat(uriString, is(expected));
        assertThat(uriBuilder.getScheme(), is(scheme));
        assertThat(uriBuilder.getUserInfo(), is(userInfo));
        assertThat(uriBuilder.getRawUserInfo(), is(userInfo));
        assertThat(uriBuilder.getHost(), is(host));
        assertThat(uriBuilder.getRawHost(), is(host));
        assertThat(uriBuilder.getPort(), is(port));
        assertThat(uriBuilder.getPath(), is(path));
        assertThat(uriBuilder.getRawPath(), is(path));

        List<QueryParameter> queryParameters = uriBuilder.getQueryParameters();
        assertThat(queryParameters, is(notNullValue()));
        assertThat(queryParameters.size(), is(2));
        assertThat(queryParameters.get(0).getName(), is(param1Name));
        assertThat(queryParameters.get(0).getValue(), is(param1Value));
        assertThat(queryParameters.get(1).getName(), is(param2Name));
        assertThat(queryParameters.get(1).getValue(), is(param2Value));

        MultivaluedMap<String, String> queryParametersMultiMap = uriBuilder.getQueryParametersMultiMap();
        assertThat(queryParametersMultiMap, is(notNullValue()));
        assertThat(queryParametersMultiMap.size(), is(2));
        assertThat(queryParametersMultiMap.get(param1Name).size(), is(1));
        assertThat(queryParametersMultiMap.getFirst(param1Name), is(param1Value));
        assertThat(queryParametersMultiMap.get(param2Name).size(), is(1));
        assertThat(queryParametersMultiMap.getFirst(param2Name), is(param2Value));

        assertThat(uriBuilder.getRawQueryString(), is(param1Name + "=" + param1Value +
                "&" + param2Name + "=" + param2Value));

        assertThat(uriBuilder.getFragment(), is(fragment));
        assertThat(uriBuilder.getRawFragment(), is(fragment));
    }

}