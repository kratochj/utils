package eu.kratochvil.util.mock;

import java.lang.reflect.Field;

/**
 * @author Jiri Kratochvil <jiri.kratochvil@topmonks.com>
 */
public class WarnOnCantGenerateValueHandler implements CantGenerateValueHandler {
	Field field;

	public void setField(Field field) {
		this.field = field;
	}

	public Object handle(Class<?> fieldType) {
		return RandomValueFieldPopulator.UNGENERATED_VALUE_MARKER;
	}
}
