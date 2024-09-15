package com.ed.xmlTraining.txttoxmlmanager.domains;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author User
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Paragraph {

    private String paragraphId;
    private ArrayList<String> lines;

    public Paragraph(String paragraphId) {
        this.paragraphId = paragraphId;
        this.lines = new ArrayList<>();
    }

    @XmlAttribute(name = "id")
    public String getParagraphId() {
        return paragraphId;
    }

    @XmlElement(name = "line")
    public ArrayList<String> getLines() {
        return lines;
    }
}
