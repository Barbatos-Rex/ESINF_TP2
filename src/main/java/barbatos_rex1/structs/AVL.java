/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipp.isep.dei.esinf.PL;

/**
 * @param <E>
 * @author DEI-ESINF
 */
public class AVL<E extends Comparable<E>> extends BST<E> {


    private int balanceFactor(Node<E> node) {


        if (node == null) return -1;

        return height(node.getRight()) - height(node.getLeft());

    }

    private Node<E> rightRotation(Node<E> node) {

        Node<E> leftson = node.getLeft();
        node.setLeft(leftson.getRight());
        leftson.setRight(node);
        node = leftson;

        return node;

    }

    private Node<E> leftRotation(Node<E> node) {

        Node<E> rightson = node.getRight();
        node.setRight(rightson.getLeft());
        rightson.setLeft(node);
        node = rightson;

        return node;

    }

    private Node<E> twoRotations(Node<E> node) {

        if (balanceFactor(node) < 0) {
            node.setLeft(leftRotation(node.getLeft()));
            node = rightRotation(node);
        } else {
            node.setRight(rightRotation(node.getRight()));
            node = leftRotation(node);
        }

        return node;
    }

    private Node<E> balanceNode(Node<E> node) {
        if (balanceFactor(node) >= -1 && balanceFactor(node) <= 1) return node;
        if (balanceFactor(node) > 0) {
            if (balanceFactor(node.getRight()) > 0 && balanceFactor(node) > 0) {
                node = leftRotation(node);
            } else {
                node = twoRotations(node);
            }
        } else {
            if (balanceFactor(node.getLeft()) < 0 && balanceFactor(node) < 0) {
                node = rightRotation(node);
            } else {
                node = twoRotations(node);
            }
        }

        return node;

    }

    @Override
    public void insert(E element) {
        root = insert(element, root, 0);
    }

    private Node<E> insert(E element, Node<E> node, int depth) {
        if (node == null)
            return new Node(element, null, null);
        if (node.getElement().compareTo(element) == 0)
            node.setElement(element);
        else if (element.compareTo(node.getElement()) < 0) {
            node.setLeft(insert(element, node.getLeft(), depth + 1));
            node = balanceNode(node);
        } else {
            node.setRight(insert(element, node.getRight(), depth + 1));
            node = balanceNode(node);
        }
        return node;
    }

    @Override
    public void remove(E element) {
        root = remove(element, root(), 0);
    }

    private Node<E> remove(E element, Node<E> node, int depth) {
        if (node == null) return null;
        if (node.getElement().compareTo(element) == 0) {

            if (node.getLeft() == null && node.getRight() == null) return null;
            if (node.getLeft() == null) return node.getRight();
            if (node.getRight() == null) return node.getLeft();
            E sma = smallestElement(node.getRight());
            node.setElement(sma);
            node.setRight(remove(sma, node.getRight(), depth + 1));
            node = balanceNode(node);

        } else if (element.compareTo(node.getElement()) < 0) {
            node.setLeft(remove(element, node.getLeft(), depth + 1));
            node = balanceNode(node);
        } else {
            node.setRight(remove(element, node.getRight(), depth + 1));
            node = balanceNode(node);
        }
        return node;
    }


    public boolean equals(Object otherObj) {

        if (this == otherObj)
            return true;

        if (otherObj == null || this.getClass() != otherObj.getClass())
            return false;

        AVL<E> second = (AVL<E>) otherObj;
        return equals(root, second.root);
    }

    public boolean equals(Node<E> root1, Node<E> root2) {
        if (root1 == null && root2 == null)
            return true;
        else if (root1 != null && root2 != null) {
            if (root1.getElement().compareTo(root2.getElement()) == 0) {
                return equals(root1.getLeft(), root2.getLeft())
                        && equals(root1.getRight(), root2.getRight());
            } else
                return false;
        } else return false;
    }

}