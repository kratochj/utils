package eu.kratochvil.util.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jiri Kratochvil <jiri.kratochvil@topmonks.com>
 */
public class JndiTestQueue {

    public static final Logger logger = LoggerFactory.getLogger(JndiTestQueue.class);

    String value = "NA";

    public String getValue() {
        logger.debug("Reading value: {}", value);
        return value;
    }

    public void setValue(String value) {
        this.value = value;
        logger.debug("New value \"{}\" set", value);
    }
}
