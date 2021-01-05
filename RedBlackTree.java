package com.company;

public class RedBlackTree {
    private Node root;
    private Node nil;

    public RedBlackTree() {
        nil = new Node();
        nil.setColor(Color.BLACK);
        root = nil;
        root.setParent(nil);
        root.setRightChild(nil);
        root.setLeftChild(nil);
    }

    public Node getRoot() {
        return root;
    }

    public void insert(int n) {
        Node currentNode = root;
        Node parentOfnewNode = nil;
        Node newNode = new Node(n);
        while (!isNil(currentNode)) {
            parentOfnewNode = currentNode;
            if (n > currentNode.getValue()) {
                currentNode = currentNode.getRightChild();
            } else {
                currentNode = currentNode.getLeftChild();
            }
        }
        newNode.setParent(parentOfnewNode);
        if (!isNil(parentOfnewNode)) {
            if (n < parentOfnewNode.getValue()) {
                parentOfnewNode.setLeftChild(newNode);
            } else {
                parentOfnewNode.setRightChild(newNode);
            }
        } else {
            root = newNode;
        }
        newNode.setRightChild(nil);
        newNode.setLeftChild(nil);
        balanceInsertion(newNode);
    }

    private void balanceInsertion(Node target) {
        Node currentNode = nil;
        while (target.getParent().getColor() == Color.RED) {
            if (target.getParent() == target.getParent().getParent().getLeftChild()) {
                currentNode = target.getParent().getParent().getRightChild();
                if (currentNode.getColor() == Color.RED) {
                    target.getParent().setColor(Color.BLACK);
                    currentNode.setColor(Color.BLACK);
                    target.getParent().getParent().setColor(Color.RED);
                    target = target.getParent().getParent();
                } else if (target == target.getParent().getRightChild()) {
                    target = target.getParent();
                    leftRotate(target);
                } else {
                    target.getParent().setColor(Color.BLACK);
                    target.getParent().getParent().setColor(Color.RED);
                    rightRotate(target.getParent().getParent());
                }
            } else {
                currentNode = target.getParent().getParent().getLeftChild();
                if (currentNode.getColor() == Color.RED) {
                    target.getParent().setColor(Color.BLACK);
                    currentNode.setColor(Color.BLACK);
                    target.getParent().getParent().setColor(Color.RED);
                    target = target.getParent().getParent();
                } else if (target == target.getParent().getLeftChild()) {
                    target = target.getParent();
                    rightRotate(target);
                } else {
                    target.getParent().setColor(Color.BLACK);
                    target.getParent().getParent().setColor(Color.RED);
                    leftRotate(target.getParent().getParent());
                }
            }
        }
        root.setColor(Color.BLACK);
    }

    private void rotate(Node pivot, boolean direction) {
        if (direction) {
            rightRotate(pivot);
        } else {
            leftRotate(pivot);
        }
    }

    private void leftRotate(Node pivot) {
        Node currentNode = pivot.getRightChild();
        pivot.setRightChild(currentNode.getLeftChild());
        if (!isNil(currentNode.getLeftChild()))
            currentNode.getLeftChild().setParent(pivot);
        currentNode.setParent(pivot.getParent());
        if (isNil(pivot.getParent()))
            root = currentNode;
        else if (pivot.getParent().getLeftChild() == pivot)
            pivot.getParent().setLeftChild(currentNode);
        else
            pivot.getParent().setRightChild(currentNode);
        currentNode.setLeftChild(pivot);
        pivot.setParent(currentNode);
    }

    private void rightRotate(Node pivot) {
        Node currentNode = pivot.getLeftChild();
        pivot.setLeftChild(currentNode.getRightChild());
        if (!isNil(currentNode.getRightChild()))
            currentNode.getRightChild().setParent(pivot);
        currentNode.setParent(pivot.getParent());
        if (isNil(pivot.getParent()))
            root = currentNode;
        else if (pivot.getParent().getRightChild() == pivot)
            pivot.getParent().setRightChild(currentNode);
        else
            pivot.getParent().setLeftChild(currentNode);
        currentNode.setRightChild(pivot);
        pivot.setParent(currentNode);
    }

    public void print() {
        print(root);
    }

    private void print(Node node) {
        if (!isNil(node.getLeftChild())) {
            print(node.getLeftChild());
        }
        System.out.println(node.getValue());
        if (!isNil(node.getRightChild()))
            print(node.getRightChild());
    }

    public Node treeMinimum(Node node) {
        while (!isNil(node.getLeftChild()))
            node = node.getLeftChild();
        return node;
    }

    public Node treeSuccessor(Node target) {
        if (!isNil(target.getLeftChild()))
            return treeMinimum(target.getRightChild());
        Node currentNode = target.getParent();
        while (!isNil(currentNode) && target == currentNode.getRightChild()) {
            target = currentNode;
            currentNode = currentNode.getParent();
        }
        return currentNode;
    }

    public Node find(int value) {
        Node currentNode = root;
        while (!isNil(currentNode)) {
            if (currentNode.getValue() == value)
                return currentNode;
            else if (currentNode.getValue() < value)
                currentNode = currentNode.getRightChild();
            else
                currentNode = currentNode.getLeftChild();
        }
        return null;
    }

    public void remove(int num) {
        Node currentNode = new Node();
        currentNode.setValue(num);
        Node treeNode = find(currentNode.getValue());
        Node child = nil;
        Node target = nil;
        if (isNil(treeNode.getLeftChild()) || isNil(treeNode.getRightChild()))
            target = treeNode;
        else target = treeSuccessor(treeNode);
        if (!isNil(target.getLeftChild()))
            child = target.getLeftChild();
        else
            child = target.getRightChild();
        child.setParent(target.getParent());

        if (isNil(target.getParent()))
            root = child;

        else if (!isNil(target.getParent().getLeftChild()) && target.getParent().getLeftChild() == target)
            target.getParent().setLeftChild(child);

        else if (!isNil(target.getParent().getRightChild()) && target.getParent().getRightChild() == target)
            target.getParent().setRightChild(child);
        if (target != treeNode) {
            treeNode.setValue(target.getValue());
        }
        if (target.getColor() == Color.BLACK)
            balanceRemove(child);
    }

    private void balanceRemove(Node currentNode) {
        Node uncle;
        while (currentNode != root && currentNode.getColor() == Color.BLACK) {
            if (currentNode == currentNode.getParent().getLeftChild()) {
                uncle = currentNode.getParent().getRightChild();
                if (uncle.getColor() == Color.RED) {
                    uncle.setColor(Color.BLACK);
                    currentNode.getParent().setColor(Color.RED);
                    leftRotate(currentNode.getParent());
                    uncle = currentNode.getParent().getRightChild();
                }
                if (uncle.getLeftChild().getColor() == Color.BLACK &&
                        uncle.getRightChild().getColor() == Color.BLACK) {
                    uncle.setColor(Color.RED);
                    currentNode = currentNode.getParent();
                } else {
                    if (uncle.getRightChild().getColor() == Color.BLACK) {
                        uncle.getLeftChild().setColor(Color.BLACK);
                        uncle.setColor(Color.RED);
                        rightRotate(uncle);
                        uncle = currentNode.getParent().getRightChild();
                    }
                    uncle.setColor(currentNode.getParent().getColor());
                    currentNode.getParent().setColor(Color.BLACK);
                    uncle.getRightChild().setColor(Color.BLACK);
                    leftRotate(currentNode.getParent());
                    currentNode = root;
                }
            } else {
                uncle = currentNode.getParent().getLeftChild();
                if (uncle.getColor() == Color.RED) {
                    uncle.setColor(Color.BLACK);
                    currentNode.getParent().setColor(Color.RED);
                    rightRotate(currentNode.getParent());
                    uncle = currentNode.getParent().getLeftChild();
                }
                if (uncle.getRightChild().getColor() == Color.BLACK &&
                        uncle.getLeftChild().getColor() == Color.BLACK) {
                    uncle.setColor(Color.RED);
                    currentNode = currentNode.getParent();
                } else {
                    if (uncle.getLeftChild().getColor() == Color.BLACK) {
                        uncle.getRightChild().setColor(Color.BLACK);
                        uncle.setColor(Color.RED);
                        leftRotate(uncle);
                        uncle = currentNode.getParent().getLeftChild();
                    }
                    uncle.setColor(currentNode.getParent().getColor());
                    currentNode.getParent().setColor(Color.BLACK);
                    uncle.getLeftChild().setColor(Color.BLACK);
                    rightRotate(currentNode.getParent());
                    currentNode = root;
                }
            }
        }
        currentNode.setColor(Color.BLACK);
    }

    public void clear() {
        root = nil;
    }

    private void recolor(Node node) {
        if (node.getColor() == Color.RED) node.setColor(Color.BLACK);
        else node.setColor(Color.RED);
    }

    public boolean isNil(Node node) {
        return node == nil;
    }
}
