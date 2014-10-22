package eu.kratochvil.util.beans;

/**
 * Callback pro možnost doplnění funkčnosti {@link SmartBeanUtils} v okamžiku,
 * kdy je provedeny změna cílové property.
 *
 * @author Milan Veselý <milan.vesely@i.cz>
 */
public interface PropertyCopyCallback {


	/**
	 * Metoda bude zavolána před nastavením nové hodnoty
	 *
	 * @param targetValue   hodnota property na cílovém objektu před změnou
	 * @param sourceValue   hodnota property na zdrojovém objektu
	 * @param propertyName  název property pro kterou bude změna provedena
	 */
	public void beforePropertySet(Object targetValue, Object sourceValue, String propertyName);

	/**
	 * Metoda bude zavolána po nastavení nové hodnoty
	 *
	 * @param targetValue   hodnota property na cílovém objektu po změně
	 * @param sourceValue   hodnota property na zdrojovém objektu
	 * @param propertyName  název property pro kterou byla změna provedena
	 */
	public void afterPropertySet(Object targetValue, Object sourceValue, String propertyName);

}
