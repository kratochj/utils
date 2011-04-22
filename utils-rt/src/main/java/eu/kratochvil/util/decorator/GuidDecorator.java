package eu.kratochvil.util.decorator;


import eu.kratochvil.util.Decorator;

import java.util.UUID;

/**
 * Returns unique ID - used in {@link eu.kratochvil.util.CsvUtils}
 *
 * @author Jiri Kratochvil (jiri.kratochvil@jetminds.com)
 * @since 1.0
 */
public class GuidDecorator implements Decorator {

    public String supportedKey() {
        return "GUID";
    }

    public String getValue() {
        return UUID.randomUUID().toString();
    }
}
