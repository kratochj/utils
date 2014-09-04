package eu.kratochvil.util.mock;

import org.springframework.util.ReflectionUtils;

/**
 * @author Jiri Kratochvil <jiri.kratochvil@topmonks.com>
 */
public class RandomValueFieldPopulator {

	public static final String UNGENERATED_VALUE_MARKER = "N/A";

	public void populate(Object object) {
		ReflectionUtils.doWithFields(object.getClass(), new RandomValueFieldSetterCallback(object));
	}

    public <T> T populate(Class<T> clazz) throws InstantiationException, IllegalAccessException {
        T instance = createInstanceWithDefaultConstructor(clazz);
        populate(instance);
        return instance;
    }

    private <T> T createInstanceWithDefaultConstructor(Class<T> clazz) throws IllegalAccessException, InstantiationException {
        return clazz.newInstance();
    }

}
