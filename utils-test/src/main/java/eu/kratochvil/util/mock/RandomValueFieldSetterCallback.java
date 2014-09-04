package eu.kratochvil.util.mock;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import org.springframework.util.ReflectionUtils;

/**
 * @author Jiri Kratochvil <jiri.kratochvil@topmonks.com>
 */
public class RandomValueFieldSetterCallback implements ReflectionUtils.FieldCallback {
	private static final int DATE_WINDOW_MILLIS = 1000;

	private Object targetObject;
	private Random random = new Random();
	private CantGenerateValueHandler cantGenerateValueHandler = new WarnOnCantGenerateValueHandler();

	public RandomValueFieldSetterCallback(Object targetObject) {
		this.targetObject = targetObject;
	}

	public RandomValueFieldSetterCallback(Object targetObject, CantGenerateValueHandler cantGenerateValueHandler) {
		this(targetObject);
		this.cantGenerateValueHandler = cantGenerateValueHandler;
	}

	@Override
	public void doWith(Field field) throws IllegalAccessException {
		Class<?> fieldType = field.getType();
		if (!Modifier.isFinal(field.getModifiers())) {
			cantGenerateValueHandler.setField(field);
			Object value = generateRandomValue(fieldType, cantGenerateValueHandler);
			if (!value.equals(RandomValueFieldPopulator.UNGENERATED_VALUE_MARKER)) {
				ReflectionUtils.makeAccessible(field);
				field.set(targetObject, value);
			}
		}
	}

	public Object generateRandomValue(Class<?> fieldType, CantGenerateValueHandler cantGenerateValueHandler) {
		if (fieldType.equals(String.class)) {
			return UUID.randomUUID().toString();
		} else if (Date.class.isAssignableFrom(fieldType)) {
			return new Date(System.currentTimeMillis() - random.nextInt(DATE_WINDOW_MILLIS));
		} else if (fieldType.equals(Short.class)) {
			return (short) random.nextInt(Short.MAX_VALUE + 1);
		} else if (Number.class.isAssignableFrom(fieldType)) {
			return random.nextInt(Byte.MAX_VALUE) + 1;
		} else if (fieldType.equals(Integer.TYPE)) {
			return random.nextInt();
		} else if (fieldType.equals(Long.TYPE)) {
			return random.nextInt();
		} else if (Enum.class.isAssignableFrom(fieldType)) {
			Object[] enumValues = fieldType.getEnumConstants();
			return enumValues[random.nextInt(enumValues.length)];
		} else {
			return cantGenerateValueHandler.handle(fieldType);
		}
	}

}
