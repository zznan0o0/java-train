

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.dwg.DWGParser;
import org.apache.tika.sax.BodyContentHandler;
import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Cad {
    @Test
    public void testCad() throws IOException, TikaException, SAXException {
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        FileInputStream inputStream = new FileInputStream(new File("C:\\Users\\dever\\Desktop\\dwg\\111.dwg"));

        DWGParser dwgParser = new DWGParser();
        dwgParser.parse(inputStream, handler, metadata);

        System.out.println(handler);
        System.out.println(metadata);
    }
}
