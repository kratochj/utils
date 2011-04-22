package eu.kratochvil.util;


import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Generator SOAP request for testing. Generator supports placeholders for data that are
 * defined in CSV file. As a source for SOAP requests is simple file with placeholders
 * (<code>${PLACEHOLDER}</code>) that will be replaced by data in CSV file.
 * <p/>
 * CSV File must have as first row header with coresponding field names that could be used
 * in request definition. See example of CSV file:
 * <pre>
 *     <strong>AAA, BBB, CCC</strong>
 *       1,   2,   3
 *       4,   5,   6
 * </pre>
 * <p/>
 * SOAP request definition may looks like:
 * <pre>
 * &lt;soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:urn="urn:test:v1"&gt;
 *     &lt;soapenv:Header/&gt;
 *     &lt;soapenv:Body&gt;
 *         &lt;urn:test somevalue="<strong>${AAA}</strong>"&gt;
 *             &lt;urn:code&gt;<strong>${BBB}</strong>&lt;/urn:code&gt;
 *             &lt;urn:info&gt;<strong>${CCC}</strong>&lt;urn:info&gt;
 *         &lt;/urn:test&gt;
 *     &lt;/soapenv:Body&gt;
 * &lt;/soapenv:Envelope&gt;
 * </pre>
 * <p/>
 * and result will be:
 * <pre>
 * &lt;soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:urn="urn:test:v1"&gt;
 *     &lt;soapenv:Header/&gt;
 *     &lt;soapenv:Body&gt;
 *         &lt;urn:test somevalue="<strong>1</strong>"&gt;
 *             &lt;urn:code&gt;<strong>2</strong>&lt;/urn:code&gt;
 *             &lt;urn:info&gt;<strong>3</strong>&lt;urn:info&gt;
 *         &lt;/urn:test&gt;
 *     &lt;/soapenv:Body&gt;
 * &lt;/soapenv:Envelope&gt;
 * </pre>
 *
 * @author Jiri Kratochvil (jiri.kratochvil@jetminds.com)
 * @version $Revision:$
 */
public class SoapUtils {

    private static Map<String, String> fileCache = new HashMap<String, String>();

    public static String getSoapFromFile(String filenameXml, String filenameData, int row) throws IOException {
        return parsePlaceholders(filenameXml, CsvUtils.read(filenameData, row));
    }


    protected static String parsePlaceholders(String filename, Map<String, String> params) throws IOException {
        URL url = SoapUtils.class.getResource(filename);
        if (url == null) {
            throw new FileNotFoundException(filename);
        }

        String request;
        if (fileCache.get(url.getFile()) != null) {
            request = fileCache.get(url.getFile());
        } else {
            request = readFileAsString(SoapUtils.class.getResourceAsStream(filename));
            fileCache.put(url.getFile(), request);
        }


        for (Map.Entry<String, String> entry : params.entrySet()) {
            request = request.replaceAll(placeholder(entry.getKey()), entry.getValue().trim());
        }

        return request;
    }


    private static String placeholder(String placeholder) {
        return new StringBuffer().append("\\$\\{").append(placeholder).append("\\}").toString();
    }

    private static String readFileAsString(InputStream is) throws java.io.IOException {
        final char[] buffer = new char[0x1000];
        StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(is, "UTF-8");
        int read;
        do {
            read = in.read(buffer, 0, buffer.length);
            if (read > 0) {
                out.append(buffer, 0, read);
            }
        } while (read >= 0);

        return out.toString();
    }
}
