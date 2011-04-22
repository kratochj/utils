package eu.kratochvil.util;

import eu.kratochvil.util.decorator.GuidDecorator;
import eu.kratochvil.util.decorator.TimeDecorator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Parser for CSV files with caching for quick random access to specified row in file.
 * CSV parser expects that on first row is header with field names.
 *
 * As result of {@link #read(String, int) call} is map with field name as key and
 * corresponding values in given row.
 *
 * Parser supports dynamic generated fields in source files such as GUID or actual
 * date. Implementation is done by {@link Decorator decorators}
 *
 * @author Jiri Kratochvil (jiri.kratochvil@jetminds.com)
 * @version $Revision:$
 * @see Decorator
 * @see GuidDecorator
 * @see TimeDecorator
 * @since 1.0
 */
public class CsvUtils {

    private static final Logger logger = LoggerFactory.getLogger(CsvUtils.class);

    private static Map<String, List<String>> cachedFiles = new HashMap<String, List<String>>();

    private static Map<String, Decorator> decorators = new HashMap<String, Decorator>();

    static {
        // Register decorators
        registerDecorator(new GuidDecorator());
        registerDecorator(new TimeDecorator());
    }


    protected static String[] readHeader(String filename) throws IOException {
        URL url = SoapUtils.class.getResource(filename);
        if (url == null) {
            throw new FileNotFoundException(filename);
        }

        if (cachedFiles.get(url.getFile()) == null) {
            InputStream is = SoapUtils.class.getResourceAsStream(filename);
            loadFile(url.getFile(), is);
        }

        String header = cachedFiles.get(url.getFile()).get(0);

        return header.split(",");
    }


    public static Map<String, String> read(String filename, int row) throws IOException {
        URL url = CsvUtils.class.getResource(filename);

        if (url == null) {
            throw new FileNotFoundException(filename);
        }

        if (cachedFiles.get(CsvUtils.class.getResource(filename).getFile()) == null) {
            InputStream is = CsvUtils.class.getResourceAsStream(filename);
            loadFile(url.getFile(), is);
        }

        String[] header = readHeader(filename);
        String[] data = cachedFiles.get(url.getFile()).get(row).split(",");


        Map<String, String> result = new HashMap<String, String>();
        for (int i = 0; i < header.length; i++) {

            if (decorators.get(data[i].trim()) != null) {
                result.put(header[i], decorators.get(data[i].trim()).getValue());
            } else {
                result.put(header[i], data[i].trim());
            }
        }
        return result;
    }

    public static void registerDecorator(Decorator decorator) {
        decorators.put("#" + decorator.supportedKey() + "#", decorator);
    }


    private static void loadFile(String filename, InputStream is) throws IOException {
        logger.trace("Loading file: " + is);


        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String strLine = null;

        List<String> file = new ArrayList<String>();

        while ((strLine = br.readLine()) != null) {
            file.add(strLine);
        }
        cachedFiles.put(filename, file);
    }
}
