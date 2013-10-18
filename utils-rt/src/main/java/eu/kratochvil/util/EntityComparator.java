package eu.kratochvil.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.logging.LogManager;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Compare two instances of entities. Developer could define which of fields
 * are compared, by adding this with method {@link #add(String)} with name of
 * field. That field must be accessible in both entities (public field or
 * public setter).
 * <p/>
 * When objects are not same, differences are printed out on debug logger.
 * <p/>
 * Example:
 * <code><pre>
 *  EntityComparator(entityA, entityB).add("field1").add("field2").add("field3").isEquals()
 * </pre></code>
 *
 * @author Jiri Kratochvil <jiri.kratochvil@topmonks.com>
 * @see EntityComparatorException
 */
public class EntityComparator {

	public static final Logger logger = LoggerFactory.getLogger(EntityComparator.class);

	public static final boolean DEFAULT_SKIP_NULLS = true;

	Object entityA;

	Object entityB;

	List<String> fields;

	List<String> availableieFldsList = new ArrayList<String>();

	StringBuilder _sbA = new StringBuilder();
	StringBuilder _sbB = new StringBuilder();


	/**
	 * Creates instance of comparator
	 *
	 * @param entityA Entity A to be compared
	 * @param entityB Entity B to be compared
	 */
	public EntityComparator(Object entityA, Object entityB) {
		this(entityA, entityB, null);
	}

	/**
	 * Creates instance of comparator and pass list of fields to be compared
	 *
	 * @param entityA Entity A to be compared
	 * @param entityB Entity B to be compared
	 */
	public EntityComparator(Object entityA, Object entityB, Collection<? extends String> fields) {
		this.entityA = entityA;
		this.entityB = entityB;
		if (fields != null) {
			this.fields = new ArrayList<String>(fields);
		} else {
			this.fields = new ArrayList<String>();
		}

		// Inspect fields on entities
		try {
			Set fieldsA = BeanUtils.describe(this.entityA).keySet();
			Set fieldsB = BeanUtils.describe(this.entityA).keySet();
			for (Object fieldA : fieldsA) {
				if (fieldsB.contains(fieldA)) {
					logger.trace("Field {} available for comparison", fieldA);
					availableieFldsList.add(fieldA.toString());
				}
			}
		} catch (IllegalAccessException e){
			logger.error("Nemohu nacist strukturu objektu - {}", e.getMessage(), e);
			throw new EntityComparatorException(e.getMessage(), e);
		} catch (InvocationTargetException e) {
			logger.error("Nemohu nacist strukturu objektu - {}", e.getMessage(), e);
			throw new EntityComparatorException(e.getMessage(), e);
		} catch (NoSuchMethodException e) {
			logger.error("Nemohu nacist strukturu objektu - {}", e.getMessage(), e);
			throw new EntityComparatorException(e.getMessage(), e);
		}
	}

	/**
	 * Add field to the list of compared fields
	 *
	 * @param field Name of field
	 * @return Instance of entity comparator
	 * @throws EntityComparatorException when field does not exist in both entities
	 */
	public EntityComparator add(String field) {
		if (availableieFldsList.contains(field)) {
			logger.trace("Adding {} to the comparator pattern", field);
			fields.add(field);
			return this;
		} else {
			logger.error("Field {} does not exist in both entities", field);
			throw new EntityComparatorException("Field " + field + " does not exists in both entities");
		}
	}

	public boolean isEquals() {
		return isEquals(DEFAULT_SKIP_NULLS);
	}

	public boolean isNotEquals() {
		return isNotEquals(DEFAULT_SKIP_NULLS);
	}

	/**
	 * Compares object and returs <code>true</code> when object differs
	 *
	 * @return <code>True</code> when object are <strong>not</strong> same.
	 */
	public boolean isNotEquals(boolean skipNulls) {
		return !isEquals(skipNulls);
	}

	/**
	 * Compare object and returs <code>true</code> when object differs. Null values are excluded from coparison.
	 *
	 * @return <code>True</code> when object <strong>are</strong> same.
	 */
	public boolean isEquals(boolean skipNulls) {
		logger.debug("Porovnavam entity {} a {}", entityA.getClass().getSimpleName(), entityB.getClass()
		                                                                                     .getSimpleName());
		boolean isEquals = true;

		try {
			for (String field : fields) {
				logger.trace("  Porovnavam atribut: {}", field);
				if (_sbA.length() == 0) {
					appendBoth("[");
				} else {
					appendBoth(", ");
				}
				appendBoth(field + "=");

				Object fieldA = BeanUtils.getProperty(entityA, field);
				Object fieldB = BeanUtils.getProperty(entityB, field);

				if (skipNulls && ((fieldA == null) || (fieldB == null))) {
					logger.trace("    Ignoruji pole: {}", field);
					appendBoth("<IGNORED>");
				} else {
					_sbA.append("\"").append(fieldA).append("\"");
					_sbB.append("\"").append(fieldB).append("\"");
					if (!compare(fieldA, fieldB)) {
						isEquals = false;
					}
				}
			}
			appendBoth("]");
		} catch (IllegalAccessException e){
			logger.error("Chyba pri porovnavani entit - {}", e.getMessage(), e);
			throw new EntityComparatorException(e.getMessage(), e);
		} catch (InvocationTargetException e) {
			logger.error("Chyba pri porovnavani entit - {}", e.getMessage(), e);
			throw new EntityComparatorException(e.getMessage(), e);
		} catch (NoSuchMethodException e) {
			logger.error("Chyba pri porovnavani entit - {}", e.getMessage(), e);
			throw new EntityComparatorException(e.getMessage(), e);
		}
		if (isEquals) {
			logger.debug("Entity se shoduji");
			logger.trace("  EntityA: {}", _sbA.toString());
			logger.trace("  EntityB: {}", _sbB.toString());
			return true;
		} else {
			logger.debug("Entity se neshoduji");
			logger.debug("  EntityA: {}", _sbA.toString());
			logger.debug("  EntityB: {}", _sbB.toString());
			return false;
		}

	}


	private boolean compare(Object fieldA, Object fieldB) {
		if (fieldA == fieldB) {
			return true;
		}

		if (fieldA == null) {
			return false;
		}

		if (fieldA.equals(fieldB)) {
			logger.trace("    Hodnoty se shoduji: \"{}\"", fieldA);
			return true;
		} else {
			logger.trace("    Hodnoty se neshoduji: \"{}\"|\"{}\"", fieldA, fieldB);
			return false;
		}
	}

	private void appendBoth(String value) {
		_sbA.append(value);
		_sbB.append(value);
	}

}