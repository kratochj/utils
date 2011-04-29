package eu.kratochvil.util;


import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.context.DefaultMessageContext;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.endpoint.MethodEndpoint;
import org.springframework.ws.soap.saaj.SaajSoapMessage;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
import org.springframework.ws.soap.saaj.support.SaajUtils;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import java.io.IOException;

/**
 * Useful utilities for creating test requests in Spring WS.
 * <p/>
 * Usage is quite simple:
 * <pre>
 *   MessageContext messageContext = createMessageContext("/request.xml");
 *   Object genericEndpoint = createGenericEndpoint();
 *   Assert.assertFalse(interceptor.handleResponse(messageContext, genericEndpoint));
 * </pre>
 *
 * @author Jiri Kratochvil (jiri.kratochvil@jetminds.com)
 * @version $Revision:$
 */
public class TestSoapUtils {


    /**
     * Create essage context with SOAP request from file
     *
     * @param soapXmlFileRequest XML file with SOAP request
     * @return message context
     * @throws SOAPException When XML in file is not SOAP compliant
     * @throws IOException   When file was not found
     */
    public static MessageContext createMessageContext(String soapXmlFileRequest) throws SOAPException, IOException {
        return createMessageContext(soapXmlFileRequest, null);
    }


    /**
     * Create essage context with SOAP request and response from file
     *
     * @param soapXmlFileRequest  XML file with SOAP request
     * @param soapXmlFileResponse XML file with SOAP response
     * @return message context
     * @throws SOAPException When XML in file is not SOAP compliant
     * @throws IOException   When file was not found
     */
    public static MessageContext createMessageContext(String soapXmlFileRequest, String soapXmlFileResponse) throws SOAPException, IOException {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SaajSoapMessageFactory saajSoapMessageFactory = new SaajSoapMessageFactory(messageFactory);
        SOAPMessage soapMessage = SaajUtils.loadMessage(new ClassPathResource(soapXmlFileRequest, TestSoapUtils.class), messageFactory);
        SaajSoapMessage saajSoapMessage = new SaajSoapMessage(soapMessage);

        MessageContext messageContext = new DefaultMessageContext(saajSoapMessage, saajSoapMessageFactory);

        if (soapXmlFileResponse != null) {
            SOAPMessage soapMessageResponse = SaajUtils.loadMessage(new ClassPathResource(soapXmlFileResponse, TestSoapUtils.class), messageFactory);
            SaajSoapMessage saajSoapMessageResponse = new SaajSoapMessage(soapMessageResponse);
            messageContext.setResponse(saajSoapMessageResponse);
        }

        return messageContext;
    }

    /**
     * Created generic endpoint for unit testing (used just for example)
     *
     * @return Generic endpoint for webservice
     * @throws NoSuchMethodException when method in mplementing class doesn't exists
     */
    public static Object createGenericEndpoint() throws NoSuchMethodException {
        return new MethodEndpoint(new Object(), "toString", new Class[]{});
    }

}
