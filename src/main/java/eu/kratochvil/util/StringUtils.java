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
 *
 * @author Jiri Kratochvil (jiri.kratochvil@jetminds.com)
 * @version $Revision:$
 */
public class StringUtils extends org.apache.commons.lang.StringUtils {

    /**
     * Regular exception to recognize if string starts and ends with same element
     */
    private static final String IS_XML_PATTERN_STR = "<(\\S+?)(.*?)>(.*?)</\\1>";


    private static final Pattern isXmlPattern = Pattern.compile(IS_XML_PATTERN_STR,
            Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE);


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


}
