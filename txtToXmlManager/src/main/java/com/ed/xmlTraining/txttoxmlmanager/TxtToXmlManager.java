package com.ed.xmlTraining.txttoxmlmanager;

import com.ed.xmlTraining.txttoxmlmanager.domains.Book;
import com.ed.xmlTraining.txttoxmlmanager.services.InformationProcessingService;
import com.ed.xmlTraining.txttoxmlmanager.services.TxtToXmlParseService;
import com.ed.xmlTraining.txttoxmlmanager.services.XmlReadWriteService;
import java.util.ArrayList;

/**
 *
 * @author User
 */
public class TxtToXmlManager {

    public static void main(String[] args) {
        String loremIpsumTxt = "sample-lorem-ipsum-text-file.txt";
        String loremIpsumXml = "parsedTxt.xml";
        String loremIpsumXmlWithSelectedParagraaphs = "selectedParagraphs.xml";
        String testTxt = "testDoc.txt";
        String testXml = "testFile.xml";
        String testXmlWithSelectedParagraaphs = "testParagraphs.xml";

        String txtFilePath = "files/txt/" + testTxt;
        String xmlNewFilePath = "files/xml/" + testXml;
        String xmlSelectedParagraphsPath = "files/xml/" + testXmlWithSelectedParagraaphs;

        TxtToXmlParseService txtToXmlConverter = new TxtToXmlParseService();
        txtToXmlConverter.writeXmlFromTxt(xmlNewFilePath, txtFilePath);

        XmlReadWriteService xmlReadWriter = new XmlReadWriteService();
        Book book = xmlReadWriter.xmlReader(xmlNewFilePath);

        InformationProcessingService processor = new InformationProcessingService();
        ArrayList<Integer> testParagraphs = new ArrayList<>();
        testParagraphs.add(1);
        testParagraphs.add(4);
        testParagraphs.add(5);
        processor.createXmlWithSelectedParagraphs(testParagraphs, book);

    }
}
