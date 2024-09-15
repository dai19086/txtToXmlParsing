package com.ed.xmlTraining.txttoxmlmanager.services;

import com.ed.xmlTraining.txttoxmlmanager.domains.Book;
import com.ed.xmlTraining.txttoxmlmanager.domains.Chapter;
import com.ed.xmlTraining.txttoxmlmanager.domains.Paragraph;
import java.util.ArrayList;

/**
 *
 * @author User
 */
public class InformationProcessingService {

    private static int customXmlsCreatedByThisService = 0;

    /**
     *  /**
     * This method extracts paragraphs based on the provided list of paragraph
     * Ids from the original Book object, organizes them into chapters, and
     * writes the resulting content into a new XML file. It handles the case
     * where selected paragraphs span multiple chapters and ensures that
     * chapters in the output XML do not exceed a predefined number of
     * paragraphs.
     *
     * @param paragraphIds an ArrayList<Integer> which contains the Ids of the
     * paragraphs that will be included in the new .xml file
     * @param book the Book object that should contain these paragraphs
     * @return String the name of the custom file created
     */
    public String createXmlWithSelectedParagraphs(ArrayList<Integer> paragraphIds, Book book) {
        Book customParagraphsBook = new Book();
        int chapterId = 1;
        Chapter customChapter = new Chapter(Integer.toString(chapterId));
        int paragraphsInChapter = TxtToXmlParseService.PARAGRAPHS_IN_CHAPTER;
        //since ids are incremental and we know up to how many paragraphs are in a chapter
        //we can use those to pinpoint the position of a paragraph in the original book object
        System.out.println("================================================");
        System.out.println("Separating paragraphs...........................");
        for (int paragraphId : paragraphIds) {
            int chapterPosition = (paragraphId - 1) / paragraphsInChapter;  //calculate the chapter the paragraph should be
            int paragraphPosition = (paragraphId - 1) % paragraphsInChapter;    //calculate the position the paragraph should be in the chapter
            //check if the chapter we calculated exists in the book to avoid Array index out of bounds exception
            if (book.getChapters().size() > chapterPosition) {
                //check if the position we calculated for the paragraph exists in the book
                //to avoid Array index out of bounds exception
                if (book.getChapters().get(chapterPosition).getParagraphs().size() > paragraphPosition) {   //
                    //get the paragraph
                    Paragraph paragraphToAdd = book
                            .getChapters().get(chapterPosition)
                            .getParagraphs().get(paragraphPosition);
                    //check if we reached the allowed paragraphs in the chapter
                    if (customChapter.getParagraphs().size() >= paragraphsInChapter) {
                        //add the full chapter in the book
                        customParagraphsBook.getChapters().add(customChapter);
                        //initialize the next chapter
                        chapterId++;
                        customChapter = new Chapter(Integer.toString(chapterId));
                    }
                    //add the paragraph in the chapter
                    customChapter.getParagraphs().add(paragraphToAdd);
                } else {
                    System.out.println("Paragraph id " + paragraphId + " NOT FOUND in the expected chapter...SKIPPING...");
                }
            } else {
                System.out.println("Paragraph id " + paragraphId + " NOT FOUND in the book...SKIPPING...");
            }
        }
        //adding the final chapter in the book
        customParagraphsBook.getChapters().add(customChapter);
        //calculate and add the statistics to the book
        System.out.println("\nCalculating new book's statistics.............");
        StatisticsService statsCalculator = new StatisticsService();
        statsCalculator.calculateBookStatistics(customParagraphsBook);
        //writing the .xml file
        System.out.println("\nCreating custom paragraphs book .xml file.....");
        XmlReadWriteService xmlWriter = new XmlReadWriteService();
        this.customXmlsCreatedByThisService++;
        String customFileName = "customParagraphsBook" + customXmlsCreatedByThisService + ".xml";
        xmlWriter.writeBookToXml(customParagraphsBook, "files/xml/" + customFileName);
        System.out.println("\nFile " + customFileName + " created!");
        System.out.println("================================================");

        return customFileName;
    }

}
