package com.ed.xmlTraining.txttoxmlmanager.domains;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author User
 */
@XmlRootElement(name = "book")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Statistics {
    private int numberOfParagraphs;
    private int numberOfLines;
    private int numberOfwords;
    private int numberOfDistinctWords;
    private String creationDate;
    private String authorName;
    private String applicationClassName;
}
