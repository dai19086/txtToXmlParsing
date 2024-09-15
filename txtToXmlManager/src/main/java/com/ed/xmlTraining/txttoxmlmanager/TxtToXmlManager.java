package com.ed.xmlTraining.txttoxmlmanager;

import com.ed.xmlTraining.txttoxmlmanager.domains.Book;
import com.ed.xmlTraining.txttoxmlmanager.services.InformationProcessingService;
import com.ed.xmlTraining.txttoxmlmanager.services.TxtToXmlParseService;
import com.ed.xmlTraining.txttoxmlmanager.services.ValidationService;
import com.ed.xmlTraining.txttoxmlmanager.services.XmlReadWriteService;
import com.ed.xmlTraining.txttoxmlmanager.services.XsdGeneratorService;
import java.util.ArrayList;

/**
 *
 * @author User
 */
public class TxtToXmlManager {

    public static void main(String[] args) {
        //set file names
        String loremIpsumTxt = "sample-lorem-ipsum-text-file.txt";
        String loremIpsumXml = "parsedTxt.xml";
        String testTxt = "testDoc.txt";
        String testXml = "testFile.xml";
        //set file paths
        String txtFilePath = "files/txt/" + testTxt;
        String xmlNewFilePath = "files/xml/" + testXml;
        String xsdFilePath = "files/xml/bookSchema.xsd";
        //read and parse .txt file to .xml file
        TxtToXmlParseService txtToXmlConverter = new TxtToXmlParseService();
        txtToXmlConverter.writeXmlFromTxt(xmlNewFilePath, txtFilePath);
        //read generated .xml and save to a Book object
        XmlReadWriteService xmlReadWriter = new XmlReadWriteService();
        Book book = xmlReadWriter.xmlReader(xmlNewFilePath);
        //initialize an array with the paragraphs' ids we want to select for the new .xml file
        ArrayList<Integer> testParagraphs = new ArrayList<>();
        testParagraphs.add(1);
        testParagraphs.add(4);
        testParagraphs.add(5);
        //call service to generate the custom .xml
        InformationProcessingService processor = new InformationProcessingService();
        String customFile = processor.createXmlWithSelectedParagraphs(testParagraphs, book);
        //generate schema for class Book
        XsdGeneratorService.xsdGenerator(xsdFilePath);
        //create List with the path of the files for validation
        ArrayList<String> fileList = new ArrayList<>();
        fileList.add(xmlNewFilePath);
        fileList.add("files/xml/" + customFile);
        //validate created .xml files with generated schema
        ValidationService validator = new ValidationService();
        validator.validateFiles(fileList, xsdFilePath);
        

    }
}
