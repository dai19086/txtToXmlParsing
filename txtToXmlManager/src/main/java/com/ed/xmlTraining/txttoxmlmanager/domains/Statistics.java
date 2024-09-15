package com.ed.xmlTraining.txttoxmlmanager.domains;

import jakarta.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author User
 */
@XmlType(propOrder = {"number_of_paragraphs", "number_of_lines", "number_of_words", "number_of_distinct_words", "date_of_creation", "author", "application_class_name"})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Statistics {

    private int number_of_paragraphs;
    private int number_of_lines;
    private int number_of_words;
    private int number_of_distinct_words;
    private String date_of_creation;
    private String author;
    private String application_class_name;
}
