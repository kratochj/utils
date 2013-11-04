package eu.kratochvil.util;

import org.json.simple.JSONObject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

/**
 * @author Jiri Kratochvil <jiri.kratochvil@topmonks.com>
 */
public class JsonFormatterTest {

	public static final Logger logger = LoggerFactory.getLogger(JsonFormatterTest.class);

	final String formattedJson = "{\n" +
			"    test :\n" +
			"    \"value1\"\n" +
			"    ,\n" +
			"    testl2 :\n" +
			"    {\n" +
			"        test1 :\n" +
			"        \"value11\"\n" +
			"    }\n" +
			"}\n";

	@SuppressWarnings("unchecked")
	@Test
	public void testFormatter() throws Exception {

		final JSONObject obj = new JSONObject();
		obj.put("test", "value1");

		JSONObject objL2 = new JSONObject();
		obj.put("testl2", objL2);
		objL2.put("test1", "value11");

		String result = JsonFormatter.format(obj);
		logger.debug(result);
		assertEquals(formattedJson, result);
	}
}
