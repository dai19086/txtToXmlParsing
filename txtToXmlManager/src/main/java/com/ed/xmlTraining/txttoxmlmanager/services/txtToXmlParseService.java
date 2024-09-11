package com.ed.xmlTraining.txttoxmlmanager.services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author User
 */
public class txtToXmlParseService {

    public ArrayList<String> parseTxtFile(String txtFilePath) {
        ArrayList<String> allBookLines = new ArrayList<>(); //initialize the list that will contain all the book's lines

        try (BufferedReader buffer = new BufferedReader(new FileReader(txtFilePath))) { //start reading file
            String fileLine;
            String unfinishedSentence = ""; //initialize to an empty carry over sentence
            while ((fileLine = buffer.readLine()) != null) {    //while there is a line read it
                fileLine = unfinishedSentence + " " + fileLine;   //append any carried over unfinished sentence (this  subsequently adds a space in the start of each  line)
                fileLine = fileLine.replaceAll("\\s+", " ");      //replace any multiple spaces in a line with a single one
                String[] sentences = fileLine.split("(?<=\\.)");  //split wherever there's a fullstop and keep the fullstop in the ending sentence
                int lastSentenceIndex = sentences.length - 1;   //find the index of the last sentence in this file's line
                sentences[lastSentenceIndex] = sentences[lastSentenceIndex].replace("\n", "");  //remove the next line character from the last sentence

                unfinishedSentence = sentences[lastSentenceIndex];
                //If the file's line finishes on a fullstop the last sentence would be the next line character (\n)
                //which will be converted to an empty string, thus not carrying over any unfinished sentence in the next line.
                //The same thing applies in an empty file line where the currently empty sentence is the last and only sentence read.
                //If the file line finishes without a fullstop it  means there are more things to be added in this sentence,
                //so we save it in the variable unfinishedSentence to append it in the next line.
                //If the next line of the file is an empty line the same thing will be repeated since there will still be no fullstops.

                //finally iterate the sentences read (excluding the last one which we dealt with in the previous step)
                //and add all the finished sentences in the list to be returned
                for (int i = 0; i < lastSentenceIndex; i++) {
                    allBookLines.add(sentences[i]);
                }
            }

            //in the end add any unfinished sentence if there is any, in case the file doesn't end with a fullstop
            if (!("").equals(unfinishedSentence)) {
                allBookLines.add(unfinishedSentence);
            }

        } catch (IOException e) {
            System.out.println("File not found or unable to be read!");
        }

        return allBookLines;
    }

}
