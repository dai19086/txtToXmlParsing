package com.ed.xmlTraining.txttoxmlmanager.services;

import com.ed.xmlTraining.txttoxmlmanager.domains.Book;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.xml.sax.SAXException;

/**
 *
 * @author User
 */
public class ValidationService {

    public void validateFiles(ArrayList<String> filePathList, String xsdFilePath) {
        System.out.println("================================================");
        System.out.println("Starting file validation........................");
        for (String filePath : filePathList) {
            if (valid(filePath, xsdFilePath)) {
                System.out.println("File (" + filePath + ") is VALID!");
            } else {
                System.out.println("File (" + filePath + ") is INVALID!");
            }
        }
        System.out.println("\nValidation complete!");
        System.out.println("================================================");
    }

    private boolean valid(String filePath, String xsdFilePath) {
        System.out.println("\nValidating file: " + filePath);
        boolean returnStatus = false;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Book.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(new File(xsdFilePath));
            unmarshaller.setSchema(schema);
            File xmlFile = new File(filePath);
            Object object = unmarshaller.unmarshal(xmlFile);
            returnStatus = true;
        } catch (JAXBException | SAXException ex) {
            Logger.getLogger(ValidationService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return returnStatus;
    }

}
