package com.ed.xmlTraining.txttoxmlmanager.services;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 *
 * @author User
 */
public class TxtToXmlParseService {

    public void writeXmlFromTxt(String xmlFilePath, String txtFilePath) {
        //initialize constants
        final int PARAGRAPHS_IN_CHAPTER = 20;
        final int LINES_IN_PARAGRAPH = 20;
        //initialize factory
        XMLOutputFactory factory = XMLOutputFactory.newInstance();
        //start reading file
        try (BufferedReader buffer = new BufferedReader(new FileReader(txtFilePath))) {
            //read the first line
            String fileLine = buffer.readLine();
            //get the first line's sentences to print in the queue  (the queue comes back empty if fileLine is null)
            ArrayDeque<String> printQueue = getNextLineSentences(fileLine, "");

            //initialize statistics
            int totalParagraphs = 0;
            int totalLines = 0;
            int totalWords = 0;
            HashSet<String> distinctWords = new HashSet<>();

            //start writing file
            OutputStream out = new FileOutputStream(xmlFilePath);
            XMLStreamWriter xmlWriter = factory.createXMLStreamWriter(out, "UTF-8");
            //start document
            System.out.println("================================================");
            System.out.println("Converting......");
            xmlWriter.writeStartDocument("UTF-8", "1.0");
            // start the root element book
            xmlWriter.writeStartElement("book");
            //CHAPTER LOOP
            int chapterCount = 0;
            while (fileLine != null) {
                chapterCount++;
                // start the root element chapter
                xmlWriter.writeStartElement("chapter");
                xmlWriter.writeAttribute("id", Integer.toString(chapterCount));
                //PARAGRAPH LOOP
                int paragraphCount = 0;
                while ((fileLine != null) && (paragraphCount < PARAGRAPHS_IN_CHAPTER)) {
                    paragraphCount++;
                    xmlWriter.writeStartElement("paragraph");
                    //LINE LOOP
                    int lineCount = 0;
                    while ((fileLine != null) && (lineCount < LINES_IN_PARAGRAPH)) {
                        if (printQueue.size() > 1) {
                            String lineToWrite = printQueue.poll(); //get line to write from queue
                            lineCount++;
                            totalWords += countWordsInLine(lineToWrite, distinctWords);
                            xmlWriter.writeStartElement("line");
                            xmlWriter.writeCharacters(lineToWrite);
                            xmlWriter.writeEndElement(); // end the element line
                        } else {
                            //there's only one element in the queue which is  the leftover sentence from the previous line
                            fileLine = buffer.readLine();   //so read the next line in the file
                            printQueue = getNextLineSentences(fileLine, printQueue.poll()); //and send it to replenish the queue
                        }
                    }
                    totalLines += lineCount;
                    xmlWriter.writeEndElement(); // end the element paragraph
                }
                totalParagraphs += paragraphCount;
                xmlWriter.writeEndElement(); // end the element chapter

            }

            //write down STATISTICS
            xmlWriter.writeStartElement("statistics");  //start the statistics element
            xmlWriter.writeStartElement("noumber_of_paragraphs");
            xmlWriter.writeCharacters(Integer.toString(totalParagraphs));
            xmlWriter.writeEndElement();
            xmlWriter.writeStartElement("noumber_of_lines");
            xmlWriter.writeCharacters(Integer.toString(totalLines));
            xmlWriter.writeEndElement();
            xmlWriter.writeStartElement("noumber_of_words");
            xmlWriter.writeCharacters(Integer.toString(totalWords));
            xmlWriter.writeEndElement();
            xmlWriter.writeStartElement("noumber_of_distinct_words");
            xmlWriter.writeCharacters(Integer.toString(distinctWords.size()));
            xmlWriter.writeEndElement();
            xmlWriter.writeStartElement("date_of_creation");
            xmlWriter.writeCharacters(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
            xmlWriter.writeEndElement();
            xmlWriter.writeStartElement("author");
            xmlWriter.writeCharacters("Konstantinos Pailas");
            xmlWriter.writeEndElement();
            xmlWriter.writeStartElement("application_class_name");
            xmlWriter.writeCharacters("txtToXmlManager");
            xmlWriter.writeEndElement();
            xmlWriter.writeEndElement();    //end the statistics element
            //close the file            
            xmlWriter.writeEndElement(); // end the root element book
            xmlWriter.writeEndDocument();   //end the document
            xmlWriter.close();  //close the writer
            out.close();        //close the output stream
            System.out.println("\n.xml file generated!!!");
            System.out.println("================================================");
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TxtToXmlParseService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TxtToXmlParseService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XMLStreamException ex) {
            Logger.getLogger(TxtToXmlParseService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Initialize queue. If the given line isn't null split it on each period
     * and maintain the period in the left String. Add the leftover unfinished
     * sentence from the previous line, in front of the first sentence. It
     * should be the beginning of it. Pass the sentences in the queue. Finally
     * if the last sentence of the given line ends on a period add an empty
     * sentence as there is no leftover for the next line.
     *
     * @param fileLine the file's given line to split
     * @param unfinishedSentence the leftover sentence from the previous line
     * @return ArrayDeque<String> with the sentences to enter in the xml
     * (returns empty queue if fileLine is null)
     */
    private ArrayDeque<String> getNextLineSentences(String fileLine, String unfinishedSentence) {
        ArrayDeque<String> nextLineSentences = new ArrayDeque<>();
        if (fileLine != null) {
            String[] sentences = fileLine.split("(?<=\\.)");
            sentences[0] = (unfinishedSentence + " " + sentences[0]).replaceAll("\\s+", " ");
            for (String sentence : sentences) {
                nextLineSentences.add(sentence);
            }
            if (sentences[sentences.length - 1].endsWith(".")) {
                nextLineSentences.add("");
            }
        }
        return nextLineSentences;
    }

    /**
     * Initialize word counter Format the line so there are no periods or commas
     * attached to the words Split the line into words by using a space (" ") as
     * separator Count the words and add them in the distinct words Set.
     *
     * @param line the given line to breakdown into words and count them
     * @param distinctWords the Set in which we will add the words of the
     * sentence
     * @return int which is the number of words in the line
     */
    private int countWordsInLine(String line, HashSet<String> distinctWords) {
        //
        int wordCounter = 0;
        //
        line = line.replace(".", " ").replace(",", " ").replaceAll("\\s+", " ");
        //
        String[] words = line.split(" ");
        //
        for (String word : words) {
            wordCounter++;
            distinctWords.add(word.toLowerCase());
        }
        return wordCounter;
    }

}
