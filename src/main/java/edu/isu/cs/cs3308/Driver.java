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

            // Ask the user for encrypt or decrypt
            System.out.print("Enter E for encrypt or D for decrypt:");
            String encOrDec = br.readLine();
            isr.close();
            br.close();

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

            // Call Encrypt or Decrypt given the input
            if (encOrDec == "E") EncryptMessage(list, deckPath);
            else if (encOrDec == "D") DecryptMessage(list, deckPath);
            else System.out.println("Did not recognize your E for Encrypt mor D for Decrypt...");


        } catch (IOException ioe) {
            System.out.println("Error reading in the information");
        }
    }

    /**
     * This runs SolitaireEncrypt and outputting it's data to the user
     * @param messages - the CircularlyLinkedList of messages to encrypt
     * @param deck - the array of integers to use as the deck
     */
    public static void EncryptMessage(CircularlyLinkedList messages, String deck){
        // Run Encrypt in a loop until all messages are encrypted
        // Store each encrypted message in a CircularlyLinkedList
        CircularlyLinkedList<String> encryptedList = new CircularlyLinkedList<String>();
        while (!messages.isEmpty()){
            SolitaireEncrypt enc = new SolitaireEncrypt(deck);
            String tempMessage = (String)messages.removeFirst();
            encryptedList.addFirst(enc.execute(tempMessage));
        }

        // Print encrypted list to the user
        while (!encryptedList.isEmpty()) {
            System.out.println(encryptedList.removeFirst());
        }
    }

    /**
     * This runs SolitaireDecrypt and outputting it's data to the user
     * @param messages - the CircularlyLinkedList of messages to decrypt
     * @param deck - the array of integers to use as the deck
     */
    public static void DecryptMessage(CircularlyLinkedList messages, String deck){
        // Run Decrypt in a loop until all messages are decrypted
        // Store each decrypted message in a CircularlyLinkedList
        CircularlyLinkedList<String> decryptedList = new CircularlyLinkedList<String>();
        while (!messages.isEmpty()){
            SolitaireDecrypt dec = new SolitaireDecrypt(deck);
            String tempMessage = (String)messages.removeFirst();
            decryptedList.addFirst(dec.execute(tempMessage));
        }

        // Print decrypted list to the user
        while (!decryptedList.isEmpty()) {
            System.out.println(decryptedList.removeFirst());
        }
    }
}
