package org.giogt.commons.uri.control;

import org.giogt.commons.core.collections.MultivaluedMap;
import org.giogt.commons.uri.entity.QueryParameter;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;

public class QueryParametersParserTest {

    @Test
    public void parser_forNullQueryString_mustParseEmptyQueryParameters() {
        String queryString = null;

        QueryParametersParser parser = new QueryParametersParser(queryString);

        List<QueryParameter> queryParameters = parser.getQueryParameters();
        MultivaluedMap<String, String> queryParametersMultiMap = parser.getQueryParametersMultiMap();

        assertThat(queryParameters, is(notNullValue()));
        assertThat(queryParameters.size(), is(0));

        assertThat(queryParametersMultiMap, is(notNullValue()));
        assertThat(queryParametersMultiMap.size(), is(0));
    }

    @Test
    public void parser_forEmptyQueryString_mustParseEmptyQueryParameters() {
        String queryString = "";

        QueryParametersParser parser = new QueryParametersParser(queryString);

        List<QueryParameter> queryParameters = parser.getQueryParameters();
        MultivaluedMap<String, String> queryParametersMultiMap = parser.getQueryParametersMultiMap();

        assertThat(queryParameters, is(notNullValue()));
        assertThat(queryParameters.size(), is(0));

        assertThat(queryParametersMultiMap, is(notNullValue()));
        assertThat(queryParametersMultiMap.size(), is(0));
    }

}