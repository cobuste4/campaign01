package edu.isu.cs.cs3308.structures.impl;

import edu.isu.cs.cs3308.structures.List;

public class CircularlyLinkedList<E> implements List<E> {

    // Code example from our textbook, pages 126-7
    // Create a Node class with getters and setters
    private static class Node<E> {
        private E contents;
        private CircularlyLinkedList.Node<E> next;

        public Node(E data, CircularlyLinkedList.Node<E> n) {
            contents = data;
            next = n;
        }

        // Getters and setters
        public E getElement() {
            return contents;
        }

        public CircularlyLinkedList.Node<E> getNext() {
            return next;
        }

        public void setNext(CircularlyLinkedList.Node<E> n) {
            next = n;
        }

        public E getData() {
            return contents;
        }

        public void setData() {
            this.contents = contents;
        }
    }

    // Code example from our textbook, page 126-7
    // Instance variables of the Singly Linked List
    private CircularlyLinkedList.Node<E> head = null;
    private CircularlyLinkedList.Node<E> tail = null;
    private int size = 0;

    // CONSTURCTOR
    public CircularlyLinkedList() {
    }

    // METHODS
    public int size() {
        return size;
    }

    public boolean isEmpty() {
        if (size == 0) return true;
        return false;
    }

    public E first() {
        if (isEmpty()) return null;
        return head.getElement();
    }

    public E last() {
        if (isEmpty()) return null;
        return tail.getElement();
    }

    public void addFirst(E element) {
        if (element != null) {
            CircularlyLinkedList.Node nd = new CircularlyLinkedList.Node<>(element, head);
            head = nd;
            if (size == 0) {
                tail = head;
            }
            size++;
        }
    }

    public void addLast(E element) {
        if (element != null) {
            CircularlyLinkedList.Node<E> newest = new CircularlyLinkedList.Node<>(element, head);
            if (isEmpty()) {
                head = newest;
                newest.setNext(head);
            } else {
                tail.setNext(newest);
            }
            tail = newest;
            size++;
        }
    }

    public E removeFirst() {
        if (isEmpty()) {
            return null;
        }
        E temp = head.getElement();
        head = head.getNext();
        tail.setNext(head);
        size--;
        if (size == 0) {
            tail = null;
        }
        return temp;
    }

    public E removeLast() {
        if (isEmpty()) {
            return null;
        }
        E toRemove = tail.getElement();
        tail = head;
        for (int i = 1; i < size; i++) {
            tail = tail.getNext();
        }
        tail.setNext(head);
        size--;
        return toRemove;
    }

    // Insert node at index -1 so that it becomes the index
    public void insert(E element, int index) {
        if (element != null && index > 0) {
            // If the index is greater than the array, add node to the end
            if (index >= size) {
                addLast(element);
            } else {
                CircularlyLinkedList.Node<E> toInsert = new CircularlyLinkedList.Node<>(element, null);
                CircularlyLinkedList.Node<E> temp = head;
                for (int i = 0; i < index - 1; i++) {
                    temp = temp.getNext();
                }
                toInsert.setNext(temp.getNext());
                temp.setNext(toInsert);
                size++;
            }
        }
    }

    // In-class example. Removes the node AFTER the index given
    public E remove(int index) {
        if (index < 0 || index >= size) {
            return null;
        }

        CircularlyLinkedList.Node<E> current = head;
        for (int i = 0; i < index - 1; i++) {
            current = current.getNext();
        }
        CircularlyLinkedList.Node<E> toRemove = current.getNext();
        current.setNext(toRemove.getNext());
        toRemove.setNext(null);
        size--;
        return toRemove.getData();
    }

    public E get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }

        CircularlyLinkedList.Node<E> toReturn = head;
        for (int i = 0; i < index; i++) {
            toReturn = toReturn.getNext();
        }
        return toReturn.getData();
    }

    public void printList() {
        String stringToOutput = "";
        CircularlyLinkedList.Node<E> tempNode = head;
        for (int i = 0; i < size; i++) {
            stringToOutput += tempNode.getData().toString();
            stringToOutput += "\n";
            tempNode = tempNode.getNext();
        }
        System.out.println(stringToOutput);
    }

    public int indexOf(E element) {
        int result = -1;
        if (element == null) return result;
        CircularlyLinkedList.Node<E> tempNode = head;
        for (int i = 0; i < size; i++) {
            if (element == tempNode.getElement()) {
                result = i;
                return result;
            }
            tempNode = tempNode.getNext();
        }
        return result;
    }
}
