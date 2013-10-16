package eu.kratochvil.util;

/**
 * @author Jiri Kratochvil <jiri.kratochvil@topmonks.com>
 */
public class EntityComparatorException extends RuntimeException {
	public EntityComparatorException(String s) {
		super(s);
	}

	public EntityComparatorException(String s, Throwable throwable) {
		super(s, throwable);
	}
}
