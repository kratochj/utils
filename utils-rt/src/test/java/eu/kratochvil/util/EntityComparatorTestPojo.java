package eu.kratochvil.util;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Jiri Kratochvil <jiri.kratochvil@topmonks.com>
 */
public class EntityComparatorTestPojo {


	private String name;

	private int age;

	private Date dateOfBirth;

	public EntityComparatorTestPojo(String name, int age, Date dateOfBirth) {
		this.name = name;
		this.age = age;
		this.dateOfBirth = dateOfBirth;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	@Override
	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("name", name).append("age", age).append("dateOfBirth", dateOfBirth);
		return builder.toString();
	}
}
