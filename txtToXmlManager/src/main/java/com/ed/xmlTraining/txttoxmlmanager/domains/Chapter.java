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
public class Chapter {

    private String chapterId;
    private ArrayList<Paragraph> paragraphs;

    public Chapter(String chapterId) {
        this.chapterId = chapterId;
        this.paragraphs = new ArrayList<>();
    }

    @XmlAttribute(name = "id")
    public String getChapterId() {
        return chapterId;
    }

    @XmlElement(name = "paragraph")
    public ArrayList<Paragraph> getParagraphs() {
        return paragraphs;
    }
}
