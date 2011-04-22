package eu.kratochvil.util.decorator;


import eu.kratochvil.util.Decorator;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Jiri Kratochvil (jiri.kratochvil@jetminds.com)
 * @version $Revision:$
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
