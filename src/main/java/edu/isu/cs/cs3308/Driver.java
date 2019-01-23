package edu.isu.cs.cs3308;

import edu.isu.cs.cs3308.structures.impl.CircularlyLinkedList;

import java.io.*;

/**
 * @author Steve Coburn
 * DueDate: 27 January 2019
 * ISUCourse: CS3308
 * Instructor: Isaac Griffith
 * Project: campaign01
 * Description: Main class that takes user input and calls the encrypt/decrypt
 */

public class Driver {
    public static void main(String[] args) {
        // System.in source: https://www.webucator.com/how-to/how-use-systemin-java.cfm
        try {
            // Ask the user for a deck to use
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(isr);
            System.out.print("Enter only the nyumber for the deck to use for encryption/decryption:");
            String usrDeckInput = br.readLine();
            String deckPath = "data/deck" + usrDeckInput + ".txt";

            // Ask the user for the messages to use
            System.out.print("Now enter the filename containing the message(s) to encrypt/decrypt\n" +
                    "(the data folder and txt extension will be added for you: ");
            String usrMessageInput = br.readLine();
            String MessagePath = "data/" + usrMessageInput + ".txt";
            isr.close();

            /**
             *     // Test output of the deck chosen and the messages
             *     System.out.format("Your deck is %s\n", deckPath);
             *     System.out.println("Your message is " + MessagePath + "\n");
             */

            //Put contents of the deck into an array
            // Code source: https://www.tutorialspoint.com/java/io/inputstreamreader_read_char.htm
            FileInputStream fis = new FileInputStream(deckPath);
            FileReader fr1 = new FileReader(deckPath);
            br = new BufferedReader(fr1);
            String temp = br.readLine();
            String[] deckArrayTemp = temp.split(" ");
            fis.close();
            //Convert String array to Int array
            // Code source: https://stackoverflow.com/questions/8348591/splitting-string-and-put-it-on-int-array
            int[] deckArray = new int[deckArrayTemp.length];
            for(int i = 0; i < deckArrayTemp.length; i++) {
                deckArray[i] = Integer.parseInt(deckArrayTemp[i]);
            }


            // Put contents of the messages into a list structure
            // Code source: https://www.java-samples.com/showtutorial.php?tutorialid=392
            FileReader fr = new FileReader(MessagePath);
            br = new BufferedReader(fr);
            CircularlyLinkedList<String> list = new CircularlyLinkedList<String>();
            String tempString;
            while ((tempString = br.readLine()) != null) {
                list.addLast(tempString);
            }
            fr.close();

            //Print message(s) to user
            while (list.size() > 0) {
                System.out.println(list.removeFirst());
            }

        } catch (IOException ioe) {
            System.out.println("Error reading your information");
        }
    }
}
