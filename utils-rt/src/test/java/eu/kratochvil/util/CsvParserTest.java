package eu.kratochvil.util;

import org.apache.commons.lang.StringEscapeUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

/**
 * @author Jiri Kratochvil <jiri.kratochvil@topmonks.com>
 */
public class CsvParserTest {

    public static final Logger logger = LoggerFactory.getLogger(CsvParserTest.class);

    @Test
    public void testCsvParser() throws Exception {
        String item1 = "Test s , (carkou)";
        String item2 = "Test s \" (uvozovkou)";
        String item3 = "Test bez niceho";

        String csv =
                StringEscapeUtils.escapeCsv(item1) + "," +
                        StringEscapeUtils.escapeCsv(item2) + "," +
                        StringEscapeUtils.escapeCsv(item3);

        logger.debug("CSV: {}", csv);


        CsvParser csvParser = new CsvParser();

        String[] result = csvParser.parseLine(csv);


        assertEquals(item1, result[0]);
        assertEquals(item2, result[1]);
        assertEquals(item3, result[2]);
    }

    @Test
    public void testName() throws Exception {
        logger.debug("'{}'", byte[].class);

    }
}
