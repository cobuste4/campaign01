package edu.isu.cs.cs3308;

import java.io.*;

public class Driver {
    public static void main(String[] args) {
        // System.in source: https://www.webucator.com/how-to/how-use-systemin-java.cfm
        try {
            // Ask the user for a deck to use
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(isr);
            System.out.print("Enter the number for the deck to use for decryption. Options are:\n" +
                    "1: Deck 1\n" +
                    "2: Deck 2\n" +
                    "3: Deck 3\n" +
                    "4: Deck 4\n" +
                    "5: Deck 5\n" +
                    "6: Deck 6\n" +
                    "7: Deck 7\n" +
                    "8: Deck 8\n" +
                    "9: Deck 9\n" +
                    "10: Deck 10\n");
            String usrInput = br.readLine();
            String deckNumber = "data/deck" + usrInput + ".txt";

            // Ask the user for the messages to use
            System.out.print("Now enter a message or messages to encrypt: ");
            String messages = br.readLine();

            // Test output of the deck chosen and the messages
            System.out.format("Your deck is %s", deckNumber);
            System.out.println("Your message is " + messages);

            // Put contents of the deck into am int array
            FileInputStream fileIn = new FileInputStream(deckNumber);

            //int[] d = fileIn.read();

        } catch (IOException ioe) {
            System.out.println("Error reading your information");
        }
    }
}
