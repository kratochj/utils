package eu.kratochvil.util.decorator;


import eu.kratochvil.util.Decorator;

import java.util.UUID;

/**
 * @author Jiri Kratochvil (jiri.kratochvil@jetminds.com)
 * @version $Revision:$
 */
public class GuidDecorator implements Decorator {

    public String supportedKey() {
        return "GUID";
    }

    public String getValue() {
        return UUID.randomUUID().toString();
    }
}
