package eu.kratochvil.util.beans;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;

import java.sql.Date;

import org.joda.time.DateTime;
import org.junit.Test;
import org.mockito.Mockito;

public class SmartBeanUtilsTest {

	private final Class1 class1 = new Class1("text", DateUtils.convert(DateTime.now()), true);

	@Test
	public void testCopyProperty() throws Exception {

		// given
		Class2 class2 = new Class2();

		// when
		SmartBeanUtils.copyProperty(class2, class1, "string");
		SmartBeanUtils.copyProperty(class2, class1, "date");
		SmartBeanUtils.copyProperty(class2, class1, "aBoolean");

		// assert
		assertEquals(class1.getString(), class2.getString());
		assertEquals(class1.getDate(), class2.getDate());
		assertEquals("Y", class2.getaBoolean());
	}

	@Test
	public void testCopyPropertyWithCallback() throws Exception {

		// given
		Class2 class2 = new Class2();

		PropertyCopyCallback callback = Mockito.mock(PropertyCopyCallback.class);

		// when
		SmartBeanUtils.copyProperty(class2, class1, "string", callback);

		// assert
		assertEquals(class1.getString(), class2.getString());
		Mockito.verify(callback, times(1)).beforePropertySet(eq(null), eq("text"), eq("string"));
		Mockito.verify(callback, times(1)).afterPropertySet(eq("text"), eq("text"), eq("string"));
	}

	@Test
	public void testGetProperty() throws Exception {

		// when
		String string = (String) SmartBeanUtils.getProperty(class1, "string");

		// assert
		assertEquals("text", string);
	}

	@Test(expected = BeanUtilsException.class)
	public void testGetPropertyNonExisting() throws Exception {

		// when
		SmartBeanUtils.getProperty(class1, "stringNeexeistujici");

	}

	@Test
	public void testGetPropertyIfExists() throws Exception {

		// when
		Object string = SmartBeanUtils.getPropertyIfExists(class1, "stringNeexeistujici");

		// assert
		assertNull(string);
	}

	public static class Class1 {

		private String string;

		private Date date;

		private Boolean aBoolean;

		private Class1(String string, Date date, Boolean aBoolean) {
			this.string = string;
			this.date = date;
			this.aBoolean = aBoolean;
		}

		public String getString() {
			return string;
		}

		public void setString(String string) {
			this.string = string;
		}

		public Date getDate() {
			return date;
		}

		public void setDate(Date date) {
			this.date = date;
		}

		public Boolean getaBoolean() {
			return aBoolean;
		}

		public void setaBoolean(Boolean aBoolean) {
			this.aBoolean = aBoolean;
		}
	}

	public static class Class2 {

		private String string;

		private java.util.Date date;

		private String aBoolean;

		public String getString() {
			return string;
		}

		public void setString(String string) {
			this.string = string;
		}

		public java.util.Date getDate() {
			return date;
		}

		public void setDate(java.util.Date date) {
			this.date = date;
		}

		public String getaBoolean() {
			return aBoolean;
		}

		public void setaBoolean(String aBoolean) {
			this.aBoolean = aBoolean;
		}
	}

}
