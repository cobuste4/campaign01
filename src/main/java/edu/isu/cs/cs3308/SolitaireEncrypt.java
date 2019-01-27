package edu.isu.cs.cs3308;

import edu.isu.cs.cs3308.structures.impl.CircularlyLinkedList;

import java.io.*;

/**
 * @author Steve Coburn
 * This class encrypts a message given a deck and returns the encrypted message
 */
public class SolitaireEncrypt {
    private String encMessage;
    private CircularlyLinkedList<Integer> deckList = new CircularlyLinkedList<>();
    private CircularlyLinkedList<Integer> messageAsInts = new CircularlyLinkedList<>();
    private CircularlyLinkedList<Integer> keystream = new CircularlyLinkedList<>();
    private int firstJokerPos;
    private int secondJokerPos;

    // Constructor(s)
    public SolitaireEncrypt(String deck) {
        DeckToList(deck);
    }

    // Method(s)
    public String execute(String messageToEncrypt) {
        MessageToIntList(messageToEncrypt);

        // Get first and second joker positions
        firstJokerPos = deckList.indexOf(27);
        secondJokerPos = deckList.indexOf(28);

        // Call StepOne, and the other steps will follow
        // Loops as many times as there are chars in the message
        for (int i = 0; i < messageAsInts.size(); i++){
            StepOne();
        }

        // Add keystream with number representation of the message. Mod 26 if less than 26, subtract 26 if more than 26
        // Convert back to letters (uppercase)
        // Result is the encrypted message

        return encMessage;
    }

    /**
     * This method takes the path to the deck and adds its values into a List called DeckList.
     *
     * @param deckPath - the pathname to a file containing a "deck".
     */
    private void DeckToList(String deckPath) {
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
            // Convert Int array into a CircularlyLinkedList
            for (int i = 0; i < deckArray.length; i++) {
                deckList.addLast(deckArray[i]);
            }
        } catch (IOException ioe) {
            System.out.println("Error reading in the information");
        }
    }

    /**
     * This method takes the message to be encrypted, converts it to ints and adds padding.
     * Puts the resulting data into a List called messageAsInts.
     *
     * @param message - the unformatted message to be encrypted
     */
    private void MessageToIntList(String message) {
        // Convert the message that was given to a CLL of integers
        // Make the String into an array of chars. Source: https://stackoverflow.com/questions/10048899/string-to-char-array-java
        char[] charArray = message.toCharArray();
        // Create an CLL of integers and using the ASCII table, eliminate all but upper and lower case letters from the array
        // ASCII table: http://www.asciitable.com/    ASCII 65-90 is uppercase, 97-122 is lower case
        // Continue source: https://stackoverflow.com/questions/14856028/how-to-skip-a-iteration-loop-in-while-loop
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
        for (int i = 0; i < messageAsInts.size() % 5; i++) {
            messageAsInts.addLast(24);
        }
    }

    /**
     * This is step 1 in the Encryption process.
     * It takes the first joker card and swaps it with the card below(after) it.
     */
    private void StepOne() {
        // If firstJoker is at the tail, swap head and tail
        if (deckList.get(firstJokerPos).equals(deckList.last())) {
            int head = deckList.removeFirst();
            int tail = deckList.removeLast();
            deckList.addFirst(tail);
            deckList.addLast(head);
        } else {
            int afterData = deckList.remove(firstJokerPos);
            int jokerData = deckList.remove(firstJokerPos - 1);
            deckList.insert(jokerData, firstJokerPos + 1);
            deckList.insert(afterData, firstJokerPos);
        }
        StepTwo();
    }

    /**
     * This is step 2 in the Encryption process
     * It moves the second joker down the deck 2 places.
     */
    private void StepTwo() {
        int secondJoker = deckList.remove(secondJokerPos);
        deckList.insert(secondJoker, secondJokerPos + 2);

        StepThree();
    }

    /**
     * This is step 3 in the Encryption process.
     * It performs a "triple cut" on the deck, surrounding the jokers.
     */
    private void StepThree() {
        CircularlyLinkedList<Integer> beginning = new CircularlyLinkedList<>();
        CircularlyLinkedList<Integer> ending = new CircularlyLinkedList<>();
        for (int i = 0; i < firstJokerPos; i++) {
            beginning.addFirst(deckList.removeFirst());
        }
        for (int i = secondJokerPos; i < deckList.size(); i++) {
            ending.addLast(deckList.removeLast());
        }
        for (int i = 0; i < beginning.size(); i++) {
            deckList.addLast(beginning.removeFirst());
        }
        for (int i = 0; i < ending.size(); i++) {
            deckList.addFirst(ending.removeFirst());
        }

        StepFour();
    }

    /**
     * This is step 4 in the Encryption process.
     * It takes the bottom card and counts down from the top by that amount.
     * Then take that amount of top cards and move them to the bottom.
     * Put the last card back in the last slot. Both jokers use value 27.
     */
    private void StepFour() {
        int lastCard = deckList.removeLast();
        CircularlyLinkedList<Integer> list = new CircularlyLinkedList<>();
        for (int i = 0; i < lastCard; i++) {
            deckList.addLast(deckList.removeFirst());
        }
        deckList.addLast(lastCard);

        StepFive();
    }

    /**
     * This is step 5 in the Encryption process.
     * It takes the top card value (plus 1) and counts down the deck. Repeat if it's a joker
     * The value reached is one keystream value.
     */
    private void StepFive() {
        int topCard = deckList.first();
        int magic = deckList.get(topCard + 1);
        if (magic != 27 && magic != 28) {
            keystream.addFirst(magic);
        }else{
            // It's a joker, start back from step 1
            StepOne();
        }
    }
}
