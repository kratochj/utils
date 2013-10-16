package eu.kratochvil.util;

import org.joda.time.DateTime;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Jiri Kratochvil <jiri.kratochvil@topmonks.com>
 */
public class EntityComparatorTest {


	@Test
	public void testEntitiesAreEquals() throws Exception {
		EntityComparatorTestPojo entityA =
				new EntityComparatorTestPojo("Karel", 1+30, DateTime.parse("1982-1-3").toDate());
		EntityComparatorTestPojo entityB =
				new EntityComparatorTestPojo("Karel", 1+30, DateTime.parse("1982-1-3").toDate());

		assertTrue(new EntityComparator(entityA, entityB).add("name").add("age").add("dateOfBirth").isEquals());
		assertFalse(new EntityComparator(entityA, entityB).add("name").add("age").add("dateOfBirth").isNotEquals());
	}

	@Test
	public void testEntitiesAreNotEquals() throws Exception {
		EntityComparatorTestPojo entityA =
				new EntityComparatorTestPojo("Pavel", 1+30, DateTime.parse("1982-1-3").toDate());
		EntityComparatorTestPojo entityB =
				new EntityComparatorTestPojo("Karel", 1+30, DateTime.parse("1982-1-3").toDate());

		assertFalse(new EntityComparator(entityA, entityB).add("name").add("age").add("dateOfBirth").isEquals());
		assertTrue(new EntityComparator(entityA, entityB).add("name").add("age").add("dateOfBirth").isNotEquals());
	}

	@Test
	public void testEntitiesAreEqualsOnSelectedFields() throws Exception {
		EntityComparatorTestPojo entityA =
				new EntityComparatorTestPojo("Pavel", 1+30, DateTime.parse("1982-1-3").toDate());
		EntityComparatorTestPojo entityB =
				new EntityComparatorTestPojo("Karel", 1+30, DateTime.parse("1982-1-3").toDate());

		assertTrue(new EntityComparator(entityA, entityB).add("age").add("dateOfBirth").isEquals());
		assertFalse(new EntityComparator(entityA, entityB).add("age").add("dateOfBirth").isNotEquals());
	}

	@Test(expected = EntityComparatorException.class)
	public void testEntitiesCheckNonExistendField() throws Exception {
		EntityComparatorTestPojo entityA =
				new EntityComparatorTestPojo("Pavel", 1+30, DateTime.parse("1982-1-3").toDate());
		EntityComparatorTestPojo entityB =
				new EntityComparatorTestPojo("Karel", 1+30, DateTime.parse("1982-1-3").toDate());

		new EntityComparator(entityA, entityB).add("age").add("XXXXXXX").add("dateOfBirth").isEquals();
	}
}
