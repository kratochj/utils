/*
 * Created on 29.4.11
 * (C) 2011 ICZ, a.s.
 */
package eu.kratochvil.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.soap.server.endpoint.interceptor.PayloadValidatingInterceptor;

/**
 * @author Jiri Kratochvil (jiri.kratochvil@jetminds.com)
 * @version $Revision:$
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class TestSoapUtilsTest {


    public static final Logger logger = LoggerFactory.getLogger(TestSoapUtilsTest.class);

    PayloadValidatingInterceptor interceptor;

    public static final String SOAP_FAULT_FOR_RESPONSE = "<env:Envelope xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\"><env:Header/>" +
            "<env:Body><env:Fault><faultcode>env:Client</faultcode><faultstring xml:lang=\"en\">Validation error</faultstring><detail>" +
            "<spring-ws:ValidationError xmlns:spring-ws=\"http://springframework.org/spring-ws\">cvc-complex-type.2.4.d: Invalid content " +
            "was found starting with element 'aaa'. No child element is expected at this point.</spring-ws:ValidationError></detail>" +
            "</env:Fault></env:Body></env:Envelope>";
    private static final String SOAP_FAULT_FOR_REQUEST = "<SOAP-ENV:Envelopexmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
            "<SOAP-ENV:Header/><SOAP-ENV:Body><SOAP-ENV:Fault><faultcode>SOAP-ENV:Client</faultcode><faultstringxml:lang=\"en\">Validationerror</faultstring>" +
            "<detail><spring-ws:ValidationErrorxmlns:spring-ws=\"http://springframework.org/spring-ws\">" +
            "cvc-complex-type.2.4.a:Invalidcontentwasfoundstartingwithelement'aaa'.Oneof'{\"urn:cz:isvs:reg:schemas:RegTypy:v1\"" +
            ":Subjekt,\"urn:cz:isvs:reg:schemas:RegTypy:v1\":Uzivatel}'isexpected.</spring-ws:ValidationError></detail></SOAP-ENV:Fault>" +
            "</SOAP-ENV:Body></SOAP-ENV:Envelope>";

    /**
     * Setting konfiguration according spring context definition
     *
     * @throws Exception When some error occurs
     */
    @Before
    public void setup() throws Exception {
        interceptor = new PayloadValidatingInterceptor();
        interceptor.setValidateRequest(true);
        interceptor.setValidateResponse(true);

        Resource[] schemas = new ClassPathResource[2];
        schemas[0] = new ClassPathResource("xsd/envelope.xsd");
        schemas[1] = new ClassPathResource("xsd/test.xsd");
        interceptor.setSchemas(schemas);
        interceptor.afterPropertiesSet();
    }

    /**
     * Test scenario: Test will send <strong>valid</strong> request in {@link org.springframework.ws.context.MessageContext} to validator. We're expecting
     * <code>true</code> as result of validation
     *
     * @throws Exception When some error occurs
     */
    @Test
    public void testClientPayloadValidatingInterceptorRequestValidation() throws Exception {
        MessageContext messageContext = TestSoapUtils.createMessageContext("/request.xml", "/response.xml");
        Object genericEndpoint = TestSoapUtils.createGenericEndpoint();
        Assert.assertTrue(interceptor.handleRequest(messageContext, genericEndpoint));
    }

}
