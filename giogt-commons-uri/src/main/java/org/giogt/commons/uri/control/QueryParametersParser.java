package org.giogt.commons.uri.control;

import org.giogt.commons.core.collections.MultivaluedHashMap;
import org.giogt.commons.core.collections.MultivaluedMap;
import org.giogt.commons.core.text.Strings;
import org.giogt.commons.core.text.unescapers.CharUnescapers;
import org.giogt.commons.uri.entity.QueryParameter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QueryParametersParser {

    public static final String ENTRY_DELIMITER = "&";
    public static final String KEY_VALUE_DELIMITER = "=";

    private final String queryString;

    private final List<QueryParameter> queryParameters;
    private final MultivaluedMap<String, String> queryParametersMultiMap;

    public QueryParametersParser(String queryString) {
        this.queryString = queryString;
        this.queryParameters = new ArrayList<>();
        this.queryParametersMultiMap = new MultivaluedHashMap<>();

        parseQueryParameters(
                queryString,
                this.queryParameters,
                this.queryParametersMultiMap);
    }

    void parseQueryParameters(
            String queryString,
            List<QueryParameter> queryParameters,
            MultivaluedMap<String, String> queryParametersMultiMap) {

        MultivaluedMap<String, String> encodedQueryParametersMultiMap = Strings.toMultivaluedMap(
                queryString, ENTRY_DELIMITER, KEY_VALUE_DELIMITER, false, false);

        /*
         * Populate the query parameters list and the query parameters multi
         * map unescaping values, since they are percent encoded at this stage.
         */

        for (Map.Entry<String, List<String>> entry : encodedQueryParametersMultiMap.entrySet()) {
            String name = entry.getKey();
            List<String> values = entry.getValue();

            for (String escapedValue : values) {
                String value = CharUnescapers.uriUnescaper().unescape(escapedValue);

                queryParameters.add(new QueryParameter(name, escapedValue));

                List<String> multiMapValues = queryParametersMultiMap.get(name);
                if (multiMapValues == null) {
                    multiMapValues = new ArrayList<>();
                    queryParametersMultiMap.put(name, multiMapValues);
                }
                multiMapValues.add(escapedValue);
            }
        }
    }

    public List<QueryParameter> getQueryParameters() {
        return queryParameters;
    }

    public MultivaluedMap<String, String> getQueryParametersMultiMap() {
        return queryParametersMultiMap;
    }

}
