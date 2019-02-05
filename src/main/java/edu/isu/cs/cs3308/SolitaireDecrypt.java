package edu.isu.cs.cs3308;

import edu.isu.cs.cs3308.structures.impl.CircularlyLinkedList;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;

public class SolitaireDecrypt {
    private String decMessage = "";
    private CircularlyLinkedList<Integer> deckList = new CircularlyLinkedList<>();
    private CircularlyLinkedList<Integer> messageAsInts = new CircularlyLinkedList<>();
    private CircularlyLinkedList<Integer> keystream = new CircularlyLinkedList<>();
    private int firstJokerPos;
    private int secondJokerPos;
    String enableLogging = "n";

    // Constructor(s)
    SolitaireDecrypt(String deck) {
        DeckToList(deck);
    }

    // Method(s)
    String execute(String messagesToDecrypt) {
        MessageToIntList(messagesToDecrypt);

        // Call StepOne, and the other steps will follow
        // Loops as many times as there are chars in the message
        while (keystream.size() != messageAsInts.size()) {
            StepOne();
        }

        PairwiseSubtract();

        return decMessage;
    }

    /**
     * This method takes the path to the deck and adds its values into a List called DeckList.
     *
     * @param deckPath - the pathname to a file containing a "deck".
     */
    @SuppressWarnings("Duplicates")
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
    @SuppressWarnings("Duplicates")
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
    @SuppressWarnings("Duplicates")
    private void StepOne() {

        // Code to print verbose encrypt/decrypt
        if (enableLogging.equals("y")) {
            System.out.println("\n\n\nPrior to Step 1:");
            deckList.printList();
        }

        firstJokerPos = deckList.indexOf(27);
        secondJokerPos = deckList.indexOf(28);
        // If firstJoker is at the tail, swap head and tail
        if (deckList.get(firstJokerPos).equals(deckList.last())) {
            int head = deckList.removeFirst();
            int tail = deckList.removeLast();
            deckList.addFirst(tail);
            deckList.addLast(head);
        } else if (deckList.get(firstJokerPos).equals(deckList.first())) {
            int first = deckList.removeFirst();
            int second = deckList.removeFirst();
            deckList.addFirst(first);
            deckList.addFirst(second);
        } else if (firstJokerPos == 26) {
            int jk = deckList.remove(firstJokerPos);
            int last = deckList.removeLast();
            deckList.addLast(last);
            deckList.addLast(jk);
        } else {
            int jokerData = deckList.remove(firstJokerPos);
            int afterData = deckList.remove(firstJokerPos);
            deckList.insert(afterData, firstJokerPos);
            firstJokerPos += 1;
            deckList.insert(jokerData, firstJokerPos);
        }
        firstJokerPos = deckList.indexOf(27);
        secondJokerPos = deckList.indexOf(28);

        // Code to print verbose encrypt/decrypt
        if (enableLogging.equals("y")) {
            System.out.println("\nAfter Step 1: Move 27 down 1");
            deckList.printList();
        }

        StepTwo();
    }

    /**
     * This is step 2 in the Encryption process
     * It moves the second joker down the deck 2 places.
     */
    @SuppressWarnings("Duplicates")
    private void StepTwo() {
        int secondJoker = deckList.remove(secondJokerPos);
        if (secondJokerPos == 26) {
            int head = deckList.removeFirst();
            deckList.addFirst(secondJoker);
            deckList.addLast(head);
        } else if (secondJokerPos == 27) {
            int head = deckList.removeFirst();
            deckList.insert(secondJoker, 1);
            deckList.addLast(head);
        } else if (secondJokerPos == 0) {
            int first = deckList.removeFirst();
            int second = deckList.removeFirst();
            deckList.addFirst(secondJoker);
            deckList.addFirst(second);
            deckList.addFirst(first);
        } else {
            deckList.insert(secondJoker, secondJokerPos + 2);
        }
        firstJokerPos = deckList.indexOf(27);
        secondJokerPos = deckList.indexOf(28);

        // Code to print verbose encrypt/decrypt
        if (enableLogging.equals("y")) {
            System.out.println("\nAfter Step 2: Move 28 down 2");
            deckList.printList();
        }

        StepThree();
    }

    /**
     * This is step 3 in the Encryption process.
     * It performs a "triple cut" on the deck, surrounding the jokers.
     */
    @SuppressWarnings("Duplicates")
    private void StepThree() {
        CircularlyLinkedList<Integer> beginning = new CircularlyLinkedList<>();
        CircularlyLinkedList<Integer> ending = new CircularlyLinkedList<>();
        // If my variables are swapped, run the for loops backwards
        if (firstJokerPos > secondJokerPos) {
            int sz = deckList.size() - 1;
            for (int i = firstJokerPos; i < sz; i++) {
                ending.addLast(deckList.removeLast());
            }
            for (int i = 0; i < secondJokerPos; i++) {
                beginning.addFirst(deckList.removeFirst());
            }
        } else {
            int sz = deckList.size() - 1;
            for (int i = secondJokerPos; i < sz; i++) {
                ending.addLast(deckList.removeLast());
            }
            for (int i = 0; i < firstJokerPos; i++) {
                beginning.addFirst(deckList.removeFirst());
            }
        }

        int sz = beginning.size();
        for (int i = 0; i < sz; i++) {
            deckList.addLast(beginning.removeLast());
        }
        sz = ending.size();
        for (int i = 0; i < sz; i++) {
            deckList.addFirst(ending.removeFirst());
        }

        // Code to print verbose encrypt/decrypt
        if (enableLogging.equals("y")) {
            System.out.println("\nAfter Step 3: Triple Cut");
            deckList.printList();
        }

        StepFour();
    }

    /**
     * This is step 4 in the Encryption process.
     * It takes the bottom card and counts down from the top by that amount.
     * Then take that amount of top cards and move them to the bottom.
     * Put the last card back in the last slot. Both jokers use value 27.
     */
    @SuppressWarnings("Duplicates")
    private void StepFour() {
        int lastCard = deckList.removeLast();
        int temp = lastCard;
        CircularlyLinkedList<Integer> list = new CircularlyLinkedList<>();
        if (lastCard == 28) temp = 27;
        for (int i = 0; i < temp; i++) {
            deckList.addLast(deckList.removeFirst());
        }
        deckList.addLast(lastCard);

        // Code to print verbose encrypt/decrypt
        if (enableLogging.equals("y")) {
            System.out.println("\nAfter Step 4: Move top cards to bottom");
            deckList.printList();
        }

        StepFive();
    }

    /**
     * This is step 5 in the Encryption process.
     * It takes the top card value (plus 1) and counts down the deck. Repeat if it's a joker
     * The value reached is one keystream value.
     */
    @SuppressWarnings("Duplicates")
    private void StepFive() {
        int topCard = deckList.first();
        if (topCard == 28) {
            topCard = 27;
        }

        int magic = deckList.get(topCard);
        if (magic != 27 && magic != 28) {
            keystream.addLast(magic);
        } else {
            // It's a joker, start back from step 1
            StepOne();
        }

        // Code to print verbose encrypt/decrypt
        if (enableLogging.equals("y")) {
            System.out.println("\nAfter Step 5, keystream is:");
            keystream.printList();
        }
    }

    /**
     * This will take the keystream list and the deck list and pairwise subtract them.
     * Stores the result (decrypted message) into decMessage.
     */
    private void PairwiseSubtract() {
        // Add keystream with number representation of the message. Mod 26 if less than 26, subtract 26 if more than 26
        // Convert back to letters (uppercase)
        // Result is the decrypted message
        decMessage = "";
        int sz = keystream.size();
        for (int i = 0; i < sz; i++) {
            int result = 0;
            if (messageAsInts.get(0) <= keystream.get(0)) {
                int temp = messageAsInts.removeFirst() + 26;
                result = temp - keystream.removeFirst();
            } else {
                result = messageAsInts.removeFirst() - keystream.removeFirst();
            }

            if (result > 26) result = result - 26;
            // Convert to uppercase letter
            result += 64;
            decMessage += (char) result;
        }
    }

}
