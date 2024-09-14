package com.ed.xmlTraining.txttoxmlmanager.domains;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author User
 */
@XmlRootElement(name = "statistics")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Statistics {
    private int numberOfParagraphs;
    private int numberOfLines;
    private int numberOfWords;
    private int numberOfDistinctWords;
    private String creationDate;
    private String authorName;
    private String applicationClassName; 
}
