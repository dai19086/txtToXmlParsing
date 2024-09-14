package com.ed.xmlTraining.txttoxmlmanager.domains;

import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author User
 */
@XmlRootElement(name = "chapter")
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
}
