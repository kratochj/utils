package eu.kratochvil.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

/**
 * @author Jiri Kratochvil (jiri.kratochvil@jetminds.com)
 * @version $Revision:$
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class ExtendedAssertsTest {

    @Test
    public void testAssertEqualsWithIgnore() throws Exception {
        ExtendedAsserts.assertEqualsWithIgnore("Test", "AAA${IGNORE}BBB", "AAAXXXBBB", "${IGNORE}");
        ExtendedAsserts.assertEqualsWithIgnore("Test", "AAA${IGNORE}BBB", "AAAXXXBBB", "${IGNORE}");
        ExtendedAsserts.assertEqualsWithIgnore("Test", "AAA${IGNORE}BBB${IGNORE}", "AAAXXXBBBB", "${IGNORE}");
        ExtendedAsserts.assertEqualsWithIgnore("Test", "AAA${IGNORE}BBB${IGNORE}CCC", "AAAXXXBBBBCCC", "${IGNORE}");
    }

    @Test
    public void testClearSpacesAndNLs() throws Exception {
        Assert.assertEquals("ABCD", ExtendedAsserts.clearSpacesAndNLs("A B C \n D"));
    }

    @Test
    public void testAssertEqualsWithIgnoreWithoutSpacesAndNL() throws Exception {
        ExtendedAsserts.assertEqualsWithIgnoreWithoutSpacesAndNL("ABCD", "A B C \n D");


    }
}
