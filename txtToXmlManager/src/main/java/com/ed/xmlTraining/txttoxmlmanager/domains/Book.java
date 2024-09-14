package com.ed.xmlTraining.txttoxmlmanager.domains;

import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author User
 */
@XmlRootElement(name = "book")
@Data
@AllArgsConstructor
public class Book {

    private ArrayList<Chapter> chapters;
    private Statistics statistics;

    public Book() {
        this.chapters = new ArrayList<>();
    }

}
