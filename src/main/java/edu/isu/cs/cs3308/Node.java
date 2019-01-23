package edu.isu.cs.cs3308;

public class Node<E> {
    private E contents;
    private Node<E> next;

    public Node(E data, Node<E> n) {
        contents = data;
        next = n;
    }

    // Getters and setters

    public Node<E> getNext() {
        return next;
    }

    public void setNext(Node<E> n) {
        next = n;
    }

    public E getData() {
        return contents;
    }

    public void setData() {
        this.contents = contents;
    }
}
