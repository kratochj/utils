package eu.kratochvil.util;


import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jiri Kratochvil (jiri.kratochvil@jetminds.com)
 * @version $Revision:$
 */
public class SoapUtils {

    private static final SimpleDateFormat isoDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");


    private static Map<String, String> fileCache = new HashMap<String, String>();

    public static String getSoapFromFile(String filenameXml, String filenameData, int row) throws IOException {
        return parsePlaceholders(filenameXml, CsvUtils.read(filenameData, row));
    }


    public static String parsePlaceholders(String filename, Map<String, String> params) throws IOException {
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

    public static Map<String, String> getSoapHeaders(String soapAction) {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Accept-Encoding", "gzip,deflate");
        headers.put("Content-Type", "text/xml;charset=UTF-8");
        headers.put("SOAPAction", soapAction); // i.e. RosSyncDotaz
        headers.put("User-Agent", "Faban/RegDriver");
        return headers;
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
