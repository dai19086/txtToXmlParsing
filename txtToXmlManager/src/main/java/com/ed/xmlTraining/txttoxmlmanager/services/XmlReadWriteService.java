package com.ed.xmlTraining.txttoxmlmanager.services;

import com.ed.xmlTraining.txttoxmlmanager.domains.Book;
import com.ed.xmlTraining.txttoxmlmanager.domains.Chapter;
import com.ed.xmlTraining.txttoxmlmanager.domains.Line;
import com.ed.xmlTraining.txttoxmlmanager.domains.Paragraph;
import com.ed.xmlTraining.txttoxmlmanager.domains.Statistics;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import org.xml.sax.SAXException;

/**
 *
 * @author User
 */
public class XmlReadWriteService {

    /**
     * Reads the given xml file containing book information and returns a Book
     * object populated with the parsed data, using StAX.
     *
     * @param filePath the file of the path to read
     * @return the Book object read from the .xml file
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     * @throws XMLStreamException
     */
    public Book xmlReader(String filePath){
        //initialize read  objects
        Book book = new Book();
        Chapter currChapter = null;
        Paragraph currParagraph = null;
        Line currLine = null;
        Statistics statistics = new Statistics();

        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLEventReader eventReader = factory.createXMLEventReader(new FileInputStream(filePath));

            //start reading the contents of the xml
            System.out.println("================================================");
            System.out.println("Reading .xml file...............................");
            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();   //read first line

                //if it is a start element proceed in reinitializing or reading the information
                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    //check for element <chapter>
                    if (("chapter").equals(startElement.getName().getLocalPart())) {
                        currChapter = new Chapter(startElement
                                .getAttributeByName(javax.xml.namespace.QName.valueOf("id"))
                                .getValue());
                    } //check for element <paragraph>
                    else if (("paragraph").equals(startElement.getName().getLocalPart())) {
                        currParagraph = new Paragraph(startElement
                                .getAttributeByName(javax.xml.namespace.QName.valueOf("id"))
                                .getValue());
                    } //check for element <line>
                    else if (("line").equals(startElement.getName().getLocalPart())) {
                        currLine = new Line();
                        event = eventReader.nextEvent();    //read the line
                        if (event instanceof Characters) {
                            currLine.setLine(event.asCharacters().getData());
                        }
                    } //check for element <statistics>
                    else if (("statistics").equals(startElement.getName().getLocalPart())) {
                        statistics = new Statistics();
                    } //check for element <number_of_paragraphs>
                    else if (("number_of_paragraphs").equals(startElement.getName().getLocalPart())) {
                        event = eventReader.nextEvent();    //read the content of the statistic
                        if (event instanceof Characters) {
                            statistics.setNumberOfParagraphs(Integer.parseInt(event.asCharacters().getData()));
                        }
                    } //check for element <number_of_lines>
                    else if (("number_of_lines").equals(startElement.getName().getLocalPart())) {
                        event = eventReader.nextEvent();    //read the content of the statistic
                        if (event instanceof Characters) {
                            statistics.setNumberOfLines(Integer.parseInt(event.asCharacters().getData()));
                        }
                    } //check for element <number_of_words>
                    else if (("number_of_words").equals(startElement.getName().getLocalPart())) {
                        event = eventReader.nextEvent();    //read the content of the statistic
                        if (event instanceof Characters) {
                            statistics.setNumberOfWords(Integer.parseInt(event.asCharacters().getData()));
                        }
                    } //check for element <number_of_distinct_words>
                    else if (("number_of_distinct_words").equals(startElement.getName().getLocalPart())) {
                        event = eventReader.nextEvent();    //read the content of the statistic
                        if (event instanceof Characters) {
                            statistics.setNumberOfDistinctWords(Integer.parseInt(event.asCharacters().getData()));
                        }
                    } //check for element <date_of_creation>
                    else if (("date_of_creation").equals(startElement.getName().getLocalPart())) {
                        event = eventReader.nextEvent();    //read the content of the statistic
                        if (event instanceof Characters) {
                            statistics.setCreationDate(event.asCharacters().getData());
                        }
                    } //check for element <author>
                    else if (("author").equals(startElement.getName().getLocalPart())) {
                        event = eventReader.nextEvent();    //read the content of the statistic
                        if (event instanceof Characters) {
                            statistics.setAuthorName(event.asCharacters().getData());
                        }
                    } //check for element <application_class_name>
                    else if (("application_class_name").equals(startElement.getName().getLocalPart())) {
                        event = eventReader.nextEvent();    //read the content of the statistic
                        if (event instanceof Characters) {
                            statistics.setApplicationClassName(event.asCharacters().getData());
                        }
                    }

                }

                //if it is an end element save the data you read before in the object hierarchy
                if (event.isEndElement()) {
                    EndElement endElement = event.asEndElement();
                    //check for element </chapter>
                    if ("chapter".equals(endElement.getName().getLocalPart())) {
                        book.getChapters().add(currChapter);
                    } //check for element </paragraph>
                    else if ("paragraph".equals(endElement.getName().getLocalPart())) {
                        currChapter.getParagraphs().add(currParagraph);
                    } //check for element </line>
                    else if ("line".equals(endElement.getName().getLocalPart())) {
                        currParagraph.getLines().add(currLine);
                    } //check for element </statistics>
                    else if ("statistics".equals(endElement.getName().getLocalPart())) {
                        book.setStatistics(statistics);
                    }

                }
            }
            System.out.println("\nRead complete!");
            System.out.println("================================================");
        } catch (IOException ex) {
            Logger.getLogger(XmlReadWriteService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XMLStreamException ex) {
            Logger.getLogger(XmlReadWriteService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return book;
    }

    /**
     * Define context for JAXB for the Book object. Create marshaller Set
     * beautifier. Creating xml to write. Marshalling book to xml.
     *
     * @param book the Book object we want to write in the file
     * @param filePath the path and name of the .xml file to be created
     */
    public void writeBookToXml(Book book, String filePath) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Book.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            System.out.println("================================================");
            System.out.println("Creating new .xml file..........................");
            File newXml = new File(filePath);
            marshaller.marshal(book, newXml);
            System.out.println("\nFile created!");
            System.out.println("================================================");
        } catch (JAXBException ex) {
            Logger.getLogger(XmlReadWriteService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
