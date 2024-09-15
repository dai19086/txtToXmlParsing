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
        //set file paths
        String txtFilePath = "files/txt/sample-lorem-ipsum-text-file.txt";
        String xmlNewFilePath = "files/xml/parsedTxt.xml";
        String xsdFilePath = "files/xml/bookSchema.xsd";
        //read and parse .txt file to .xml file
        TxtToXmlParseService txtToXmlConverter = new TxtToXmlParseService();
        txtToXmlConverter.writeXmlFromTxt(xmlNewFilePath, txtFilePath);
        //read generated .xml and save to a Book object
        XmlReadWriteService xmlReadWriter = new XmlReadWriteService();
        Book book = xmlReadWriter.xmlReader(xmlNewFilePath);
        //initialize an array with the paragraphs' ids we want to select for the new .xml file
        ArrayList<Integer> testParagraphs = new ArrayList<>();
        testParagraphs.add(2);
        testParagraphs.add(4);
        testParagraphs.add(5);
        testParagraphs.add(22);
        testParagraphs.add(44);
        testParagraphs.add(55);
        testParagraphs.add(222);
        testParagraphs.add(444);
        testParagraphs.add(555);
        testParagraphs.add(1000);
        testParagraphs.add(1001);
        testParagraphs.add(1002);
        testParagraphs.add(1003);
        testParagraphs.add(1004);
        testParagraphs.add(1005);
        testParagraphs.add(1006);
        testParagraphs.add(1007);
        testParagraphs.add(1008);
        testParagraphs.add(1009);
        testParagraphs.add(1010);
        testParagraphs.add(1011);
        testParagraphs.add(1012);
        testParagraphs.add(1013);
        testParagraphs.add(10000);
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
