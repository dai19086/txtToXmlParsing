package com.ed.xmlTraining.txttoxmlmanager;

import com.ed.xmlTraining.txttoxmlmanager.domains.Book;
import com.ed.xmlTraining.txttoxmlmanager.services.TxtToXmlParseService;
import com.ed.xmlTraining.txttoxmlmanager.services.XmlReadWriteService;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import org.xml.sax.SAXException;

/**
 *
 * @author User
 */
public class TxtToXmlManager {

    public static void main(String[] args) {
        String loremIpsumTxt = "sample-lorem-ipsum-text-file.txt";
        String loremIpsumXml = "parsedTxt.xml";
        String testTxt = "testDoc.txt";
        String testXml = "testFile.xml";

        String txtFileName = "files/txt/" + testTxt;
        String xmlNewFileName = "files/xml/" + testXml;
        TxtToXmlParseService txtToXmlConverter = new TxtToXmlParseService();
        txtToXmlConverter.writeXmlFromTxt(xmlNewFileName, txtFileName);

        XmlReadWriteService xmlReadWriter = new XmlReadWriteService();

        Book book = xmlReadWriter.xmlReader(xmlNewFileName);
        System.out.println(book);

    }
}
