package edu.isu.cs.cs3308;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;

public class SolitaireDecrypt {
    private String deckPath;
    private String decMessage;

    // Constructor(s)
    public SolitaireDecrypt(String deck) {
        deckPath = deck;
    }

    // Method(s)
    public String execute(String messagesToDecrypt) {

        try {
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

        // Do stuff here for the decryption. deckArray is the deck, and messagesToEncrypt is the messages

        return decMessage;
    }

}
