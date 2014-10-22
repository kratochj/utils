package eu.kratochvil.util;

import java.util.Date;
import java.util.UUID;

/**
 * @author Jiri Kratochvil <jiri.kratochvil@topmonks.com>
 */
public abstract class AbstractException extends RuntimeException {

	String uuid;

	Date timestamp;

	/**
	 * @param message
	 * @param args
	 */
	public AbstractException(String message, Object... args) {
		this((Throwable) null, message, args);
	}

	/**
	 * @param cause
	 * @param message
	 * @param args
	 */
	public AbstractException(Throwable cause, String message, Object... args) {
		super((null == args || args.length == 0) ? message : StringUtils.withArgs(message, args), cause);
		uuid = UUID.randomUUID().toString();
		timestamp = new Date();
	}

	public String getUuid() {
		return uuid;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	private static final long serialVersionUID = 1L;

}
