package eu.kratochvil.util;

import org.joda.time.DateTime;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Jiri Kratochvil <jiri.kratochvil@topmonks.com>
 */
public class FreezeTimeTest {

	@Test
	public void shouldFreezeTime() {
		FreezeTime.timeAt("2008-09-04T12:32:12").thawAfter(new Snippet() {{
			assertEquals(new DateTime(2008, 9, 4, 12, 32, 12, 0), DateTime.now());
		}});
	}
}
