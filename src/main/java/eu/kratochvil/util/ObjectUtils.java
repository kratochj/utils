/*
 * Created on 17.3.2011
 * (C) 2011 ICZ, a.s.
 */
package eu.kratochvil.util;

/**
 * Enrichment of standard commons-lang library from <a href="http://commons.apache.org">Apache</a>, which add some
 * new cool and fancy functions
 * <p/>
 * To "standard apache commons ObjectUtils" library were added theese functions:
 * <ul>
 * <li>{@link #nullValue} - when parameter is null, replace it with another value</li>
 * </ul>
 *
 * @author Jiri Kratochvil (jiri.kratochvil@jetminds.com)
 * @version $Revision:$
 * @see org.apache.commons.lang.ObjectUtils
 */
public class ObjectUtils extends org.apache.commons.lang.ObjectUtils {

    /**
     * <p>Returns a default value if the object passed is
     * <code>null</code>.</p>
     *
     * <pre>
     * ObjectUtils.defaultIfNull(null, null)      = null
     * ObjectUtils.defaultIfNull(null, "")        = ""
     * ObjectUtils.defaultIfNull(null, "zz")      = "zz"
     * ObjectUtils.defaultIfNull("abc", *)        = "abc"
     * ObjectUtils.defaultIfNull(Boolean.TRUE, *) = Boolean.TRUE
     * </pre>
     *
     * @param object  the <code>Object</code> to test, may be <code>null</code>
     * @param defaultValue  the default value to return, may be <code>null</code>
     * @return <code>object</code> if it is not <code>null</code>, defaultValue otherwise
     */
    public static Object nullValue(String object, String defaultValue) {
        return defaultIfNull(object, defaultValue);
    }

}
