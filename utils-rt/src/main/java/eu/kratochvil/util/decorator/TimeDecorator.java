package eu.kratochvil.util.decorator;


import eu.kratochvil.util.Decorator;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Returns current time in iso format (i.e. for webservices)
 *
 * @author Jiri Kratochvil (jiri.kratochvil@jetminds.com)
 * @since 1.0
 */
public class TimeDecorator implements Decorator {

    private static final SimpleDateFormat isoDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");

    public String supportedKey() {
        return "TIME";
    }

    public String getValue() {
        return isoDateFormat.format(new Date());
    }
}
