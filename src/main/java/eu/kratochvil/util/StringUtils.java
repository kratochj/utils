/*
 * Created on 25.2.2011
 * (C) 2011 ICZ, a.s.
 */
package eu.kratochvil.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Enrichment of standard commons-lang library from <a href="http://commons.apache.org">Apache</a>, which add some
 * new cool and fancy functions
 * <p/>
 * To "standard apache commons StringUtils" library were added theese functions:
 * <ul>
 * <li>{@link #isXmlLike} - check if string value looks life xml</li>
 * <li>{@link #notBlankValue} - when parameter is blank, replace it with another value</li>
 * <li>{@link #transformToPackageName} - Transformation path to java package name</li>
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
                if (xmlString.indexOf("?>") < 0) {
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


}
