package eu.kratochvil.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Working with DateFormat in a multithreading environment can be tricky. The Java API documentation clearly states :
 * <p/>
 * <em>“Date formats are not synchronized. It is recommended to create separate format instances for each thread.
 * If multiple threads access a format concurrently, it must be synchronized externally.”</em>
 * <p/>
 * A typical case scenario is to convert a Date to its String representation or vice versa, using a predefined
 * format. Creating new DateFormat instances for every conversion is very inefficient. You should keep in mind
 * that the static factory methods “getDateInstance(..)” also create new DateFormat instances when used. What
 * most developers do is that they construct a DateFormat instance, using a DateFormat implementation class
 * (e.g. SimpleDateFormat), and assign its value to a class variable. The class scoped variable is used for all
 * their Date parsing and formatting needs. The aforementioned approach, although very efficient, can cause
 * problems when multiple threads access the same instance of the class variable, due to lack of synchronization
 * on the DateFormat class
 * <p/>
 * <strong>Things to notice here:</strong>
 * Every individual Thread executing the “convertStringToDate” operation, invokes the “df.get()”
 * operation in order to initialize or retrieve an already initialized reference of its local
 * scoped DateFormat instance
 * <p/>
 * More information here:
 * <a href="http://www.javacodegeeks.com/2010/07/java-best-practices-dateformat-in.html">DateFormat in a Multithreading Environment</a>
 * and
 * <a href="http://stackoverflow.com/questions/6840803/simpledateformat-thread-safety">SimpleDateFormat thread safety</a>
 *
 * @author Jiri Kratochvil <jiri.kratochvil@topmonks.com>
 */
public class ConcurrentDateFormatAccess {

	private ThreadLocal<DateFormat> df = new ThreadLocal<DateFormat>() {

		public static final String DEFAULT_DATE_FORMAT_PATTERN = "yyyy.MM.dd";

		String dateFormatPattern = DEFAULT_DATE_FORMAT_PATTERN;

		@Override
		public DateFormat get() {
			return super.get();
		}

		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat(dateFormatPattern);
		}

		@Override
		public void remove() {
			super.remove();
		}

		@Override
		public void set(DateFormat value) {
			super.set(value);
		}

	};


	public Date convertStringToDate(String dateString) throws ParseException {
		return df.get().parse(dateString);
	}

	public String convertDateToString(Date date) {
		return df.get().format(date);
	}

}