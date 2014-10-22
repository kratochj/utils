package eu.kratochvil.util.beans;

import org.apache.commons.lang.StringUtils;

/**
 * Converts boolean value from/to string representation.Like <code>true</code> is <code>Y</code> and <code>false</code>
 * is <code>N</code>
 * 
 * @author Milan Veselý <milan.vesely@i.cz>
 * @author Jiri Kratochvil <jiri@kratochvil.eu>
 */
public class DatabaseBoolean {

	private static final String DB_TRUE = "Y";
	private static final String DB_FALSE = "N";

	public static String asString(Boolean value) {
		if (value == null) {
			return null;
		}
		return value ? DB_TRUE : DB_FALSE;
	}

	public static Boolean asBoolean(String value) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		return StringUtils.equals(value, DB_TRUE) ? Boolean.TRUE : Boolean.FALSE;
	}

}
