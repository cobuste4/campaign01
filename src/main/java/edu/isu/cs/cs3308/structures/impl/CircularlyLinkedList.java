package edu.isu.cs.cs3308.structures.impl;

import edu.isu.cs.cs3308.Node;

public class CircularlyLinkedList<E> extends SinglyLinkedList<E> {

    // CONSTURCTOR
    public CircularlyLinkedList() {
    }

    // METHODS
    @Override
    public void addLast(E element) {
        if (element != null) {
            Node<E> newest = new Node<>(element, head);
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

    @Override
    public E removeFirst() {
        if (isEmpty()) {
            return null;
        }
        E temp = head.getData();
        head = head.getNext();
        tail.setNext(head);
        size--;
        if (size == 0) {
            tail = null;
        }
        return temp;
    }

    @Override
    public E removeLast() {
        if (isEmpty()) {
            return null;
        }
        E toRemove = tail.getData();
        tail = head;
        for (int i = 1; i < size; i++) {
            tail = tail.getNext();
        }
        tail.setNext(head);
        size--;
        return toRemove;
    }
}
