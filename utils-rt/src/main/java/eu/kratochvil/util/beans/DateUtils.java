package eu.kratochvil.util.beans;

import org.joda.time.DateTime;

/**
 * @author Milan Veselý <milan.vesely@i.cz>
 */
public class DateUtils {

	public static java.sql.Date convert(java.util.Date value) {
		return new java.sql.Date(value.getTime());
	}

	public static java.sql.Date convertNullSafe(java.util.Date value) {
		if (value == null) {
			return null;
		}
		return convert(value);
	}

	public static java.sql.Date convert(DateTime dateTime){
		return convert(dateTime.toDate());
	}

	public static boolean isDate(Object mayBeDate) {
		if (mayBeDate == null) {
			return false;
		}
		return mayBeDate instanceof java.util.Date;
	}

	public static boolean isSqlDate(Object mayBeSqlDate) {
		if (mayBeSqlDate == null) {
			return false;
		}
		return mayBeSqlDate instanceof java.sql.Date;
	}

}
