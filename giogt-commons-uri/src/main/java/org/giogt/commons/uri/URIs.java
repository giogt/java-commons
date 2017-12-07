package org.giogt.commons.uri;

import org.giogt.commons.core.Preconditions;
import org.giogt.commons.core.text.Escaper;
import org.giogt.commons.core.text.escapers.CharEscapers;

import java.io.File;
import java.net.URI;
import java.util.LinkedList;
import java.util.List;

public class URIs {

    public static String escapeUserInfo(String rawUserInfo) {
        Preconditions.notNull(rawUserInfo, "rawUserInfo");

        Escaper escaper = CharEscapers.uriUserInfoEscaper();
        return escaper.escape(rawUserInfo);
    }

    public static String escapeHost(String rawHost) {
        Preconditions.notNull(rawHost, "rawHost");

        Escaper escaper = CharEscapers.uriHostEscaper();
        return escaper.escape(rawHost);
    }

    public static String escapePathSegment(String rawPathSegment) {
        Preconditions.notNull(rawPathSegment, "rawPathSegment");

        Escaper escaper = CharEscapers.uriPathSegmentEscaper();
        return escaper.escape(rawPathSegment);
    }

    /**
     * Escapes specified URI path.
     * <p>
     * Please note that, unlike {@link #escapePathSegment(String)} this method
     * will escape the whole rawPath, not just a path segment. Therefore, the URI
     * path segment separator (<tt>/</tt>) will not be escaped.
     *
     * @param rawPath the URI path to escape
     * @return the escaped file
     * @throws IllegalArgumentException if the file is null, the file path is
     *                                  empty or the file path is not absolute
     */
    public static String escapePath(String rawPath) {
        Preconditions.notNull(rawPath, "rawPath");
        Preconditions.checkArgument(!rawPath.equals(""), "rawPath cannot be empty");
        Preconditions.checkArgument(rawPath.startsWith("/"), "rawPath must start with </>");

        Escaper escaper = CharEscapers.uriPathEscaper();
        return escaper.escape(rawPath);
    }

    public static String escapeQueryParam(String rawQueryParam) {
        Preconditions.notNull(rawQueryParam, "rawQueryParam");

        Escaper escaper = CharEscapers.uriQueryStringParameterEscaper();
        return escaper.escape(rawQueryParam);
    }

    public static String escapeFragment(String rawFragment) {
        Preconditions.notNull(rawFragment, "rawFragment");

        Escaper escaper = CharEscapers.getUriFragmentEscaper();
        return escaper.escape(rawFragment);
    }

    /**
     * Returns a file object corresponding to the specified file URI string,
     * if the URI scheme is <tt>file://</tt> or if the URI scheme is not
     * specified.
     *
     * @param uriString
     * @return
     * @throws IllegalArgumentException if the specified URI string is null,
     *                                  the URI path is empty or the URI scheme
     *                                  is specified, but it is not
     *                                  <tt>file://</tt>
     */
    public static File toFile(String uriString) {
        Preconditions.notNull(uriString, "uriString");
        Preconditions.checkArgument(!uriString.equals(""), "URI string cannot be empty");
        uriString = replaceSpacesWithPercentEncoding(uriString);

        try {
            URI uri = new URI(uriString);
            return toFile(uri);
        } catch (java.net.URISyntaxException e) {
            throw URISyntaxException.forURI(uriString, e);
        }
    }

    /**
     * Returns a file object corresponding to the specified file URI,
     * if the URI scheme is <tt>file://</tt> or if the URI scheme is not
     * specified.
     *
     * @param uri
     * @return
     * @throws IllegalArgumentException if the specified URI is null,
     *                                  the URI path is empty or the URI scheme
     *                                  is specified, but it is not
     *                                  <tt>file://</tt>
     */
    public static File toFile(URI uri) {
        Preconditions.notNull(uri, "uri");
        Preconditions.checkArgument(!uri.getPath().equals(""), "URI path cannot be an empty string");

        checkFileURIScheme(uri);
        String pathName = uri.getPath();
        return new File(pathName);
    }

    /**
     * @see #fromFile(File)
     */
    public static String fromFile(String filePathName) {
        Preconditions.notNull(filePathName, "filePathName");
        return fromFile(new File(filePathName));
    }

    /**
     * @see #fromFile(File, boolean)
     */
    public static String fromFile(String filePathName, boolean includeScheme) {
        Preconditions.notNull(filePathName, "filePathName");
        return fromFile(new File(filePathName), includeScheme);
    }

    /**
     * Convenient method to invoke {@link #fromFile(File)} passing
     * <tt>true</tt> as <tt>includeScheme</tt> parameter.
     *
     * @see #fromFile(File, boolean)
     */
    public static String fromFile(File file) {
        return fromFile(file, true);
    }

    /**
     * Returns the URI string corresponding to specified file.
     * <p>
     * The path name denoted by specified file must be an absolute path.
     *
     * @param file          the file object corresponding to the absolute path name to
     *                      convert to URI string
     * @param includeScheme whether to include the URI <tt>file</tt> scheme or not
     * @return the URI string corresponding to the file
     * @throws IllegalArgumentException if the file is <tt>null</tt>, the file
     *                                  path is empty or the file path is not
     *                                  absolute
     */
    public static String fromFile(File file, boolean includeScheme) {
        Preconditions.notNull(file, "file");
        Preconditions.checkArgument(!file.getPath().equals(""), "file path cannot be empty");
        Preconditions.checkArgument(file.isAbsolute(), "file path must be absolute");

        // split file path in segments
        LinkedList<String> pathSegments = new LinkedList<>();
        File currentFile = file;
        do {
            String currentFileName = currentFile.getName();
            if (!currentFileName.equals("")) {
                pathSegments.push(currentFileName);
            }
            currentFile = currentFile.getParentFile();
        } while (currentFile != null);

        StringBuilder uriStringBuilder = new StringBuilder();
        if (includeScheme) {
            uriStringBuilder.append(URIScheme.FILE.getScheme()).append("://");
        }
        // build the URI path segment by segment, escaping each segment
        for (String pathSegment : pathSegments) {
            String pathSegmentEscaped = URIs.escapePathSegment(pathSegment);
            uriStringBuilder
                    .append("/")
                    .append(pathSegmentEscaped);
        }
        return uriStringBuilder.toString();
    }

    /**
     * Creates a new URI.
     *
     * @return
     */
    public static URIBuilder<? extends URIBuilder> newURI() {
        return new StandardURIBuilder();
    }

    /**
     * Creates a new URI starting from specified URI string.
     *
     * @param uriString
     * @return the URI builder to construct the URI
     */
    public static URIBuilder<? extends URIBuilder> newURI(String uriString) {
        return new StandardURIBuilder(uriString);
    }

    /**
     * Creates a new URI starting from specified URI.
     *
     * @param uri
     * @return
     */
    public static URIBuilder<? extends URIBuilder> newURI(URI uri) {
        return new StandardURIBuilder(uri);
    }

    /**
     * Adds specified parameter to specified URI.
     *
     * @param uri              the string representation of the URI (it can be
     *                         in the file URI or in the Spazio URI format)
     * @param key              the key of the parameter to add
     * @param value            the value of the parameter to add
     * @param addParameterMode the modality to use when adding the parameter
     * @return the URI with the added parameter
     */
    public static String addParameterToURI(
            String uri,
            String key,
            String value,
            AddParameterMode addParameterMode) {

        Preconditions.notNull(uri, "uri");
        Preconditions.notNull(key, "key");
        Preconditions.notNull(addParameterMode, "addParameterMode");

        final URIBuilder<? extends URIBuilder> uriBuilder = newURI(uri);

        if (addParameterMode.equals(AddParameterMode.ADD)) {
            uriBuilder.addQueryParameter(key, value);
        } else if (addParameterMode.equals(AddParameterMode.OVERRIDE)) {
            uriBuilder.setQueryParameter(key, value);
        } else if (addParameterMode.equals(AddParameterMode.KEEP_PREVIOUS_VALUE)) {
            List<String> parameterValues = uriBuilder.getQueryParameterValues(key);
            if (parameterValues == null || parameterValues.size() == 0) {
                uriBuilder.setQueryParameter(key, value);
            }
        } else {
            throw new AssertionError("add parameter mode <" + addParameterMode + "> not expected");
        }

        return uriBuilder.buildString();
    }

    /**
     * Replaces the spaces inside the URI characters with its percent encoding
     * (<tt>%20</tt>).
     *
     * @param uriString
     * @return
     * @throws IllegalArgumentException when <tt>fileURI</tt> is <tt>null</tt>
     */
    static String replaceSpacesWithPercentEncoding(String uriString) {
        Preconditions.notNull(uriString, "uriString");
        return uriString.replace(" ", "%20");
    }

    static void checkFileURIScheme(URI fileURI) {
        String scheme = fileURI.getScheme();
        if (scheme != null && !scheme.equalsIgnoreCase(URIScheme.FILE.getScheme())) {
            throw new IllegalArgumentException("unexpected URI scheme " +
                    "[expected=<" + URIScheme.FILE.getScheme() + ">, actual=<" + scheme + ">]");
        }
    }

    public enum AddParameterMode {

        /**
         * If a parameter with the same key already exists, another parameter
         * with the same key and the new specified value will be added.
         */
        ADD,

        /**
         * If a parameter with the same key already exists, the existing
         * parameter value will be replaced with the new specified value.
         */
        OVERRIDE,

        /**
         * If a parameter with the same key already exists, the already
         * existing value will be kept (the URI will not be modified).
         */
        KEEP_PREVIOUS_VALUE

    }

}
