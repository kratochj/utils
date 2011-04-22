package eu.kratochvil.util;

/**
 * Interface for implementation dynamic fields in CSV file with source data
 * that will be used with {@link SoapUtils}.
 *
 * That is usefull when some dynamic data are needed such as date etc. then could be
 * used dynamic field <code>#FIELD_NAME#</code> which is replaced by result of
 * {@link #getValue()} method.
 *
 * @author Jiri Kratochvil (jiri.kratochvil@jetminds.com)
 * @since 1.0
 * @see CsvUtils
 */
public interface Decorator {

    /**
     * Key definition - result of this method with crosses on both sides (#...#)
     * could be used in source csv file with data
     * @return Name of field
     */
    String supportedKey();


    /**
     * Dynamic generated value that will replace key in csv file with
     * source data
     * @return Generated value
     */
    String getValue();
}
