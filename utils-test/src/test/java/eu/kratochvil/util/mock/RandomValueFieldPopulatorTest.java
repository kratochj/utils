package eu.kratochvil.util.mock;

import junit.framework.TestCase;

public class RandomValueFieldPopulatorTest extends TestCase {

	public void testPopulate() throws Exception {
		RandomValueFieldPopulator populator = new RandomValueFieldPopulator();

		TestPojo testPojo = new TestPojo();
		populator.populate(testPojo);
		doAsserts(testPojo);
	}

	public void testPopulateFromClass() throws Exception {
		RandomValueFieldPopulator populator = new RandomValueFieldPopulator();

		TestPojo testPojo = populator.populate(TestPojo.class);
		doAsserts(testPojo);
	}

	private void doAsserts(TestPojo testPojo) {
		assertNotNull(testPojo.getDatumPlatnosti());
		assertNotNull(testPojo.getOpDruh());
		assertNotNull(testPojo.getIdFoto());
		assertNotNull(testPojo.getOpJmeno());
	}
}
