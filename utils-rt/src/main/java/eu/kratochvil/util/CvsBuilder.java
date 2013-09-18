package eu.kratochvil.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * @author Jiri Kratochvil <jiri.kratochvil@topmonks.com>
 */
public class CvsBuilder {

    /**
     * The default separator to use if none is supplied to the constructor.
     */
    public static final char DEFAULT_SEPARATOR = ',';

    /**
     * Indicates if values are escaped
     */
    public static final boolean DEFAULT_ESCAPE = true;

    /**
     * Default date format
     */
    public static final SimpleDateFormat DEFAULT_DATE_TIME_FORMATTER = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    private char separator;

    private boolean escape;

    private StringBuilder row;

    public CvsBuilder() {
        this(DEFAULT_SEPARATOR, DEFAULT_ESCAPE);
    }

    public CvsBuilder(char separator, boolean escape) {
        this.separator = separator;
        this.escape = escape;
        row = new StringBuilder();
    }


    public CvsBuilder buildLine(String[] values) {
        for (String str : values) {
            append(str);
        }
        return this;
    }

    public CvsBuilder append(String value) {
        if (row.length() > 0) {
            row.append(separator);
        }
        row.append(escape ? StringEscapeUtils.escapeCsv(value) : value);
        return this;
    }

    public CvsBuilder append(double value) {
        return append(Double.toString(value));
    }

    public CvsBuilder append(long value) {
        return append(Long.toString(value));
    }

    public CvsBuilder append(int value) {
        return append(Integer.toString(value));
    }

    public CvsBuilder append(Date value) {
        return append(DEFAULT_DATE_TIME_FORMATTER.format(value));
    }


    @Override
    public String toString() {
        return row.toString();
    }
}
