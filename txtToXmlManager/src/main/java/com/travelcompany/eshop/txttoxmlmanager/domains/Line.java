package com.travelcompany.eshop.txttoxmlmanager.domains;

import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author User
 */
@XmlRootElement(name = "line")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Line {
    private ArrayList<String> words;
}
