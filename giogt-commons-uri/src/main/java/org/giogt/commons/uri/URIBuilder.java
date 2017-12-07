package org.giogt.commons.uri;

import org.giogt.commons.core.collections.MultivaluedMap;
import org.giogt.commons.uri.entity.QueryParameter;

import java.net.URI;
import java.util.List;

public interface URIBuilder<T extends URIBuilder> {

    String getScheme();

    T setScheme(String scheme);

    String getUserInfo();

    T setUserInfo(String userInfo);

    String getHost();

    T setHost(String host);

    Integer getPort();

    T setPort(Integer port);

    String getPath();

    T setPath(String path);

    List<QueryParameter> getQueryParameters();

    MultivaluedMap<String, String> getQueryParametersMultiMap();

    List<String> getQueryParameterValues(String name);

    String getFirstQueryParameterValue(String name);

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
    T addQueryParameter(String name, String value);

    /**
     * Removes all values associated to the parameter with the specified name
     * in the URI query string.
     *
     * @param name
     * @return
     */
    T removeQueryParameter(String name);

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
    T setQueryParameter(String name, String value);

    String getFragment();

    T setFragment(String fragment);

    String getRawUserInfo();

    T setRawUserInfo(String rawUserInfo);

    String getRawHost();

    T setRawHost(String rawHost);

    String getRawPath();

    T setRawPath(String rawPath);

    String getRawQueryString();

    T setRawQueryString(String rawQueryString);

    String getRawFragment();

    T setRawFragment(String rawFragment);

    URI build();

    String buildString();

}
