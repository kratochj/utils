/*
 * Created on 25.2.2011
 * (C) 2011 ICZ, a.s.
 */
package eu.kratochvil.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import eu.kratochvil.util.helpers.MessageFormatter;

/**
 * Enrichment of standard commons-lang library from <a href="http://commons.apache.org">Apache</a>, which add some
 * new cool and fancy functions
 * <p/>
 * To "standard apache commons StringUtils" library were added theese functions:
 * <ul>
 * <li>{@link #isXmlLike(String)} - check if string value looks life xml</li>
 * <li>{@link #notBlankValue(String, String)} - when parameter is blank, replace it with another value</li>
 * <li>{@link #nullValue(String, String)} - when parameter is null, replace it with another value</li>
 * <li>{@link #transformToPackageName(String)} - Transformation path to java package name</li>
 * <li>{@link #withArgs(String, Object[])} </li>
 * </ul>
 *
 * @author Jiri Kratochvil (jiri.kratochvil@jetminds.com)
 * @version $Revision:$
 * @see org.apache.commons.lang.StringUtils
 */
public class StringUtils extends org.apache.commons.lang.StringUtils {

    /**
     * Regular exception to recognize if string starts and ends with same element
     */
    private static final String IS_XML_PATTERN_STR = "<(\\S+?)(.*?)>(.*?)</\\1>";


    private static final Pattern isXmlPattern = Pattern.compile(IS_XML_PATTERN_STR,
            Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE);


    private static final String PATH_TRANSFORMATION_PATTERN = "[^a-z^A-Z^0-9^.]";

    private static final String PATH_TRANSFORMATION_FOLDER_SEPARATOR_PATTERN = "[/\\\\]";

    private static final String PATH_TRANSFORMATION_PACKAGE_SEPARATOR = ".";

    private static final String PATH_TRANSFORMATION_UNIVERSAL_CHAR = "_";

    /**
     * Method transforms some string into package name by following rules:
     * <ul>
     * <li>folder separators (<code>/</code> or <code>\</code>) are replaced by period (<code>.</code>)
     * <li>Special characters (non-alphabetical, non-numeric) are replaced by underscore (<code>_</code>)
     * </ul>
     * <p/>
     * Examples:
     * <ul>
     * <li><code>/aaaa/bbbb/cccc</code> transformed into <code>aaaa.bbbb.cccc</code></li>
     * <li><code>aaaa/bbbb/cccc</code> transformed into <code>aaaa/bbbb/cccc</code></li>
     * <li><code>\aaaa\bbbb\cccc</code> transformed into <code>aaaa.bbbb.cccc</code></li>
     * <li><code>/aaaa/bbbb/cccc/Something-w@ng</code> transformed into <code>aaaa.bbbb.cccc. Something_w_ng</code></li>
     * </ul>
     *
     * @param path To be transformed
     * @return Transformed pjackage name
     */
    public static String transformToPackageName(String path) {
        String result = path.replaceAll(PATH_TRANSFORMATION_FOLDER_SEPARATOR_PATTERN, PATH_TRANSFORMATION_PACKAGE_SEPARATOR).
                replaceAll(PATH_TRANSFORMATION_PATTERN, PATH_TRANSFORMATION_UNIVERSAL_CHAR);
        if (result.startsWith(".")) {
            return result.substring(1);
        } else {
            return result;
        }
    }

    /**
     * Returns true when param is String representation of xml. It means starts and end with same xml tag.
     * Method doesn't check validity of XML!
     *
     * @param xmlString String to check
     * @return Returns true when string is looks like XML
     */
    public static boolean isXmlLike(String xmlString) {
        if (xmlString != null && xmlString.trim().length() > 0) {
            if (xmlString.startsWith("<?")) {
                // remove xml header
                if (!xmlString.contains("?>")) {
                    return false;
                }
                xmlString = xmlString.substring(xmlString.indexOf("?>") + 2);
            }

            // If we even resemble xml
            if (xmlString.trim().startsWith("<")) {
                Matcher matcher = isXmlPattern.matcher(xmlString);
                return matcher.matches();
            }
        }
        return false;
    }

    /**
     * Method returns value contained in parameter <code>value</code> if this value is not blank.
     * In other case is returnes value in parameter <code>replaceWith</code>.
     * <p/>
     * Behaviour is simmilar to Oracle function <code>nvl</code>
     *
     * @param value       Value to test
     * @param replaceWith This value is returned when <code>value</code> is blank
     * @return Value of parameter <code>value</code> when is not blank. Otherwise value of <code>replaceWith</code>
     *         is returned
     */
    public static String notBlankValue(String value, String replaceWith) {
        if (isBlank(value)) {
            return replaceWith;
        } else {
            return value;
        }
    }

    /**
     * Method returns value contained in parameter <code>value</code> if this value is not null.
     * In other case is returnes value in parameter <code>replaceWith</code>.
     * <p/>
     * Behavior is same as Oracle function <code>nvl</code>
     *
     * @param value       Value to test
     * @param replaceWith This value is returned when <code>value</code> is null
     * @return Value of parameter <code>value</code> when is not null. Otherwise value of <code>replaceWith</code>
     *         is returned
     */
    public static String nullValue(String value, String replaceWith) {
        return (String) ObjectUtils.defaultIfNull(value, replaceWith);
    }


    /**
     * Same principle as the {@link #withArgs(String, Object)} and
     * {@link #withArgs(String, Object, Object)} methods except that any number of
     * arguments can be passed in an array.
     *
     * @param value    The message pattern which will be parsed and formatted
     * @param argArray An array of arguments to be substituted in place of
     *                 formatting anchors
     * @return The formatted message
     */
    public static String withArgs(String value, Object... argArray) {
//    public static String withArgs(String value, Object[] argArray) {
        return MessageFormatter.arrayFormat(value, argArray);
    }

    /**
     * Performs a two argument substitution for the 'messagePattern' passed as
     * parameter.
     * <p/>
     * For example,
     * <p/>
     * <pre>
     * StringUtils.withArgs(&quot;Hi {}. My name is {}.&quot;, &quot;Alice&quot;, &quot;Bob&quot;);
     * </pre>
     * <p/>
     * will return the string "Hi Alice. My name is Bob.".
     *
     * @param value The message pattern which will be parsed and formatted
     * @param arg1  The argument to be substituted in place of the first
     *              formatting anchor
     * @param arg2  The argument to be substituted in place of the second
     *              formatting anchor
     * @return The formatted message
     */
    public static String withArgs(String value, Object arg1, Object arg2) {
        return withArgs(value, new Object[]{arg1, arg2});
    }

    /**
     * Performs single argument substitution for the 'messagePattern' passed as
     * parameter.
     * <p/>
     * For example,
     * <p/>
     * <pre>
     * StringUtils.withArgs(&quot;Hi {}.&quot;, &quot;there&quot;);
     * </pre>
     * <p/>
     * will return the string "Hi there.".
     * <p/>
     *
     * @param value The message pattern which will be parsed and formatted
     * @param arg   The argument to be substituted in place of the formatting
     *              anchor
     * @return The formatted message
     */
    public static String withArgs(String value, Object arg) {
        return withArgs(value, new Object[]{arg});
    }

}
