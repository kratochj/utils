package eu.kratochvil.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.util.Map;

/**
 * @author Jiri Kratochvil (jiri.kratochvil@jetminds.com)
 * @version $Revision:$
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class CsvUtilsTest {


    @Test
    public void testRead() throws Exception {
        Map<String, String> data = CsvUtils.read("/RosCtiIcoData.csv", 5);
        System.out.println(data);
    }
}
