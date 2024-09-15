package com.ed.xmlTraining.txttoxmlmanager.domains;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author User
 */
@Data
@XmlRootElement(name = "book")
@XmlType(propOrder = {"chapters", "statistics"})
@AllArgsConstructor
public class Book {

    private ArrayList<Chapter> chapters;
    private Statistics statistics;

    public Book() {
        this.chapters = new ArrayList<>();
    }

    @XmlElement(name = "chapter")
    public ArrayList<Chapter> getChapters() {
        return chapters;
    }

    @XmlElement(name = "statistics")
    public Statistics getStatistics() {
        return statistics;
    }

}
