package edu.isu.cs.cs3308;

import edu.isu.cs.cs3308.structures.impl.CircularlyLinkedList;

import java.io.*;

/**
 * @author Steve Coburn
 * This class encrypts a message given a deck and returns the encrypted message
 */
public class SolitaireEncrypt {
    private String deckPath;
    private String encMessage;

    // Constructor(s)
    public SolitaireEncrypt(String deck) {
        deckPath = deck;
    }

    // Method(s)
    public String execute(String messageToEncrypt) {

        try {
            @SuppressWarnings("Duplicates")
            // Put contents of the deck into an array
                    // Code source: https://www.tutorialspoint.com/java/io/inputstreamreader_read_char.htm
                    FileInputStream fis = new FileInputStream(deckPath);
            FileReader fr = new FileReader(deckPath);
            BufferedReader br = new BufferedReader(fr);
            String tempDeck = br.readLine();
            String[] deckArrayTemp = tempDeck.split(" ");
            fis.close();
            //Convert String array to Int array
            // Code source: https://stackoverflow.com/questions/8348591/splitting-string-and-put-it-on-int-array
            int[] deckArray = new int[deckArrayTemp.length];
            for (int i = 0; i < deckArrayTemp.length; i++) {
                deckArray[i] = Integer.parseInt(deckArrayTemp[i]);
            }
        } catch (IOException ioe) {
            System.out.println("Error reading in the information");
        }


        // Convert the message that was given to a CLL of integers
        // Make the String into an array of chars. Source: https://stackoverflow.com/questions/10048899/string-to-char-array-java
        char[] charArray = messageToEncrypt.toCharArray();
        // Create an CLL of integers and using the ASCII table, eliminate all but upper and lower case letters from the array
        // ASCII table: http://www.asciitable.com/    ASCII 65-90 is uppercase, 97-122 is lower case
        // Continue source: https://stackoverflow.com/questions/14856028/how-to-skip-a-iteration-loop-in-while-loop
        CircularlyLinkedList<Integer> messageAsInts = new CircularlyLinkedList<>();
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] >= 65 && charArray[i] <= 90) { //Checks for uppercase letter
                charArray[i] -= 64; //sets the number from 1 to 26
                messageAsInts.addLast((int) charArray[i]);
            } else if (charArray[i] >= 97 && charArray[i] <= 122) { //Checks for lower case letter
                charArray[i] -= 96; //sets the number from 1 to 26
                messageAsInts.addLast((int) charArray[i]);
            }
        }
        // Add Xs to the end of the message if it is not a multiple of 5
        for(int i = 0; i < messageAsInts.size() % 5; i++){
            messageAsInts.addLast(24);
        }

        return encMessage;
    }
}
