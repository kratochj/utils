package eu.kratochvil.util;

import org.junit.Assert;
import org.junit.ComparisonFailure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Extended basic set of junit {@link Assert asserts}:
 * <ul>
 * <li><strong>assertEqualsWithIgnore</strong> - Expaded functionality of {@link Assert#assertEquals(Object, Object)}
 * with support for placeholder as some kind of wildcard</li>
 * <li><strong>assertEqualsWithIgnoreWithoutSpacesAndNL</strong> - Similar to {@link #assertEqualsWithIgnore(String, String)}
 * but ignores spaces and new lines</li>
 * </ul>
 *
 * @author Jiri Kratochvil (jiri.kratochvil@jetminds.com)
 * @since 1.0
 */
public class ExtendedAsserts {

    public static final Logger logger = LoggerFactory.getLogger(ExtendedAsserts.class);

    /**
     * Default placeholder that represents wildcard character
     */
    public static final String DEFAULT_IGNORE_PLACEHOLDER = "${IGNORE}";


    /**
     * Similar to {@link #assertEqualsWithIgnore(String, String)} but ignores spaces and new lines
     *
     * @param expected Expected string (could contain wildcard)
     * @param actual   String value to be checked
     * @throws ComparisonFailure When expected and actual strings are not equals
     */
    public static void assertEqualsWithIgnoreWithoutSpacesAndNL(String expected, String actual) {
        assertEqualsWithIgnore("", clearSpacesAndNLs(expected), clearSpacesAndNLs(actual));
    }

    /**
     * Similar to {@link #assertEqualsWithIgnore(String, String)} but ignores spaces and new lines
     *
     * @param message  Message shown when assertion fails
     * @param expected Expected string (could contain wildcard)
     * @param actual   String value to be checked
     * @throws ComparisonFailure When expected and actual strings are not equals
     */
    public static void assertEqualsWithIgnoreWithoutSpacesAndNL(String message, String expected, String actual) {
        assertEqualsWithIgnore(message, clearSpacesAndNLs(expected), clearSpacesAndNLs(actual));
    }

    /**
     * Similar to {@link #assertEqualsWithIgnore(String, String)} but ignores spaces and new lines
     *
     * @param message           Message shown when assertion fails
     * @param expected          Expected string (could contain wildcard)
     * @param actual            String value to be checked
     * @param ignorePlaceholder Custom defined wildcard string
     * @throws ComparisonFailure When expected and actual strings are not equals
     */
    public static void assertEqualsWithIgnoreWithoutSpacesAndNL(String message, String expected, String actual, String ignorePlaceholder) {
        assertEqualsWithIgnore(message, clearSpacesAndNLs(expected), clearSpacesAndNLs(actual), ignorePlaceholder);
    }

    /**
     * Expaded functionality of {@link Assert#assertEquals(Object, Object)} with support for placeholder as
     * some kind of wildcard
     *
     * @param message  Message shown when assertion fails
     * @param expected Expected string (could contain wildcard)
     * @param actual   String value to be checked
     * @throws ComparisonFailure When expected and actual strings are not equals
     */
    public static void assertEqualsWithIgnore(String message, String expected, String actual) {
        assertEqualsWithIgnore(message, expected, actual, DEFAULT_IGNORE_PLACEHOLDER);

    }

    /**
     * Expaded functionality of {@link Assert#assertEquals(Object, Object)} with support for placeholder as
     * some kind of wildcard
     *
     * @param expected Expected string (could contain wildcard)
     * @param actual   String value to be checked
     * @throws ComparisonFailure When expected and actual strings are not equals
     */
    public static void assertEqualsWithIgnore(String expected, String actual) {
        assertEqualsWithIgnore("", expected, actual, DEFAULT_IGNORE_PLACEHOLDER);
    }


    /**
     * Expaded functionality of {@link Assert#assertEquals(Object, Object)} with support for placeholder as
     * some kind of wildcard
     *
     * @param message           Message shown when assertion fails
     * @param expected          Expected string (could contain wildcard)
     * @param actual            String value to be checked
     * @param ignorePlaceholder Custom defined wildcard string
     * @throws ComparisonFailure When expected and actual strings are not equals
     */
    public static void assertEqualsWithIgnore(String message, String expected, String actual, String ignorePlaceholder) {
        try {
            Assert.assertEquals(message, expected, actual);
            return;
        } catch (ComparisonFailure e) {
            logger.trace("Common assertEquals returns comparsion failure");
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


}
