package eu.kratochvil.util.beans;

import eu.kratochvil.util.AbstractException;

/**
 * @author Jiri Kratochvil <jiri.kratochvil@topmonks.com>
 */
public class BeanUtilsException extends AbstractException {

	/**
	 * The message may contain parameters which are then expanded using the {@link java.text.MessageFormat} rules.
	 *
	 * @param cause
	 *            Cause exception
	 * @param message
	 *            Log message
	 * @param args
	 *            Arguments to expand into the message
	 */
	public BeanUtilsException(Throwable cause, String message, Object... args) {
		super(cause, message, args);
	}

	/**
	 * The message may contain parameters which are then expanded using the {@link java.text.MessageFormat} rules.
	 *
	 * @param message
	 *            Log message
	 * @param args
	 *            Arguments to expand into the message
	 */
	public BeanUtilsException(String message, Object... args) {
		super(message, args);
	}

	private static final long serialVersionUID = 1L;

}

