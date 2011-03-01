/*
 * Created on 25.2.2011
 * (C) 2011 ICZ, a.s.
 */
package eu.kratochvil.util;

import org.junit.Test;

import static org.junit.Assert.*    ;

/**
 * @author Jiri Kratochvil (jiri.kratochvil@jetminds.com)
 * @version $Revision:$
 */
public class StringUtilsTest {

    public final String XML_EXAMPLE1 = "<?xml version='1.0' encoding='UTF-8'?>" +
            "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
            "<S:Body><ns2:sayHelloResponse xmlns:ns2=\"http://vinodsingh.com\">" +
            "<return>Hello Test!</return>" +
            "</ns2:sayHelloResponse></S:Body></S:Envelope>";
    public final String XML_EXAMPLE2 = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
            "<S:Body><ns2:sayHelloResponse xmlns:ns2=\"http://vinodsingh.com\">" +
            "<return>Hello Test!</return>" +
            "</ns2:sayHelloResponse></S:Body></S:Envelope>";
    public final String NON_XML_EXAMPLE1 = "<?xml version='1.0' encoding='UTF-8'?>" +
            "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
            "<S:Body><ns2:sayHelloResponse xmlns:ns2=\"http://vinodsingh.com\">" +
            "<return>Hello Test!</return>" +
            "</ns2:sayHelloResponse></S:Body></S:EnvelopeX>";
    public final String NON_XML_EXAMPLE2 = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
            "<S:Body><ns2:sayHelloResponse xmlns:ns2=\"http://vinodsingh.com\">" +
            "<return>Hello Test!</return>" +
            "</ns2:sayHelloResponse></S:Body></S:EnvelopeX>";
    public final String NON_XML_EXAMPLE3 = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">";

    @Test
    public void testIsXml() throws Exception {
        assertTrue(StringUtils.isXmlLike(XML_EXAMPLE1));
        assertTrue(StringUtils.isXmlLike(XML_EXAMPLE2));
    }

    @Test
    public void testIsNotXml() throws Exception {
        assertFalse(StringUtils.isXmlLike(NON_XML_EXAMPLE1));
        assertFalse(StringUtils.isXmlLike(NON_XML_EXAMPLE2));
        assertFalse(StringUtils.isXmlLike(NON_XML_EXAMPLE3));
    }

    @Test
    public void testNotBlankValue() throws Exception {
        assertEquals("TEST", StringUtils.notBlankValue("TEST", "ANOTHER"));
        assertEquals("ANOTHER", StringUtils.notBlankValue("", "ANOTHER"));
        assertEquals("ANOTHER", StringUtils.notBlankValue(null, "ANOTHER"));
    }
}
