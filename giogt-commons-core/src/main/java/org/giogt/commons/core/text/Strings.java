package org.giogt.commons.core.text;


import org.giogt.commons.core.Preconditions;
import org.giogt.commons.core.collections.MultivaluedHashMap;
import org.giogt.commons.core.collections.MultivaluedMap;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Some common string manipulation utilities.
 */
public class Strings {

    // \u3000 is the double-byte space character in UTF-8
    // \u00A0 is the non-breaking space character (&nbsp;)
    // \u2007 is the figure space character (&#8199;)
    // \u202F is the narrow non-breaking space character (&#8239;)
    public static final String WHITE_SPACES = " \r\n\t\u3000\u00A0\u2007\u202F";

    // prevent instantiation
    private Strings() {
    }

    /**
     * Split "str" by run of delimiters and return.
     */
    public static String[] split(String str, String delims) {
        return split(str, delims, false);
    }

    /**
     * Split "str" into tokens by delimiters and optionally remove white spaces
     * from the split tokens.
     *
     * @param trimTokens if true, then trim the tokens
     */
    public static String[] split(String str, String delims, boolean trimTokens) {
        StringTokenizer tokenizer = new StringTokenizer(str, delims);
        int n = tokenizer.countTokens();
        String[] list = new String[n];
        for (int i = 0; i < n; i++) {
            if (trimTokens) {
                list[i] = tokenizer.nextToken().trim();
            } else {
                list[i] = tokenizer.nextToken();
            }
        }
        return list;
    }

    /**
     * Short hand for <code>split(str, delims, true)</code>
     */
    public static String[] splitAndTrim(String str, String delims) {
        return split(str, delims, true);
    }

    /**
     * Parse comma-separated list of ints and return as array.
     */
    public static int[] toIntArray(String str) throws IllegalArgumentException {
        StringTokenizer tokenizer = new StringTokenizer(str, ",");
        int n = tokenizer.countTokens();
        int[] list = new int[n];
        for (int i = 0; i < n; i++) {
            String token = tokenizer.nextToken();
            list[i] = Integer.parseInt(token);
        }
        return list;
    }

    /**
     * Parse comma-separated list of longs and return as array.
     */
    public static long[] toLongArray(String str) throws IllegalArgumentException {
        StringTokenizer tokenizer = new StringTokenizer(str, ",");
        int n = tokenizer.countTokens();
        long[] list = new long[n];
        for (int i = 0; i < n; i++) {
            String token = tokenizer.nextToken();
            list[i] = Long.parseLong(token);
        }
        return list;
    }

    /**
     * Concatenates the given int[] array into one String, inserting a
     * delimiter between each pair of elements.
     */
    public static String fromIntArray(int[] tokens, String delimiter) {
        if (tokens == null) return "";
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < tokens.length; i++) {
            if (i > 0 && delimiter != null) {
                result.append(delimiter);
            }
            result.append(String.valueOf(tokens[i]));
        }
        return result.toString();
    }

    /**
     * Concatenates the given long[] array into one String, inserting a
     * delimiter between each pair of elements.
     */
    public static String fromLongArray(long[] tokens, String delimiter) {
        if (tokens == null) return "";
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < tokens.length; i++) {
            if (i > 0 && delimiter != null) {
                result.append(delimiter);
            }
            result.append(String.valueOf(tokens[i]));
        }
        return result.toString();
    }

    /**
     * Re-formats the given string to a fixed width by inserting
     * carriage returns and trimming unnecessary whitespace.
     *
     * @param str   the string to format
     * @param width the fixed width (in characters)
     */
    public static String fixedWidth(String str, int width) {
        String[] lines = split(str, "\n");
        return fixedWidth(lines, width);
    }

    /**
     * Re-formats the given array of lines to a fixed width by inserting
     * carriage returns and trimming unnecessary whitespace.
     *
     * @param lines - array of lines to format
     * @param width - the fixed width (in characters)
     */
    public static String fixedWidth(String[] lines, int width) {
        StringBuilder formatStr = new StringBuilder();

        for (int i = 0; i < lines.length; i++) {
            int curWidth = 0;
            if (i != 0) {
                formatStr.append("\n");
            }
            // a small optimization
            if (lines[i].length() <= width) {
                formatStr.append(lines[i]);
                continue;
            }
            String[] words = splitAndTrim(lines[i], WHITE_SPACES);
            for (int j = 0; j < words.length; j++) {
                if (curWidth == 0 || (curWidth + words[j].length()) < width) {
                    // add a space if we're not at the beginning of a line
                    if (curWidth != 0) {
                        formatStr.append(" ");
                        curWidth += 1;
                    }
                    curWidth += words[j].length();
                    formatStr.append(words[j]);
                } else {
                    formatStr.append("\n");
                    curWidth = words[j].length();
                    formatStr.append(words[j]);
                }
            }
        }

        return formatStr.toString();
    }

    /**
     * Indents the given String per line.
     *
     * @param iString      The string to indent.
     * @param iIndentDepth The depth of the indentation.
     * @return The indented string.
     */
    public static String indent(String iString, int iIndentDepth) {
        StringBuilder spacer = new StringBuilder();
        spacer.append("\n");
        for (int i = 0; i < iIndentDepth; i++) {
            spacer.append("  ");
        }
        return iString.replace("\n", spacer.toString());
    }

    public static String trimLeading(String str) {
        return (str == null) ? null : CharMatcher.WHITESPACE.trimLeadingFrom(str);
    }

    public static String trimTrailing(String str) {
        return (str == null) ? null : CharMatcher.WHITESPACE.trimTrailingFrom(str);
    }

    public static String trim(String str) {
        return (str == null) ? null : CharMatcher.WHITESPACE.trimFrom(str);
    }

    /**
     * Returns the specified string without the specified prefix if the
     * string starts with the specified prefix, <tt>null</tt> otherwise.
     */
    public static String stripPrefix(String str, String prefix) {
        return str.startsWith(prefix) ? str.substring(prefix.length()) : null;
    }

    /**
     * Case insensitive version of {@link #stripPrefix(String, String)}.
     */
    public static String stripPrefixIgnoreCase(String str, String prefix) {
        if (str.length() >= prefix.length()
                && str.substring(0, prefix.length()).equalsIgnoreCase(prefix)) {
            return str.substring(prefix.length());
        }

        return null;
    }

    /**
     * Short for invoking {@link #substring(String, int, int)}, with
     * <tt>string.length()</tt> as end index.
     *
     * @param string
     * @param beginIndex
     * @return the sub-string
     * @throws IllegalArgumentException when input string is <tt>null</tt>
     * @see #substring(String, int, int)
     */
    public static String substring(String string, int beginIndex) {
        Preconditions.notNull(string, "string");
        return substring(string, beginIndex, string.length());
    }

    /**
     * Similar to {@link String#substring(int, int)}, but with some important
     * differences. The behaviour is the following:
     * <ul>
     * <li>
     * if the begin index is greater as the end index, it throws a
     * {@link StringIndexOutOfBoundsException} (same behaviour as
     * {@link String#substring(int, int)});
     * </li>
     * <li>
     * if the string length is less than <tt>beginIndex</tt>, it returns an
     * empty string (instead of throwing an out of bounds exception);
     * </li>
     * <li>
     * if the specified string is less than than <tt>endIndex</tt>, it returns
     * a sub-string starting from specified <tt>beginIndex</tt> up to the end
     * of the input string (instead of throwing an out of bounds exception);
     * </li>
     * <li>
     * in all the other cases, it returns a sub-string starting from specified
     * <tt>beginIndex</tt> and ending at <tt>endIndex - 1</tt> (same behaviour
     * as {@link String#substring(int, int)} - please note that if
     * <tt>beginIndex</tt> and <tt>endIndex</tt> are the same, an empty string
     * is returned.
     * </li>
     * </ul>
     *
     * @param string     the input string
     * @param beginIndex the begin index: the position where the sub-string
     *                   will start
     * @param endIndex   the end index: the sub-string will stop at <tt>end
     *                   index - 1</tt> or at the input string length
     *                   (whichever the minimum value between the two is)
     * @return the sub-string
     * @throws IllegalArgumentException        when input string is <tt>null</tt>
     * @throws StringIndexOutOfBoundsException when <tt>beginIndex</tt> is
     *                                         greater than <tt>endIndex</tt>
     */
    public static String substring(String string, int beginIndex, int endIndex) {
        Preconditions.notNull(string, "string");

        if (string.length() < beginIndex) {
            return "";
        }
        return string.substring(beginIndex, Math.min(endIndex, string.length()));
    }

    /**
     * Like String.indexOf() except that it will look for any of the
     * characters in 'chars'.
     */
    public static int indexOfChars(String str, String chars, int fromIndex) {
        final int len = str.length();

        for (int pos = fromIndex; pos < len; pos++) {
            if (chars.indexOf(str.charAt(pos)) >= 0) {
                return pos;
            }
        }

        return -1;
    }

    /**
     * Like String.indexOf() except that it will look for any of the
     * characters in 'chars'.
     */
    public static int indexOfChars(String str, String chars) {
        return indexOfChars(str, chars, 0);
    }

    /**
     * Like String.replace() except that it accepts any number of old chars.
     * Replaces any occurrences of 'oldchars' in 'str' with 'newchar'.
     * Example: replaceChars("Hello, world!", "H,!", ' ') returns " ello  world "
     */
    public static String replaceChars(String str, String oldchars, char newchar) {
        int pos = indexOfChars(str, oldchars);
        if (pos == -1) {
            return str;
        }

        StringBuilder buf = new StringBuilder(str);
        do {
            buf.setCharAt(pos, newchar);
            pos = indexOfChars(str, oldchars, pos + 1);
        } while (pos != -1);

        return buf.toString();
    }

    /**
     * Replaces microsoft "smart quotes" (curly " and ') with their
     * ascii counterparts.
     */
    public static String replaceSmartQuotes(String str) {
        // See http://www.microsoft.com/typography/unicode/1252.htm
        str = replaceChars(str, "\u0091\u0092\u2018\u2019", '\'');
        str = replaceChars(str, "\u0093\u0094\u201c\u201d", '"');
        return str;
    }


    /**
     * Removes all the characters contained in specified <tt>chars</tt> string
     * from specified input <tt>string</tt>.
     *
     * @param string the input string
     * @param chars  string containing the characters to remove from string
     * @return
     */
    public static String removeChars(String string, String chars) {
        Preconditions.notNull(string, "string");
        if (chars == null || chars.length() == 0 || string.length() == 0) {
            return string;
        }

        for (int i = 0; i < chars.length(); i++) {
            string = string.replace(String.valueOf(chars.charAt(i)), "");
        }
        return string;
    }

    /**
     * Convert a string of hex digits to a byte array, with the first
     * byte in the array being the MSB. The string passed in should be
     * just the raw digits (upper or lower case), with no leading
     * or trailing characters (like '0x' or 'h').
     * An odd number of characters is supported.
     * If the string is empty, an empty array will be returned.
     * <p>
     * This is significantly faster than using
     * new BigInteger(str, 16).toByteArray();
     * especially with larger strings. Here are the results of some
     * microbenchmarks done on a P4 2.8GHz 2GB RAM running
     * linux 2.4.22-gg11 and JDK 1.5 with an optimized build:
     * <p>
     * String length    toBytesFromHexString (usec)   BigInteger
     * -----------------------------------------------------
     * 16                       0.570                 1.43
     * 256                      8.21                 44.4
     * 1024                    32.8                 526
     * 16384                  546                121000
     */
    public static byte[] toBytesFromHexString(String str) {
        byte[] bytes = new byte[(str.length() + 1) / 2];
        if (str.length() == 0) {
            return bytes;
        }
        bytes[0] = 0;
        int nibbleIdx = (str.length() % 2);
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (!isHex(c)) {
                throw new IllegalArgumentException("string contains non-hex chars");
            }
            if ((nibbleIdx % 2) == 0) {
                bytes[nibbleIdx >> 1] = (byte) (hexValue(c) << 4);
            } else {
                bytes[nibbleIdx >> 1] += (byte) hexValue(c);
            }
            nibbleIdx++;
        }
        return bytes;
    }

    /**
     * Converts any instances of "\r" or "\r\n" style EOLs into "\n" (Line Feed).
     */
    public static String convertEOLToLF(String input) {
        StringBuilder res = new StringBuilder(input.length());
        char[] s = input.toCharArray();
        int from = 0;
        final int end = s.length;
        for (int i = 0; i < end; i++) {
            if (s[i] == '\r') {
                res.append(s, from, i - from);
                res.append('\n');
                if (i + 1 < end && s[i + 1] == '\n') {
                    i++;
                }

                from = i + 1;
            }
        }

        if (from == 0) {   // no \r!
            return input;
        }

        res.append(s, from, end - from);
        return res.toString();
    }

    public static String convertEOLToCRLF(String input) {
        return input.replaceAll("(\r\n|\r|\n)", "\r\n");
    }

    /**
     * Returns a string consisting of "s", plus enough copies of "padChar" on the
     * left hand side to make the length of "s" equal to or greater than len (if
     * "s" is already longer than "len", then "s" is returned).
     */
    public static String padLeft(String s, int len, char padChar) {
        if (s.length() >= len) {
            return s;
        } else {
            StringBuilder sb = new StringBuilder();
            int n = len - s.length();
            for (int i = 0; i < n; i++) {
                sb.append(padChar);
            }
            sb.append(s);
            return sb.toString();
        }
    }

    /**
     * Returns a string consisting of "s", plus enough copies of "padChar" on the
     * right hand side to make the length of "s" equal to or greater than len (if
     * "s" is already longer than "len", then "s" is returned).
     */
    public static String padRight(String s, int len, char padChar) {
        if (s.length() >= len) {
            return s;
        } else {
            StringBuilder sb = new StringBuilder();
            int n = len - s.length();
            sb.append(s);
            for (int i = 0; i < n; i++) {
                sb.append(padChar);
            }
            return sb.toString();
        }
    }

    /**
     * Returns a string consisting of "s", with each of the first "len" characters
     * replaced by "maskChar" character.
     */
    public static String maskLeft(String s, int len, char maskChar) {
        if (len <= 0) {
            return s;
        }
        len = Math.min(len, s.length());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append(maskChar);
        }
        sb.append(s.substring(len));
        return sb.toString();
    }

    /**
     * Returns a string consisting of "s", with each of the last "len" characters
     * replaces by "maskChar" character.
     */
    public static String maskRight(String s, int len, char maskChar) {
        if (len <= 0) {
            return s;
        }
        len = Math.min(len, s.length());
        StringBuilder sb = new StringBuilder();
        sb.append(s.substring(0, s.length() - len));
        for (int i = 0; i < len; i++) {
            sb.append(maskChar);
        }
        return sb.toString();
    }

    private static boolean isOctal(char c) {
        return (c >= '0') && (c <= '7');
    }

    private static boolean isHex(char c) {
        return ((c >= '0') && (c <= '9')) ||
                ((c >= 'a') && (c <= 'f')) ||
                ((c >= 'A') && (c <= 'F'));
    }

    private static int hexValue(char c) {
        if ((c >= '0') && (c <= '9')) {
            return (c - '0');
        } else if ((c >= 'a') && (c <= 'f')) {
            return (c - 'a') + 10;
        } else {
            return (c - 'A') + 10;
        }
    }

    /**
     * Removes characters between matching charLimit chars. For example
     * cropBetween("ab^cd^ef^gh^hi", '^') will return "abefhi". It will
     * consider sequences of 2 charLimit as one charLimit in the output.
     *
     * @param in    - the string to process
     * @param limit - the limit of the string(s) to remove
     * @return String - the cropped string
     */
    public static String cropBetween(String in, String limit) {
        StringBuilder out = new StringBuilder();
        int lastPos = 0;
        int lenLimit = limit.length();
        boolean modeAdd = true;
        int pos = -1;
        while ((pos = in.indexOf(limit, lastPos)) >= 0) {
            if (modeAdd) {
                out.append(in.substring(lastPos, pos));
            }
            modeAdd = !modeAdd;
            lastPos = pos + lenLimit;
        }

        // add the remainings
        if (modeAdd) {
            out.append(in.substring(lastPos));
        }

        return out.toString();
    }

    /**
     * Invokes {@link #toList(String, String, boolean)}, passing <tt>true</tt>
     * for trim.
     */
    public static List<String> toList(
            String in,
            String delimiter) {

        return toList(in, delimiter, true);
    }

    /**
     * Converts specified input string to a list of strings by extracting it
     * in sub-strings, using specified delimiter
     *
     * @param in        input string
     * @param delimiter the delimiting string
     * @param trim      if true, trims the strings before adding them to the list
     * @return the list of extracted sub-strings
     */
    public static List<String> toList(
            String in,
            String delimiter,
            boolean trim) {

        if (in == null) {
            return null;
        }

        ArrayList<String> out = new ArrayList<String>();
        toCollection(in, delimiter, trim, out);
        return out;
    }

    /**
     * Invokes {@link #toList(String, String, boolean)} , passing <tt>true</tt>
     * for trim.
     */
    public static String getListValue(
            String in,
            String delimiter,
            int index) {

        return getListValue(in, delimiter, index, true);
    }

    /**
     * Parses a list from specified input string using
     * {@link #toList(String, String, boolean)}, but returns
     * only the value corresponding to the specified index, if present.
     *
     * If not present, an empty string is returned.
     */
    public static String getListValue(
            String in,
            String delimiter,
            int index,
            boolean trim) {

        List<String> list = toList(in, delimiter, trim);

        String element;
        if (index <= (list.size() - 1)) {
            element = list.get(index);
        } else {
            element = "";
        }
        return element;
    }

    /**
     * Converts specified input string to a set of strings by extracting it
     * in sub-strings, using specified delimiter
     *
     * @param in        input string
     * @param delimiter the delimiting string
     * @param trim      if true, trims the strings before adding them to the list
     * @return the set of extracted sub-strings
     */
    public static Set toSet(String in, String delimiter, boolean trim) {
        if (in == null) {
            return null;
        }

        HashSet<String> out = new HashSet<String>();
        toCollection(in, delimiter, trim, out);
        return out;
    }

    /**
     * Converts a delimited string to a collection of strings. Sub-strings between
     * delimiters are extracted from the string and added to a collection that is
     * provided by the caller.
     *
     * @param in         The delimited input string to process
     * @param delimiter  The string delimiting entries in the input string.
     * @param trim       Whether to trim the sub-strings before adding to the
     *                   collection or not
     * @param collection The collection to which the strings will be added. If
     *                   <code>null</code>, a new <code>List</code> will be created.
     * @return The collection to which the sub-strings were added. This is
     * syntactic sugar to allow call chaining.
     */
    public static Collection<String> toCollection(
            String in,
            String delimiter,
            boolean trim,
            Collection<String> collection) {

        if (in == null) {
            return null;
        }
        if (collection == null) {
            collection = new ArrayList<>();
        }
        if (delimiter == null || delimiter.length() == 0) {
            collection.add(in);
            return collection;
        }

        int fromIndex = 0;
        int pos;
        while ((pos = in.indexOf(delimiter, fromIndex)) >= 0) {
            String interim = in.substring(fromIndex, pos);
            if (trim) {
                interim = trim(interim);
            }
            if (!trim || interim.length() > 0) {
                collection.add(interim);
            }

            fromIndex = pos + delimiter.length();
        }

        String interim = in.substring(fromIndex);
        if (trim) {
            interim = trim(interim);
        }
        if (!trim || interim.length() > 0) {
            collection.add(interim);
        }

        return collection;
    }

    /**
     * This concatenates the elements of a collection in a string
     *
     * @param in        - the collection that has to be concatenated
     * @param separator - a string to separate the elements from the list
     * @return String
     */
    public static String fromCollection(
            Collection<?> in, String separator) {

        if (in == null) {
            return null;
        }
        return fromIterator(in.iterator(), separator);
    }

    public static String fromIterator(
            Iterator<?> it, String separator) {

        if (it == null) {
            return null;
        }

        StringBuilder out = new StringBuilder();
        while (it.hasNext()) {
            if (out.length() > 0) {
                out.append(separator);
            }
            Object next = it.next();
            if (next != null) {
                out.append(next.toString());
            }
        }

        return out.toString();
    }

    /**
     * Invokes {@link #toMap(String, String, String, boolean, boolean)},
     * passing <tt>true</tt> for both trim keys and trim entries.
     */
    public static Map<String, String> toMap(
            String in,
            String delimEntry,
            String delimKey) {

        return toMap(in, delimEntry, delimKey, true, true);
    }

    /**
     * Converts a string to a Map. It will first split the string into
     * entries using delimEntry. Then each entry is split into a key and a value
     * using delimKey. By default we trim the keys. Use doStripEntry to strip
     * also the entries
     *
     * @param in          - the string to be processed
     * @param delimEntry  - delimiter for the entries
     * @param delimKey    - delimiter between keys and values
     * @param trimKeys    - trim keys before inserting in the map
     * @param trimEntries - trim entries before inserting in the map
     * @return the map
     */
    public static Map<String, String> toMap(
            String in,
            String delimEntry,
            String delimKey,
            boolean trimKeys,
            boolean trimEntries) {

        if (isEmpty(in)) {
            return Collections.emptyMap();
        }

        Map<String, String> out = new HashMap<String, String>();

        if (isEmpty(delimEntry) || isEmpty(delimKey)) {
            // the  string is
            out.put(trim(in), "");
            return out;
        }

        List<String> list = toList(in, delimEntry, false);
        int len = delimKey.length();
        for (String entry : list) {
            int pos = entry.indexOf(delimKey);
            if (pos > 0) {
                String key = entry.substring(0, pos);
                if (trimKeys) {
                    key = trim(key);
                }
                String value = entry.substring(pos + len);
                if (trimEntries) {
                    value = trim(value);
                }
                out.put(key, value);
            } else {
                String key = entry;
                if (trimKeys) {
                    key = trim(key);
                }
                out.put(key, "");
            }
        }

        return out;
    }

    /**
     * Invokes {@link #toMultivaluedMap(String, String, String, boolean, boolean)},
     * passing <tt>true</tt> for both trim keys and trim entries.
     */
    public static MultivaluedMap<String, String> toMultivaluedMap(
            String in,
            String delimEntry,
            String delimKey) {

        return toMultivaluedMap(in, delimEntry, delimKey, true, true);
    }

    /**
     * Converts a string to a multi-valued map. It will first split the string
     * into entries using delimEntry. Then each entry is split into a key and a
     * value using delimKey. By default we trim the keys. Use doStripEntry to
     * strip also the entries
     *
     * @param in          - the string to be processed
     * @param delimEntry  - delimiter for the entries
     * @param delimKey    - delimiter between keys and values
     * @param trimKeys    - trim keys before inserting in the map
     * @param trimEntries - trim entries before inserting in the map
     * @return the map
     */
    public static MultivaluedMap<String, String> toMultivaluedMap(
            String in,
            String delimEntry,
            String delimKey,
            boolean trimKeys,
            boolean trimEntries) {

        if (isEmpty(in)) {
            return new MultivaluedHashMap<>();
        }

        MultivaluedMap<String, String> out = new MultivaluedHashMap<>();

        if (isEmpty(delimEntry) || isEmpty(delimKey)) {
            // the input string is just the first and single key, with no value
            if (trimKeys) {
                in = trim(in);
            }
            out.put(in, new ArrayList<String>(0));
            return out;
        }

        List<String> list = toList(in, delimEntry, false);
        int len = delimKey.length();
        for (String entry : list) {
            int pos = entry.indexOf(delimKey);
            if (pos > 0) {
                String key = entry.substring(0, pos);
                if (trimKeys) {
                    key = trim(key);
                }
                String value = entry.substring(pos + len);
                if (trimEntries) {
                    value = trim(value);
                }

                List<String> elems = out.get(key);
                if (elems == null) {
                    elems = new ArrayList<>();
                    out.put(key, elems);
                }
                elems.add(value);
            } else {
                String key = entry;
                if (trimKeys) {
                    key = trim(key);
                }
                out.put(key, new ArrayList<String>());
            }
        }

        return out;
    }

    /**
     * Invokes {@link #getMapValue(String, String, String, String, boolean, boolean)} ,
     * passing <tt>true</tt> for both trim keys and trim entries.
     */
    public static String getMapValue(
            String in,
            String delimEntry,
            String delimKey,
            String key) {

        return getMapValue(in, delimEntry, delimKey, key, true, true);
    }

    /**
     * Parses a map from specified input string using
     * {@link #toMap(String, String, String, boolean, boolean)}, but returns
     * only the value corresponding to the specified key, if present.
     *
     * If not present, an empty string is returned.
     */
    public static String getMapValue(
            String in,
            String delimEntry,
            String delimKey,
            String key,
            boolean trimKeys,
            boolean trimEntries) {

        Map<String, String> map = toMap(in, delimEntry, delimKey, trimKeys, trimEntries);
        return makeSafe(map.get(key));
    }

    /**
     * This function concatenates the elements of a Map in a string with form
     * "<key1><sepKey><value1><sepEntry>...<keyN><sepKey><valueN>"
     *
     * @param in       - the map to be converted
     * @param sepKey   - the separator to put between key and value
     * @param sepEntry - the separator to put between map entries
     * @return String
     */
    public static <K, V> String fromMap(
            Map<K, V> in,
            String sepKey,
            String sepEntry) {

        if (in == null) {
            return null;
        }

        StringBuilder out = new StringBuilder();
        Iterator<Entry<K, V>> it = in.entrySet().iterator();
        while (it.hasNext()) {
            if (out.length() > 0) {
                out.append(sepEntry);
            }
            Entry<K, V> entry = it.next();
            out.append(entry.getKey() + sepKey + entry.getValue());
        }

        return out.toString();
    }

    /**
     * Read a String from an InputStream
     *
     * @param is       input stream
     * @param encoding the encoding to use
     * @return String read from "is"
     */
    public static String fromStream(InputStream is, String encoding) throws IOException {
        return fromStream(is, -1, encoding);
    }

    /**
     * Read a String of up to maxLength bytes from an InputStream
     *
     * @param is        input stream
     * @param maxLength max number of bytes to read from "is". If this is -1, we
     *                  read everything.
     * @param encoding  the encoding to use
     * @return String up to maxLength bytes, read from "is"
     */
    public static String fromStream(
            InputStream is,
            int maxLength,
            String encoding)
            throws IOException {

        byte[] buffer = new byte[4096];
        StringWriter sw = new StringWriter();
        int totalRead = 0;
        int read = 0;

        do {
            sw.write(new String(buffer, 0, read, encoding));
            totalRead += read;
            read = is.read(buffer, 0, buffer.length);
        } while (((-1 == maxLength) || (totalRead < maxLength)) && (read != -1));

        return sw.toString();
    }

    public static String fromUTF8Stream(InputStream is)
            throws IOException {

        return fromStream(is, "UTF-8");
    }

    public static String fromUTF8Stream(
            InputStream is,
            int maxLength)
            throws IOException {

        return fromStream(is, maxLength, "UTF-8");
    }

    /**
     * Helper function for null and empty string testing.
     *
     * @return true iff s == null or s.equals("");
     */
    public static boolean isEmpty(String s) {
        return makeSafe(s).length() == 0;
    }

    /**
     * Helper function for null, empty, and whitespace string testing.
     *
     * @return true if s == null or s.equals("") or s contains only whitespace
     * characters.
     */
    public static boolean isEmptyOrWhitespace(String s) {
        s = makeSafe(s);
        for (int i = 0, n = s.length(); i < n; i++) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Helper function for making null strings safe for comparisons, etc.
     *
     * @return (s == null) ? "" : s;
     */
    public static String makeSafe(String s) {
        return (s == null) ? "" : s;
    }

    /**
     * Helper function for making empty strings into a null.
     *
     * @return null if s is zero length.  otherwise, returns s.
     */
    public static String toNullIfEmpty(String s) {
        return (Strings.isEmpty(s)) ? null : s;
    }

    /**
     * Helper function for turning empty or whitespace strings into a null.
     *
     * @return null if s is zero length or if s contains only whitespace
     * characters.  otherwise, returns s.
     */
    public static String toNullIfEmptyOrWhitespace(String s) {
        return (Strings.isEmptyOrWhitespace(s)) ? null : s;
    }

    /**
     * Serializes a map
     *
     * @param map           A map of String keys to arrays of String values
     * @param keyValueDelim Delimiter between keys and values
     * @param entryDelim    Delimiter between entries
     * @return String A string containing a serialized representation of the
     * contents of the map.
     * <p>
     * e.g. fromArrayMap({"foo":["bar","bar2"],"foo1":["bar1"]}, "=", "&")
     * returns "foo=bar&foo=bar2&foo1=bar1"
     */
    public static String fromArrayMap(
            Map<String, String[]> map,
            String keyValueDelim,
            String entryDelim) {

        Set<Entry<String, String[]>> entrySet = map.entrySet();
        Iterator<Entry<String, String[]>> itor = entrySet.iterator();
        StringWriter sw = new StringWriter();
        while (itor.hasNext()) {
            Entry<String, String[]> entry = itor.next();
            String key = entry.getKey();
            String[] values = entry.getValue();
            for (int i = 0; i < values.length; i++) {
                sw.write(entry.getKey() + keyValueDelim + values[i]);
                if (i < values.length - 1) {
                    sw.write(entryDelim);
                }
            }
            if (itor.hasNext()) {
                sw.write(entryDelim);
            }
        }
        return sw.toString();
    }

    /**
     * Compares two strings, guarding against nulls If both Strings are null we
     * return true
     */
    public static boolean equals(String s1, String s2) {
        if (s1 == s2) {
            return true; // Either both the same String, or both null
        }
        if (s1 != null) {
            if (s2 != null) {
                return s1.equals(s2);
            }
        }
        return false;
    }

    /**
     * Determines if a string contains only ascii characters
     */
    public static boolean isAllAscii(String s) {
        int len = s.length();
        for (int i = 0; i < len; ++i) {
            if ((s.charAt(i) & 0xff80) != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Replaces each non-ascii character in s with its Unicode escape sequence
     * \\uxxxx where xxxx is a hex number. Existing escape sequences won't be
     * affected.
     */
    public static String unicodeEscape(String s) {
        if (isAllAscii(s)) {
            return s;
        }
        StringBuilder sb = new StringBuilder(s.length());
        int len = s.length();
        for (int i = 0; i < len; ++i) {
            char ch = s.charAt(i);
            if (ch <= 127) {
                sb.append(ch);
            } else {
                sb.append("\\u");
                String hexString = Integer.toHexString(ch);
                // Pad with zeros if necessary
                int numZerosToPad = 4 - hexString.length();
                for (int j = 0; j < numZerosToPad; ++j) {
                    sb.append('0');
                }
                sb.append(hexString);
            }
        }
        return sb.toString();
    }

    /**
     * @return a string representation of the given native array.
     */
    public static String toString(float[] array) {
        if (array == null) {
            return "<null>";
        }

        StringBuilder buffer = new StringBuilder();
        buffer.append("[");
        for (int i = 0; i < array.length; i++) {
            buffer.append(array[i]);
            if (i != (array.length - 1)) {
                buffer.append(", ");
            }
        }
        buffer.append("]");
        return buffer.toString();
    }

    /**
     * @return a string representation of the given native array.
     */
    public static String toString(long[] array) {
        if (array == null) {
            return "<null>";
        }

        StringBuilder buffer = new StringBuilder();
        buffer.append("[");
        for (int i = 0; i < array.length; i++) {
            buffer.append(array[i]);
            if (i != (array.length - 1)) {
                buffer.append(", ");
            }
        }
        buffer.append("]");
        return buffer.toString();
    }

    /**
     * @return a string representation of the given native array
     */
    public static String toString(int[] array) {
        if (array == null) {
            return "<null>";
        }

        StringBuilder buffer = new StringBuilder();
        buffer.append("[");
        for (int i = 0; i < array.length; i++) {
            buffer.append(array[i]);
            if (i != (array.length - 1)) {
                buffer.append(", ");
            }
        }
        buffer.append("]");
        return buffer.toString();
    }

    /**
     * @return a string representation of the given array.
     */
    public static String toString(String[] array) {
        if (array == null) return "<null>";

        StringBuilder buffer = new StringBuilder();
        buffer.append("[");
        for (int i = 0; i < array.length; i++) {
            buffer.append("'").append(array[i]).append("'");
            if (i != array.length - 1) {
                buffer.append(", ");
            }
        }
        buffer.append("]");

        return buffer.toString();
    }

    /**
     * Returns the string, in single quotes, or "<null>". Intended only for
     * logging.
     *
     * @param s - the string
     * @return the string, in single quotes, or the string "<null>" if it's null.
     */
    public static String toString(String s) {
        if (s == null) {
            return "<null>";
        } else {
            return new StringBuilder(s.length() + 2).append("'").append(s)
                    .append("'").toString();
        }
    }


    /**
     * @return a string representation of the given native array
     */
    public static String toString(int[][] array) {
        if (array == null) {
            return "<null>";
        }

        StringBuilder buffer = new StringBuilder();
        buffer.append("[");
        for (int i = 0; i < array.length; i++) {
            buffer.append("[");
            for (int j = 0; j < array[i].length; j++) {
                buffer.append(array[i][j]);
                if (j != (array[i].length - 1)) {
                    buffer.append(", ");
                }
            }
            buffer.append("]");
            if (i != array.length - 1) {
                buffer.append(" ");
            }
        }
        buffer.append("]");
        return buffer.toString();
    }

    /**
     * @return a string representation of the given native array.
     */
    public static String toString(long[][] array) {
        if (array == null) return "<null>";

        StringBuilder buffer = new StringBuilder();
        buffer.append("[");
        for (int i = 0; i < array.length; i++) {
            buffer.append("[");
            for (int j = 0; j < array[i].length; j++) {
                buffer.append(array[i][j]);
                if (j != (array[i].length - 1)) {
                    buffer.append(", ");
                }
            }
            buffer.append("]");
            if (i != array.length - 1) {
                buffer.append(" ");
            }
        }
        buffer.append("]");
        return buffer.toString();
    }

    /**
     * @return a String representation of the given object array.
     * The strings are obtained by calling toString() on the
     * underlying objects.
     */
    public static String toString(Object[] obj) {
        if (obj == null) return "<null>";
        StringBuilder tmp = new StringBuilder();
        tmp.append("[");
        for (int i = 0; i < obj.length; i++) {
            tmp.append(obj[i].toString());
            if (i != obj.length - 1) {
                tmp.append(",");
            }
        }
        tmp.append("]");
        return tmp.toString();
    }

    /**
     * Converts a string of enumerations into a list of strings.
     *
     * @param enumList
     * @param <E>
     * @return
     */
    public static <E extends Enum<E>> List<String> toStringList(List<E> enumList) {
        if (enumList == null) {
            return null;
        }

        List<String> strList = new ArrayList<>(enumList.size());
        for (Enum<E> e : enumList) {
            if (e == null) {
                strList.add(null);
            } else {
                strList.add(e.toString());
            }
        }
        return strList;
    }

    public static InputStream toStream(String str, String encoding) {

        try {
            return new ByteArrayInputStream(str.getBytes(encoding));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("encoding <" + encoding + "> not supported", e);
        }
    }

    public static InputStream toUTF8Stream(String str) {
        try {
            return new ByteArrayInputStream(str.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError("UTF-8 should always be supported");
        }
    }

    private static char[] hexChars = {'0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * Convert a byte array to a hex-encoding string: "a33bff00..."
     */
    public static String fromBytesToHexString(final byte[] bytes) {
        return fromBytesToHexString(bytes, null);
    }

    /**
     * Convert a byte array to a hex-encoding string with the specified
     * delimiter: "a3&lt;delimiter&gt;3b&lt;delimiter&gt;ff..."
     */
    public static String fromBytesToHexString(
            final byte[] bytes,
            Character delimiter) {

        StringBuffer hex =
                new StringBuffer(bytes.length * (delimiter == null ? 2 : 3));
        int nibble1, nibble2;
        for (int i = 0; i < bytes.length; i++) {
            nibble1 = (bytes[i] >>> 4) & 0xf;
            nibble2 = bytes[i] & 0xf;
            if (i > 0 && delimiter != null) hex.append(delimiter.charValue());
            hex.append(hexChars[nibble1]);
            hex.append(hexChars[nibble2]);
        }
        return hex.toString();
    }

    /**
     * Convert a byte array to a String using Latin-1 (aka ISO-8859-1) encoding.
     * <p>
     * Note: something is probably wrong if you're using this method. Either
     * you're dealing with legacy code that doesn't support i18n or you're
     * using a third-party library that only deals with Latin-1. New code
     * should (almost) always uses UTF-8 encoding.
     *
     * @return the decoded String or null if ba is null
     */
    public static String fromBytesLatin1(final byte[] ba) {
        // ISO-8859-1 should always be supported
        return fromBytes(ba, "ISO-8859-1");
    }

    /**
     * Convert a String to a byte array using Latin-1 (aka ISO-8859-1) encoding.
     * If any character in the String is not Latin-1 (meaning it's high 8 bits
     * are not all zero), then the returned byte array will contain garbage.
     * Therefore, only use this if you know all your characters are within
     * Latin-1.
     * <p>
     * Note: something is probably wrong if you're using this method. Either
     * you're dealing with legacy code that doesn't support i18n or you're
     * using a third-party library that only deals with Latin-1. New code
     * should (almost) always uses UTF-8 encoding.
     *
     * @return the encoded byte array or null if str is null
     */
    public static byte[] toBytesLatin1(final String str) {
        // ISO-8859-1 should always be supported
        return toBytes(str, "ISO-8859-1");
    }

    /**
     * Convert a byte array to a String using UTF-8 encoding.
     *
     * @return the decoded String or null if ba is null
     */
    public static String fromBytesUtf8(final byte[] ba) {
        // UTF-8 should always be supported
        return fromBytes(ba, "UTF-8");
    }

    /**
     * Convert a String to a byte array using UTF-8 encoding.
     *
     * @return the encoded byte array or null if str is null
     */
    public static byte[] toBytesUtf8(final String str) {
        // UTF-8 should always be supported
        return toBytes(str, "UTF-8");
    }

    /**
     * Convert a byte array to a String using the specified encoding.
     *
     * @param encoding the encoding to use
     * @return the decoded String or null if ba is null
     */
    private static String fromBytes(
            final byte[] ba,
            final String encoding) {

        if (ba == null) {
            return null;
        }

        try {
            return new String(ba, encoding);
        } catch (final UnsupportedEncodingException e) {
            throw new RuntimeException("encoding <" + encoding + "> not supported" + e);
        }
    }

    /**
     * Convert a byte array to a String using the specified encoding.
     *
     * @param encoding the encoding to use
     * @return the decoded String or null if ba is null
     */
    private static String fromBytes(
            final byte[] ba,
            final Charset encoding) {

        if (ba == null) {
            return null;
        }

        return new String(ba, encoding);
    }

    /**
     * Convert a String to a byte array using the specified encoding.
     *
     * @param encoding the encoding to use
     * @return the encoded byte array or null if str is null
     */
    public static byte[] toBytes(
            final String str,
            final String encoding) {

        if (str == null) {
            return null;
        }

        try {
            return str.getBytes(encoding);
        } catch (final UnsupportedEncodingException e) {
            throw new Error(encoding + " not supported! Original exception: " + e);
        }
    }

    /**
     * Convert a String to a byte array using the specified encoding.
     *
     * @param encoding the encoding to use
     * @return the encoded byte array or null if str is null
     */
    public static byte[] toBytes(
            final String str,
            final Charset encoding) {

        if (str == null) {
            return null;
        }

        return str.getBytes(encoding);
    }

    /**
     * Convert an array of bytes into a List of Strings using UTF-8. A line is
     * considered to be terminated by any one of a line feed ('\n'), a carriage
     * return ('\r'), or a carriage return followed immediately by a linefeed.
     * <p>
     * Can be used to parse the output of
     *
     * @param bytes the array to convert
     * @return A new mutable list containing the Strings in the input array. The
     * list will be empty if bytes is empty or if it is null.
     */
    public static List<String> bytesToStringList(byte[] bytes) {
        List<String> lines = new ArrayList<String>();

        if (bytes == null) {
            return lines;
        }

        BufferedReader r = null;

        try {
            r = new BufferedReader(
                    new InputStreamReader(
                            new ByteArrayInputStream(bytes),
                            "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            // If UTF-8 is not supported we are in big trouble.
            throw new RuntimeException(e);
        }

        try {
            try {
                for (String line = r.readLine(); line != null; line = r.readLine()) {
                    lines.add(line);
                }
            } finally {
                r.close();
            }
        } catch (IOException e) {
            // I can't think of a reason we'd get here.
            throw new RuntimeException(e);
        }

        return lines;
    }

    /**
     * Safely convert the string to uppercase.
     *
     * @return upper case representation of the String; or null if
     * the input string is null.
     */
    public static String toUpperCase(String src) {
        if (src == null) {
            return null;
        } else {
            return src.toUpperCase();
        }
    }

    /**
     * Returns sourceString concatenated together 'factor' times.
     *
     * @param sourceString The string to repeat
     * @param factor       The number of times to repeat it.
     */
    public static String repeat(String sourceString, int factor) {
        if (factor < 1) {
            return "";
        }
        if (factor == 1) {
            return sourceString;
        }

        StringBuilder sb = new StringBuilder(factor * sourceString.length());

        while (factor > 0) {
            sb.append(sourceString);
            factor--;
        }

        return sb.toString();
    }

    /**
     * Returns a string that is equivalent to the specified string with its
     * first character converted to uppercase as by {@link String#toUpperCase}.
     * The returned string will have the same value as the specified string if
     * its first character is non-alphabetic, if its first character is already
     * uppercase, or if the specified string is of length 0.
     * <p>
     * <p>For example:
     * <pre>
     *    capitalize("foo bar").equals("Foo bar");
     *    capitalize("2b or not 2b").equals("2b or not 2b")
     *    capitalize("Foo bar").equals("Foo bar");
     *    capitalize("").equals("");
     * </pre>
     *
     * @param s the string whose first character is to be uppercased
     * @return a string equivalent to <tt>s</tt> with its first character
     * converted to uppercase
     * @throws NullPointerException if <tt>s</tt> is null
     */
    public static String capitalize(String s) {
        if (s.length() == 0)
            return s;
        char first = s.charAt(0);
        char capitalized = Character.toUpperCase(first);
        return (first == capitalized)
                ? s
                : capitalized + s.substring(1);
    }
}
