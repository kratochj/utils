package eu.kratochvil.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.io.FileNotFoundException;

/**
 * @author Jiri Kratochvil (jiri.kratochvil@jetminds.com)
 * @version $Revision:$
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class SoapUtilsTest {

    public static final String SOAP_ROS_CTI_ICO_XML =
            "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:cz:isvs:ros:schemas:RosUnivDotazy:v1\" xmlns:data=\"urn:cz:isvs:ros:schemas:RosDotazyData:v2\" xmlns:reg=\"urn:cz:isvs:reg:schemas:RegTypy:v1\">\n" +
                    "   <soapenv:Header/>\n" +
                    "   <soapenv:Body>\n" +
                    "      <urn:RosSyncDotaz verzeZadosti=\"0.0.5\">\n" +
                    "         <urn:KodSluzby>RosCtiIco</urn:KodSluzby>\n" +
                    "         <urn:ZadostInfo>\n" +
                    "            <reg:CasZadosti>${IGNORE}</reg:CasZadosti>\n" +
                    "            <reg:Agenda>IAIS</reg:Agenda>\n" +
                    "            <reg:OvmAgendy>OVM1</reg:OvmAgendy>\n" +
                    "            <reg:Ais>AIS1</reg:Ais>\n" +
                    "            <reg:Subjekt>Subjekt1</reg:Subjekt>\n" +
                    "            <reg:Uzivatel>Uživatel1</reg:Uzivatel>\n" +
                    "            <reg:DuvodUcel>Důvod a účel 1</reg:DuvodUcel>\n" +
                    "            <reg:AgendaZadostId>${IGNORE}</reg:AgendaZadostId>\n" +
                    "            <reg:IszrZadostId>${IGNORE}</reg:IszrZadostId>\n" +
                    "         </urn:ZadostInfo>\n" +
                    "         <urn:AutorizaceInfo>\n" +
                    "            <reg:Role>?</reg:Role>\n" +
                    "            <reg:MaximalniPocetZaznamu>10</reg:MaximalniPocetZaznamu>\n" +
                    "            <reg:SeznamUdaju>\n" +
                    "            </reg:SeznamUdaju>\n" +
                    "         </urn:AutorizaceInfo>\n" +
                    "         <urn:Dotaz verzeSluzby=\"0.0.5\">\n" +
                    "            <data:RosCtiIcoData>\n" +
                    "              <data:pp>5555</data:pp>\n" +
                    "            </data:RosCtiIcoData>\n" +
                    "         </urn:Dotaz>\n" +
                    "      </urn:RosSyncDotaz>\n" +
                    "   </soapenv:Body>\n" +
                    "</soapenv:Envelope>";

    public static final String SOAP_ROS_CTI_PODLE_UDAJU_XML = "<env:Envelope xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
            "    <env:Header/>\n" +
            "    <env:Body>\n" +
            "        <ns2:RobSyncDotaz xmlns:ns2=\"urn:cz:isvs:rob:schemas:RobUnivDotazy:v1\"\n" +
            "                          xmlns:ns3=\"urn:cz:isvs:reg:schemas:RegTypy:v1\"\n" +
            "                          xmlns:ns4=\"urn:cz:isvs:rob:schemas:RobUnivEditace:v1\" verzeZadosti=\"0.0.5\">\n" +
            "            <ns2:KodSluzby>RobCtiPodleUdaju</ns2:KodSluzby>\n" +
            "            <ns2:ZadostInfo>\n" +
            "                <ns3:CasZadosti>${IGNORE}</ns3:CasZadosti>\n" +
            "                <ns3:Agenda>agenda1</ns3:Agenda>\n" +
            "                <ns3:OvmAgendy>12345678</ns3:OvmAgendy>\n" +
            "                <ns3:Ais>IAIS-ROS</ns3:Ais>\n" +
            "                <ns3:Subjekt>12345678</ns3:Subjekt>\n" +
            "                <ns3:Uzivatel>iais.user.gestoreditor</ns3:Uzivatel>\n" +
            "                <ns3:AgendaZadostId>${IGNORE}</ns3:AgendaZadostId>\n" +
            "                <ns3:IszrZadostId>${IGNORE}</ns3:IszrZadostId>\n" +
            "            </ns2:ZadostInfo>\n" +
            "            <ns2:AutorizaceInfo>\n" +
            "                <ns3:Role>GESTOREDITOR</ns3:Role>\n" +
            "                <ns3:SeznamUdaju>\n" +
            "                    <ns3:RobSeznamUdaju></ns3:RobSeznamUdaju>\n" +
            "                </ns3:SeznamUdaju>\n" +
            "            </ns2:AutorizaceInfo>\n" +
            "            <ns2:MapaAifo/>\n" +
            "            <ns2:SeznamIdAdres/>\n" +
            "            <ns2:Dotaz verzeSluzby=\"0.0.51\">\n" +
            "                <ns6:RobCtiPodleUdajuData xmlns:ns6=\"urn:cz:isvs:rob:schemas:RobDotazyData:v1\"\n" +
            "                                          xmlns=\"urn:cz:isvs:ros:schemas:RosTypy:v2\"\n" +
            "                                          xmlns:ns2=\"urn:cz:isvs:ros:schemas:RosEditaceData:v2\"\n" +
            "                                          xmlns:ns3=\"urn:cz:isvs:ros:schemas:RosEditaceTypy:v1\"\n" +
            "                                          xmlns:ns4=\"urn:cz:isvs:ros:schemas:RosDotazyData:v2\"\n" +
            "                                          xmlns:ns5=\"urn:cz:isvs:ros:schemas:RosDotazyTypy:v1\"\n" +
            "                                          xmlns:ns7=\"urn:cz:isvs:rob:schemas:RobTypy:v1\">\n" +
            "                    <ns6:MaximalniPocet>50</ns6:MaximalniPocet>\n" +
            "                    <ns6:DatumNarozeni>1960-07-23+01:00</ns6:DatumNarozeni>\n" +
            "                    <ns6:DatumUmrtiUrcenoSoudem>false</ns6:DatumUmrtiUrcenoSoudem>\n" +
            "                    <ns6:Jmeno>Marie</ns6:Jmeno>\n" +
            "                    <ns6:Prijmeni>Novakova</ns6:Prijmeni>\n" +
            "                </ns6:RobCtiPodleUdajuData>\n" +
            "            </ns2:Dotaz>\n" +
            "        </ns2:RobSyncDotaz>\n" +
            "    </env:Body>\n" +
            "</env:Envelope>";


    @Test
    public void testRosCtiIcoSoapRequest() throws Exception {
        ExtendedAsserts.assertEqualsWithIgnoreWithoutSpacesAndNL(SOAP_ROS_CTI_ICO_XML, SoapUtils.getSoapFromFile("/RosCtiIcoRequest.xml", "/RosCtiIcoData.csv", 5));
    }

    @Test
    public void testRosCtiPodleUdajuRequest() throws Exception {
        ExtendedAsserts.assertEqualsWithIgnoreWithoutSpacesAndNL(SOAP_ROS_CTI_PODLE_UDAJU_XML, SoapUtils.getSoapFromFile("/RosCtiPodleUdajuRequest.xml", "/RosCtiPodleUdajuData.csv", 1));
    }
    @Test
    public void testRosCtiPodleUdajuRequestOutOfRange() throws Exception {
        try {
            ExtendedAsserts.assertEqualsWithIgnoreWithoutSpacesAndNL(SOAP_ROS_CTI_PODLE_UDAJU_XML, SoapUtils.getSoapFromFile("/RosCtiPodleUdajuRequest.xml", "/RosCtiPodleUdajuData.csv", 2));
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        Assert.fail("IndexOutOfBoundsException expected");
    }


    @Test
    public void testRosCtiIcoSoapRequestFromNonExistingFile() throws Exception {
        try {
            SoapUtils.getSoapFromFile("XXXRosCtiIcoRequest.xml", "xxx.csv", 5);
        } catch (FileNotFoundException e) {
            return;
        }

        Assert.fail("Not thrown FileNotFoundException");
    }
}
