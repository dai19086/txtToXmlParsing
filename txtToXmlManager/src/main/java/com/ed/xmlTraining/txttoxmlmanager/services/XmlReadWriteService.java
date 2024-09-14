package com.ed.xmlTraining.txttoxmlmanager.services;

import com.ed.xmlTraining.txttoxmlmanager.domains.Book;
import com.ed.xmlTraining.txttoxmlmanager.domains.Chapter;
import com.ed.xmlTraining.txttoxmlmanager.domains.Line;
import com.ed.xmlTraining.txttoxmlmanager.domains.Paragraph;
import com.ed.xmlTraining.txttoxmlmanager.domains.Statistics;
import java.io.FileInputStream;
import java.io.IOException;
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

    public Book xmlReader(String filePath) throws ParserConfigurationException, SAXException, IOException, XMLStreamException {
        //initialize read  objects
        Book book = new Book();
        Chapter currChapter = null;
        Paragraph currParagraph = null;
        Line currLine = null;
        Statistics statistics = new Statistics();

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
        return book;
    }

}
