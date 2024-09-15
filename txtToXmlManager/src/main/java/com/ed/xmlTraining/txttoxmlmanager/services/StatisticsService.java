package com.ed.xmlTraining.txttoxmlmanager.services;

import com.ed.xmlTraining.txttoxmlmanager.domains.Book;
import com.ed.xmlTraining.txttoxmlmanager.domains.Chapter;
import com.ed.xmlTraining.txttoxmlmanager.domains.Paragraph;
import com.ed.xmlTraining.txttoxmlmanager.domains.Statistics;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author User
 */
public class StatisticsService {

    /**
     * Parses a Book object and calculates its statistics: (number of
     * paragraphs), (number of lines), (number of words), (number of distinct
     * words), (date of creation), (author's name), (application class name).
     * After calculating it enters the statistics to the book's field.
     *
     * @param book the Book object given for statistics calculation
     */
    public void calculateBookStatistics(Book book) {
        //initialize Statistics object and counters
        Statistics stats = new Statistics();
        int nOfParagraphs = 0;
        int nOfLines = 0;
        int nOdWords = 0;
        HashSet<String> dWords = new HashSet<>();

        //parse the book to get statistics
        ArrayList<Chapter> bookChapters = book.getChapters();
        for (Chapter chapter : bookChapters) {
            ArrayList<Paragraph> chapterParagraphs = chapter.getParagraphs();
            nOfParagraphs += chapterParagraphs.size();
            for (Paragraph paragraph : chapterParagraphs) {
                ArrayList<String> paragraphLines = paragraph.getLines();
                nOfLines += paragraphLines.size();
                for (String line : paragraphLines) {
                    nOdWords += countWordsInLine(line, dWords);
                }
            }
        }
        //update the statistics object
        stats.setNumber_of_paragraphs(nOfParagraphs);
        stats.setNumber_of_lines(nOfLines);
        stats.setNumber_of_words(nOdWords);
        stats.setNumber_of_distinct_words(dWords.size());
        stats.setDate_of_creation(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        stats.setAuthor("Konstantinos Pailas");
        stats.setApplication_class_name("StatisticsService");
        //enter the updated statistics object in the book
        book.setStatistics(stats);
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
    public int countWordsInLine(String line, HashSet<String> distinctWords) {
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
