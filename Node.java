package com.company;

public class Node {
    private int value;
    private Color color;
    private Node parent;
    private Node rightChild;
    private  Node leftChild;

    public Node() {
        color = Color.RED;
        parent = null;
        rightChild = null;
        leftChild = null;
    }

    public Node(int value) {
        this();
        this.value = value;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Node getRightChild() {
        return rightChild;
    }

    public void setRightChild(Node rightChild) {
        this.rightChild = rightChild;
    }

    public Node getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(Node leftChild) {
        this.leftChild = leftChild;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }
}

