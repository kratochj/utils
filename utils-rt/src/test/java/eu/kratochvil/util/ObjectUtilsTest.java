package eu.kratochvil.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Jiri Kratochvil (jiri.kratochvil@jetminds.com)
 * @version $Revision:$
 */
public class ObjectUtilsTest {

    @Test
    public void testNullValue() throws Exception {
        assertEquals(null, ObjectUtils.defaultIfNull(null, null));
        assertEquals("", ObjectUtils.defaultIfNull(null, ""));
        assertEquals("zz", ObjectUtils.defaultIfNull(null, "zz"));
        assertEquals("abc", ObjectUtils.defaultIfNull("abc", "def"));
        assertEquals(Boolean.TRUE, ObjectUtils.defaultIfNull(Boolean.TRUE, ""));
    }

}
