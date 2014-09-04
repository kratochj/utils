package eu.kratochvil.util.mock;

import java.lang.reflect.Field;

/**
 * @author Jiri Kratochvil <jiri.kratochvil@topmonks.com>
 */
public interface CantGenerateValueHandler {
	Object handle(Class<?> fieldType);

	void setField(Field field);
}
