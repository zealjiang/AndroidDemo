package com.example.libjava;

public class Node<T> {

    private Node<T> leftChild;
    private Node<T> rightChild;

    private T value;


    public Node(T value) {
        this.value = value;
    }

    public Node(Node<T> leftChild, Node<T> rightChild) {
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    public Node<T> getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(Node<T> leftChild) {
        this.leftChild = leftChild;
    }

    public Node<T> getRightChild() {
        return rightChild;
    }

    public void setRightChild(Node<T> rightChild) {
        this.rightChild = rightChild;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
