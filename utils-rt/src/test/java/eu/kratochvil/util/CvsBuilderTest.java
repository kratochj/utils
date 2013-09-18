package eu.kratochvil.util;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

/**
 * @author Jiri Kratochvil <jiri.kratochvil@topmonks.com>
 */
public class CvsBuilderTest {

    public static final Logger logger = LoggerFactory.getLogger(CvsBuilderTest.class);

    final String resultRow = "Radek jedna,\"Radek dva s carkou (,)\"," +
            "\"Radek tri s Uvozovkou(\"\")\",Radek ctyri,Radek pet,34.45,100234,23456787654323456," +
            "2012/12/26 22:43:23";

    final String[] testValues = new String[]{
            "Radek jedna",
            "Radek dva s carkou (,)",
            "Radek tri s Uvozovkou(\")",
            "Radek ctyri",
            "Radek pet",
            "34.45",
            "100234",
            "23456787654323456",
            "2012/12/26 22:43:23"
    };


    @Test
    public void testTestBuildRowFromStringArray() throws Exception {
        CvsBuilder builder = new CvsBuilder();
        String row = builder.buildLine(testValues).toString();
        logger.debug("Generated CSV: {}", row);
        assertEquals(resultRow, row);
    }

    @Test
    public void testWithAppend() throws Exception {
        CvsBuilder builder = new CvsBuilder();
        String row = builder
                .append(testValues[0])
                .append(testValues[1])
                .append(testValues[2])
                .append(testValues[3])
                .append(testValues[4])
                .append(Double.parseDouble(testValues[5]))
                .append(Integer.parseInt(testValues[6]))
                .append(Long.parseLong(testValues[7]))
                .append(CvsBuilder.DEFAULT_DATE_TIME_FORMATTER.parse(testValues[8]))
                .toString();
        logger.debug("Generated CSV: {}", row);
        assertEquals(resultRow, row);
    }
}
