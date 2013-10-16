package eu.kratochvil.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * TODO Add JavaDoc
 *
 * @author Jiri Kratochvil <jiri.kratochvil@topmonks.com>
 */
public class EntityComparator {

	public static final Logger logger = LoggerFactory.getLogger(EntityComparator.class);

	Object entityA;

	Object entityB;

	List<String> fields;

	List<String> availableieFldsList = new ArrayList<String>();

	boolean isEquals = true;
	StringBuilder _sbA = new StringBuilder();
	StringBuilder _sbB = new StringBuilder();


	public EntityComparator(Object entityA, Object entityB) {
		this(entityA, entityB, null);
	}

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
		} catch (IllegalAccessException e) {
			logger.error("Nemohu nacist strukturu objektu - {}", e, e.getMessage());
			throw new EntityComparatorException(e.getMessage(), e);
		} catch (InvocationTargetException e) {
			logger.error("Nemohu nacist strukturu objektu - {}", e, e.getMessage());
			throw new EntityComparatorException(e.getMessage(), e);
		} catch (NoSuchMethodException e) {
			logger.error("Nemohu nacist strukturu objektu - {}", e, e.getMessage());
			throw new EntityComparatorException(e.getMessage(), e);
		}
	}

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

	public boolean isNotEquals() {
		return !isEquals();
	}

	public boolean isEquals() {
		logger.debug("Porovnavam entity {} a {}", entityA.getClass().getSimpleName(), entityB.getClass()
		                                                                                     .getSimpleName());
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
				_sbA.append("\"").append(fieldA).append("\"");

				Object fieldB = BeanUtils.getProperty(entityB, field);
				_sbB.append("\"").append(fieldB).append("\"");

				if (fieldA.equals(fieldB)) {
					logger.trace("    Hodnoty se shoduji: \"{}\"", fieldA);
				} else {
					logger.trace("    Hodnoty se neshoduji: \"{}\"|\"{}\"", fieldA, fieldB);
					isEquals = false;
				}
			}
			appendBoth("]");
		} catch (IllegalAccessException e) {
			logger.error("Chyba pri porovnavani entit - {}", e, e.getMessage());
			throw new EntityComparatorException(e.getMessage(), e);
		} catch (InvocationTargetException e) {
			logger.error("Chyba pri porovnavani entit - {}", e, e.getMessage());
			throw new EntityComparatorException(e.getMessage(), e);
		} catch (NoSuchMethodException e) {
			logger.error("Chyba pri porovnavani entit - {}", e, e.getMessage());
			throw new EntityComparatorException(e.getMessage(), e);
		}
		if (isEquals) {
			logger.debug("Entity se shoduji");
			return true;
		} else {
			logger.debug("Entity se neshoduji");
			logger.debug("  EntityA: {}", _sbA.toString());
			logger.debug("  EntityB: {}", _sbB.toString());
			return false;
		}

	}

	private void appendBoth(String value) {
		_sbA.append(value);
		_sbB.append(value);
	}
}