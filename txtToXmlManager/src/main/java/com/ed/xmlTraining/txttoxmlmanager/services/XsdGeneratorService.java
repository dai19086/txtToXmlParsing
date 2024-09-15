package com.ed.xmlTraining.txttoxmlmanager.services;

import com.ed.xmlTraining.txttoxmlmanager.domains.Book;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.SchemaOutputResolver;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

/**
 *
 * @author User
 */
public class XsdGeneratorService {

    public static void xsdGenerator(String xsdFilePath) {
        try {
            // create a JAXBContext for the class Book
            JAXBContext context = JAXBContext.newInstance(Book.class);
            // generate the XSD schema
            System.out.println("================================================");
            System.out.println("Generating .xsd schema..........................");
            context.generateSchema(new MySchemaOutputResolver(xsdFilePath));
            System.out.println("\nSchema generated!");
            System.out.println("================================================");
        } catch (IOException | JAXBException ex) {
            Logger.getLogger(XsdGeneratorService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Custom implementation of SchemaOutputResolver to output the XSD file
    static class MySchemaOutputResolver extends SchemaOutputResolver {

        private String xsdFileName;

        public MySchemaOutputResolver(String xsdFilePath) {
            this.xsdFileName = xsdFilePath; //initialize the file path for the schema to the inputed one
        }

        @Override
        public Result createOutput(String namespaceUri, String suggestedFileName) throws IOException {
            // Specify the location and file where the XSD will be saved
            File file = new File(xsdFileName);

            // Create a StreamResult to write the schema to the file
            StreamResult result = new StreamResult(file);

            // Return the StreamResult with systemId set to file's absolute path
            result.setSystemId(file.toURI().toString());

            return result;
        }
    }
}
