package eu.kratochvil.util;

/**
 * @author Jiri Kratochvil (jiri.kratochvil@jetminds.com)
 * @version $Revision:$
 */
public interface Decorator {

    String supportedKey();

    String getValue();
}
