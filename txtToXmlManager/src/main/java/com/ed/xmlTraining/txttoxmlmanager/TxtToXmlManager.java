package com.ed.xmlTraining.txttoxmlmanager;

import com.ed.xmlTraining.txttoxmlmanager.services.TxtToXmlParseService;

/**
 *
 * @author User
 */
public class TxtToXmlManager {

    public static void main(String[] args) {
        String txtFileName = "testDoc.txt";//"sample-lorem-ipsum-text-file.txt";
        String xmlNewFileName = "testFile.xml";//"parsedTxt.xml";
        TxtToXmlParseService txtToXmlConverter = new TxtToXmlParseService();
        txtToXmlConverter.writeXmlFromTxt("files/xml/" + xmlNewFileName, "files/txt/" + txtFileName);
    }
}
