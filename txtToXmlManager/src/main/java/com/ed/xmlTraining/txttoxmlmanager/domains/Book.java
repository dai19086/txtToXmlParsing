package com.ed.xmlTraining.txttoxmlmanager.domains;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import java.util.ArrayList;
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
public class Book {
    private ArrayList<Chapter> chapters;
}
