package eu.kratochvil.util;

import junit.framework.AssertionFailedError;
import org.junit.Assert;
import org.junit.ComparisonFailure;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Jiri Kratochvil (jiri.kratochvil@jetminds.com)
 * @version $Revision:$
 */
public class ExtendedAsserts {
    public static final Logger logger = Logger.getLogger(ExtendedAsserts.class.getName());

    public static final String DEFAULT_IGNORE_PLACEHOLDER = "${IGNORE}";

    public static void fail() {
        fail(null);
    }

    public static void fail(String message) {
        throw new AssertionFailedError(message);
    }

    protected static String clearSpacesAndNLs(String value) {
        StringBuilder _sb = new StringBuilder();
        for (char ch : value.toCharArray()) {
            if ((ch == '\n') || (ch == ' ')) {
                continue;
            }
            _sb.append(ch);
        }
        return _sb.toString();
    }

    public static void assertEqualsWithIgnoreWithoutSpacesAndNL(String expected, String actual) {
        assertEqualsWithIgnore("", clearSpacesAndNLs(expected), clearSpacesAndNLs(actual));
    }
    public static void assertEqualsWithIgnoreWithoutSpacesAndNL(String message, String expected, String actual) {
        assertEqualsWithIgnore(message, clearSpacesAndNLs(expected), clearSpacesAndNLs(actual));
    }        
    public static void assertEqualsWithIgnoreWithoutSpacesAndNL(String message, String expected, String actual, String ignorePlaceholder) {
        assertEqualsWithIgnore(message, clearSpacesAndNLs(expected), clearSpacesAndNLs(actual), ignorePlaceholder);
    }

    public static void assertEqualsWithIgnore(String message, String expected, String actual) {
        assertEqualsWithIgnore(message, expected, actual, DEFAULT_IGNORE_PLACEHOLDER);

    }

    public static void assertEqualsWithIgnore(String expected, String actual) {
        assertEqualsWithIgnore("", expected, actual, DEFAULT_IGNORE_PLACEHOLDER);
    }


    public static void assertEqualsWithIgnore(String message, String expected, String actual, String ignorePlaceholder) {
        try {
            Assert.assertEquals(message, expected, actual);
            return;
        } catch (ComparisonFailure e) {
            logger.log(Level.FINE, "Common assertEquals returns comparsion failure", e);
        }

        try {
            int indexInExpected = 0;
            int indexInActual = 0;
            if (expected.indexOf(ignorePlaceholder) < 0) {
                throw new ComparisonFailure(message, expected, actual);
            }

            while (expected.indexOf(ignorePlaceholder, indexInExpected) > -1) {
                String pattern = expected.substring(indexInExpected, expected.indexOf(ignorePlaceholder, indexInExpected));
                if (!pattern.equals(actual.substring(indexInActual, indexInActual + pattern.length()))) {
                    throw new ComparisonFailure(message, expected, actual);
                }
                indexInExpected = expected.indexOf(ignorePlaceholder, indexInExpected) + ignorePlaceholder.length();
                int nextPartEnd = expected.indexOf(ignorePlaceholder, indexInExpected) < 0 ? expected.length() : expected.indexOf(ignorePlaceholder, indexInExpected);
                String nextPart = expected.substring(indexInExpected, nextPartEnd);

                indexInActual = actual.indexOf(nextPart, indexInActual);
            }

            String xExpected = expected.substring(indexInExpected);
            String xActual = actual.substring(indexInActual);
            if (!(xExpected.equals(xActual)) && !("".equals(xExpected))) {
                throw new ComparisonFailure(message, expected, actual);
            }
        } catch (StringIndexOutOfBoundsException e) {
            throw new ComparisonFailure(message, expected, actual);
        }


    }

}
