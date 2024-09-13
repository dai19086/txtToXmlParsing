package com.ed.xmlTraining.txttoxmlmanager;

import com.ed.xmlTraining.txttoxmlmanager.services.TxtToXmlParseService;
import java.util.ArrayList;

/**
 *
 * @author User
 */
public class TxtToXmlManager {

    public static void main(String[] args) {
        String txtFileName = "testDoc.txt";
        String xmlNewFileName = "parsedTxt.xml";
        TxtToXmlParseService txtToXmlConverter = new TxtToXmlParseService();
        txtToXmlConverter.writeXmlFromTxt("files/xml/" + xmlNewFileName, "files/txt/" + txtFileName);
    }
}
