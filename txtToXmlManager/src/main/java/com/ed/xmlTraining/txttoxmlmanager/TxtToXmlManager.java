package com.ed.xmlTraining.txttoxmlmanager;

import com.ed.xmlTraining.txttoxmlmanager.services.txtToXmlParseService;
import java.util.ArrayList;

/**
 *
 * @author User
 */
public class TxtToXmlManager {

    public static void main(String[] args) {
        String txtFileName = "testDoc.txt";
        txtToXmlParseService txtToXmlConverter = new txtToXmlParseService();
        ArrayList<String> allFileLines = txtToXmlConverter.parseTxtFile("files/txt/" + txtFileName);
        for (String line : allFileLines){
            System.out.println(line);
        }
    }
}
