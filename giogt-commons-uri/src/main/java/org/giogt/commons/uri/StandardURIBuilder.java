package org.giogt.commons.uri;

import org.giogt.commons.core.Preconditions;
import org.giogt.commons.core.collections.MultivaluedHashMap;
import org.giogt.commons.core.collections.MultivaluedMap;
import org.giogt.commons.core.text.escapers.CharEscapers;
import org.giogt.commons.core.text.unescapers.CharUnescapers;
import org.giogt.commons.uri.control.QueryParametersParser;
import org.giogt.commons.uri.entity.QueryParameter;

import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class StandardURIBuilder implements URIBuilder<StandardURIBuilder> {

    private String scheme;
    private String userInfo;
    private String host;
    private Integer port;
    private String path;
    private List<QueryParameter> queryParameters;
    private MultivaluedMap<String, String> queryParametersMultiMap;
    private String fragment;

    private String rawUserInfo;
    private String rawHost;
    private String rawPath;
    private String rawQueryString;
    private String rawFragment;

    StandardURIBuilder() {
        // initialize collections
        this.queryParameters = new ArrayList<>();
        this.queryParametersMultiMap = new MultivaluedHashMap<>();
    }

    StandardURIBuilder(String uriString) throws URISyntaxException {
        Preconditions.notNull(uriString, "uriString");
        if (uriString.trim().equals("")) {
            throw new URISyntaxException("Illegal URI syntax: URI string cannot be empty");
        }

        try {
            URI uri = new URI(uriString);
            digestURI(uri);
        } catch (java.net.URISyntaxException e) {
            throw URISyntaxException.forURI(uriString, e);
        }
    }

    StandardURIBuilder(URI uri) {
        Preconditions.notNull(uri, "uri");
        digestURI(uri);
    }

    void digestURI(final URI uri) {
        this.scheme = uri.getScheme();
        this.userInfo = uri.getUserInfo();
        this.host = uri.getHost();
        this.port = (uri.getPort() >= 0)
                ? uri.getPort()
                : null;
        this.path = uri.getPath();
        QueryParametersParser queryParametersParser = new QueryParametersParser(uri.getRawQuery());
        this.queryParameters = queryParametersParser.getQueryParameters();
        this.queryParametersMultiMap = queryParametersParser.getQueryParametersMultiMap();
        this.fragment = uri.getFragment();

        this.rawPath = uri.getRawPath();
        this.rawQueryString = uri.getRawQuery();
        this.rawFragment = uri.getRawFragment();
    }

    @Override
    public String getScheme() {
        return scheme;
    }

    @Override
    public StandardURIBuilder setScheme(String scheme) {
        this.scheme = scheme;
        return this;
    }

    @Override
    public String getUserInfo() {
        return userInfo;
    }

    @Override
    public StandardURIBuilder setUserInfo(String userInfo) {
        this.userInfo = userInfo;
        this.rawUserInfo = (userInfo != null)
                ? CharEscapers.uriUserInfoEscaper().escape(userInfo)
                : null;

        return this;
    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public StandardURIBuilder setHost(String host) {
        this.host = host;
        // TODO if this is an IPv6 literal, add square brackets around it
        this.rawHost = (host != null)
                ? CharEscapers.uriHostEscaper().escape(host)
                : null;

        return this;
    }

    @Override
    public Integer getPort() {
        return port;
    }

    @Override
    public StandardURIBuilder setPort(Integer port) {
        this.port = port;
        return this;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public StandardURIBuilder setPath(String path) {
        this.path = path;
        this.rawPath = (path != null)
                ? CharEscapers.uriPathEscaper().escape(path)
                : null;

        return this;
    }

    @Override
    public List<QueryParameter> getQueryParameters() {
        return queryParameters;
    }

    @Override
    public MultivaluedMap<String, String> getQueryParametersMultiMap() {
        return queryParametersMultiMap;
    }

    @Override
    public List<String> getQueryParameterValues(String name) {
        return queryParametersMultiMap.get(name);
    }

    @Override
    public String getFirstQueryParameterValue(String name) {
        return queryParametersMultiMap.getFirst(name);
    }

    /**
     * Adds a parameter to the URI query string.
     * <p>
     * The parameter name and value are expected to be unescaped and they will
     * be escaped properly by this method.
     *
     * @param name
     * @param value
     * @return
     */
    @Override
    public StandardURIBuilder addQueryParameter(String name, String value) {
        Preconditions.notNull(name, "name");

        this.queryParameters.add(new QueryParameter(name, value));
        List<String> multiMapValues = this.queryParametersMultiMap.get(name);
        if (multiMapValues == null) {
            multiMapValues = new ArrayList<>();
            this.queryParametersMultiMap.put(name, multiMapValues);
        }
        multiMapValues.add(value);

        this.rawQueryString = buildQueryStringFromQueryParameters(this.queryParameters);
        return this;
    }

    /**
     * Removes all values associated to the parameter with the specified name
     * in the URI query string.
     *
     * @param name
     * @return
     */
    @Override
    public StandardURIBuilder removeQueryParameter(String name) {
        // remove from query parameters list, if present
        Iterator<QueryParameter> queryParametersIt = this.queryParameters.iterator();
        while (queryParametersIt.hasNext()) {
            QueryParameter queryParameter = queryParametersIt.next();
            if (queryParameter.getName().equals(name)) {
                queryParametersIt.remove();
            }
        }

        // remove from query parameters multi map
        Iterator<Map.Entry<String, List<String>>> queryParametersMultiMapIt = this.queryParametersMultiMap.entrySet().iterator();
        while (queryParametersMultiMapIt.hasNext()) {
            Map.Entry<String, List<String>> queryParameter = queryParametersMultiMapIt.next();
            if (queryParameter.getKey().equals(name)) {
                queryParametersMultiMapIt.remove();
            }
        }

        this.rawQueryString = buildQueryStringFromQueryParameters(this.queryParameters);
        return this;
    }

    /**
     * Sets a parameter value to the URI query string, overriding existing
     * value if set.
     * <p>
     * The parameter name and value are expected to be unescaped and they will
     * be escaped properly by this method.
     *
     * @param name
     * @param value
     * @return
     */
    @Override
    public StandardURIBuilder setQueryParameter(String name, String value) {
        Preconditions.notNull(name, "name");

        removeQueryParameter(name);
        addQueryParameter(name, value);

        return this;
    }

    @Override
    public String getFragment() {
        return fragment;
    }

    @Override
    public StandardURIBuilder setFragment(String fragment) {
        this.fragment = fragment;
        this.rawFragment = (fragment != null)
                ? CharEscapers.getUriFragmentEscaper().escape(fragment)
                : null;

        return this;
    }

    @Override
    public String getRawUserInfo() {
        return rawUserInfo;
    }

    @Override
    public StandardURIBuilder setRawUserInfo(String rawUserInfo) {
        this.rawUserInfo = rawUserInfo;
        this.userInfo = (rawUserInfo != null)
                ? CharUnescapers.uriUnescaper().unescape(rawUserInfo)
                : null;

        return this;
    }

    @Override
    public String getRawHost() {
        return rawHost;
    }

    @Override
    public StandardURIBuilder setRawHost(String rawHost) {
        this.rawHost = rawHost;
        this.host = (rawHost != null)
                ? CharUnescapers.uriUnescaper().unescape(rawHost)
                : null;

        return this;
    }

    @Override
    public String getRawPath() {
        return rawPath;
    }

    @Override
    public StandardURIBuilder setRawPath(String rawPath) {
        this.rawPath = rawPath;
        this.path = (rawPath != null)
                ? CharUnescapers.uriUnescaper().unescape(rawPath)
                : null;

        return this;
    }

    @Override
    public String getRawQueryString() {
        return rawQueryString;
    }

    @Override
    public StandardURIBuilder setRawQueryString(String rawQueryString) {
        this.rawQueryString = rawQueryString;
        QueryParametersParser queryParametersParser = new QueryParametersParser(rawQueryString);
        this.queryParameters = queryParametersParser.getQueryParameters();
        this.queryParametersMultiMap = queryParametersParser.getQueryParametersMultiMap();

        return this;
    }

    @Override
    public String getRawFragment() {
        return rawFragment;
    }

    @Override
    public StandardURIBuilder setRawFragment(String rawFragment) {
        this.rawFragment = rawFragment;
        this.fragment = (rawFragment != null)
                ? CharUnescapers.uriUnescaper().unescape(rawFragment)
                : null;

        return this;
    }

    @Override
    public URI build() throws URISyntaxException {
        String uriString = buildString();
        try {
            return new URI(uriString);
        } catch (java.net.URISyntaxException e) {
            throw URISyntaxException.forURI(uriString, e);
        }
    }

    @Override
    public String buildString() {
        StringBuilder uriStringBuilder = new StringBuilder();
        if (scheme != null) {
            uriStringBuilder
                    .append(scheme)
                    .append("://");
        }
        if (rawUserInfo != null) {
            uriStringBuilder
                    .append(rawUserInfo)
                    .append("@");
        }
        if (rawHost != null) {
            uriStringBuilder.append(rawHost);
        }
        if (port != null) {
            uriStringBuilder
                    .append(":")
                    .append(port);
        }
        if (rawPath != null) {
            uriStringBuilder.append(rawPath);
        }
        if (rawQueryString != null || rawFragment != null) {
            uriStringBuilder.append("?");
        }
        if (rawQueryString != null) {
            uriStringBuilder.append(rawQueryString);
        }
        if (rawFragment != null) {
            uriStringBuilder
                    .append("#")
                    .append(rawFragment);
        }

        return uriStringBuilder.toString();
    }

    String buildQueryStringFromQueryParameters(List<QueryParameter> queryParameters) {
        StringBuilder queryStringBuilder = new StringBuilder();

        if (queryParameters != null && queryParameters.size() > 0) {
            // add first query parameter without trailing entry delimiter
            QueryParameter firstQueryParameter = queryParameters.get(0);
            appendQueryParameter(queryStringBuilder, firstQueryParameter);

            // add other query parameters (if any) with trailing entry delimiter
            for (int i = 1; i < queryParameters.size(); i++) {
                QueryParameter currentQueryParameter = queryParameters.get(i);
                queryStringBuilder.append(QueryParametersParser.ENTRY_DELIMITER);
                appendQueryParameter(queryStringBuilder, currentQueryParameter);
            }
        }

        return queryStringBuilder.toString();
    }

    void appendQueryParameter(StringBuilder queryStringBuilder, QueryParameter queryParameter) {
        queryStringBuilder
                .append(queryParameter.getName())
                .append(QueryParametersParser.KEY_VALUE_DELIMITER);
        if (queryParameter.getValue() != null) {
            queryStringBuilder.append(queryParameter.getValue());
        }
    }
}
