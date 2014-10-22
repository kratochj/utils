package eu.kratochvil.util.beans;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.PropertyUtils;

/**
 * Utility třída pro práci (copy, get) s property objektu
 *
 * @author Milan Veselý <milan.vesely@i.cz>
 * @author Jiri Kratochvil
 */
public class SmartBeanUtils {

	/**
	 * Metoda pro kopírování hodnoty property ze zdrojového objektu na cílový.
	 * <p/>
	 * Prováděné konverze - pokud je property na zdrojovém objektu {@link java.util.Date} a na cílovém
	 * {@link java.sql.Date} pomocí {@link DateUtils#convertNullSafe} - pokud je property na zdrojovém objektu
	 * {@link java.lang.Boolean} a na cílovém {@link java.lang.String} pomocí {@link DatabaseBoolean#asString}
	 *
	 * @param targetBean
	 *            cílový objekt
	 * @param sourceBean
	 *            zdrojový objekt
	 * @param propertyName
	 *            jméno kopírované property
	 * @throws {@link eu.kratochvil.util.beans.BeanUtilsException} pokud se nepovede konverze nebo překopírování
	 */
	public static void copyProperty(Object targetBean, Object sourceBean, String propertyName) {
		copyProperty(targetBean, sourceBean, propertyName, null);
	}

	/**
	 * Metoda pro kopírování hodnoty property ze zdrojového objektu na cílový, viz copyProperty(,,). Přidává možnost
	 * použití callback implementace, která bude zavolána před a po nastavení hodnoty property na cílový objekt.
	 *
	 * @param targetBean
	 * @param sourceBean
	 * @param propertyName
	 * @param callback
	 *            implementace callback objektu, který bude volán během kopírování nové hodnoty
	 */
	public static void copyProperty(Object targetBean, Object sourceBean, String propertyName,
			PropertyCopyCallback callback) {
		try {
			PropertyDescriptor targetDescriptor = PropertyUtils.getPropertyDescriptor(targetBean, propertyName);
			PropertyDescriptor sourceDescriptor = PropertyUtils.getPropertyDescriptor(sourceBean, propertyName);

			Object sourceValue = PropertyUtils.getProperty(sourceBean, propertyName);
			Object targetValue = PropertyUtils.getProperty(targetBean, propertyName);

			if (callback != null) {
				callback.beforePropertySet(targetValue, sourceValue, propertyName);
			}

			// Set Util Date to SQL Date
			if (targetDescriptor.getPropertyType().equals(java.sql.Date.class)
					&& sourceDescriptor.getPropertyType().equals(java.util.Date.class)) {

				targetDescriptor.getWriteMethod().invoke(targetBean,
						DateUtils.convertNullSafe((java.util.Date) sourceValue));
				return;
			}
			// Set Boolean to Informix boolean (char1)
			if (targetDescriptor.getPropertyType().equals(String.class)
					&& sourceDescriptor.getPropertyType().equals(Boolean.class)) {

				targetDescriptor.getWriteMethod().invoke(targetBean, DatabaseBoolean.asString((Boolean) sourceValue));
				return;
			}

			targetDescriptor.getWriteMethod().invoke(targetBean, PropertyUtils.getProperty(sourceBean, propertyName));

			if (callback != null) {
				callback.afterPropertySet(PropertyUtils.getProperty(targetBean, propertyName), sourceValue,
						propertyName);
			}
		} catch (IllegalAccessException e) {
			createBeanUtilsExceptionCopyProperty(targetBean, sourceBean, propertyName, e);
		} catch (InvocationTargetException e) {
			createBeanUtilsExceptionCopyProperty(targetBean, sourceBean, propertyName, e);
		} catch (NoSuchMethodException e) {
			createBeanUtilsExceptionCopyProperty(targetBean, sourceBean, propertyName, e);
		} catch (ConversionException e) {
			createBEanUtilsExceptionConvertProperty(targetBean, sourceBean, propertyName, e);
		}
	}

	/**
	 * Získá hodnotu property na objektu podle jejího jména
	 *
	 * @param bean
	 *            objekt
	 * @param propertyName
	 *            jméno property
	 * @return hodnotu property
	 * @throws {@link eu.kratochvil.util.beans.BeanUtilsException} pokud property není na objektu dostupná
	 */
	public static Object getProperty(Object bean, String propertyName) {
		try {
			return PropertyUtils.getProperty(bean, propertyName);
		} catch (IllegalAccessException e) {
			throw new BeanUtilsException(e, "Nepodařilo se získat hodnotu property {} z objektu {}.", propertyName,
					bean);
		} catch (InvocationTargetException e) {
			throw new BeanUtilsException(e, "Nepodařilo se získat hodnotu property {} z objektu {}.", propertyName,
					bean);
		} catch (NoSuchMethodException e) {
			throw new BeanUtilsException(e, "Nepodařilo se získat hodnotu property {} z objektu {}.", propertyName,
					bean);
		}
	}

	/**
	 * Získá hodnotu property na objektu podle jejího jména.
	 *
	 * @param bean
	 *            objekt
	 * @param propertyName
	 *            jméno property
	 * @return hodnotu property, pokud není na objektu dostupná vrací null
	 */
	public static Object getPropertyIfExists(Object bean, String propertyName) {
		try {
			return PropertyUtils.getProperty(bean, propertyName);
		} catch (IllegalAccessException e) {
			return null;
		} catch (InvocationTargetException e) {
			return null;
		} catch (NoSuchMethodException e) {
			return null;
		}
	}

	/**
	 * Zjistí jestli property s názvem na objektu existuje
	 *
	 * @param bean
	 *            objekt
	 * @param propertyName
	 *            jméno property
	 * @return true pokud property exituje, jinak false
	 */
	public static boolean hasProperty(Object bean, String propertyName) {
		try {
			return org.apache.commons.beanutils.BeanUtils.describe(bean).keySet().contains(propertyName);
		} catch (IllegalAccessException e) {
			return false;
		} catch (InvocationTargetException e) {
			return false;
		} catch (NoSuchMethodException e) {
			return false;
		}
	}

	private static void createBEanUtilsExceptionConvertProperty(Object targetBean, Object sourceBean,
			String propertyName, ConversionException e) {
		throw new BeanUtilsException(e,
				"Nepodařilo se zkonvertovat property {} při kopírování ze source {} na target {}.", propertyName,
				sourceBean.getClass().getSimpleName(), targetBean.getClass().getSimpleName());
	}

	private static void createBeanUtilsExceptionCopyProperty(Object targetBean, Object sourceBean, String propertyName,
			Throwable e) {
		throw new BeanUtilsException(e,
				"Nepodařilo se překopírovat property {} při kopírování ze source {} na target {}.", propertyName,
				sourceBean.getClass().getSimpleName(), targetBean.getClass().getSimpleName());
	}

}
