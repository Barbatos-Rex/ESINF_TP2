package pt.ipp.isep.dei.esinf.PL;

import java.util.LinkedList;

public class CompleteBinaryTree<E extends Comparable<E>> extends BST<E> {
    /**
     * Método retirado de https://www.geeksforgeeks.org/check-if-a-given-binary-tree-is-complete-tree-or-not/ . Todos os créditos deste método devem sem atribuídos só, e apenas só, aos contribuintes da página apresentada
     *
     * @param tree Árvore Binária
     * @param <E>  Tipo específico da árvore
     * @return True se a árvore for completa e false se não.
     */

    public static <E extends Comparable<E>> boolean verifyIfBinaryTreeIsComplete(BST<E> tree) {
        if (tree.root == null) {
            return true;
        }

        LinkedList<Node<E>> bfsQueue = new LinkedList<>();
        boolean flag = false;

        bfsQueue.add(tree.root);

        while (!bfsQueue.isEmpty()) {

            Node<E> tmp_node = bfsQueue.remove();
            if (tmp_node.getLeft() != null) {
                if (flag) {
                    flag = false;
                }
                bfsQueue.add(tmp_node.getLeft());
            } else {
                flag = true;
            }

            if (tmp_node.getRight() != null) {
                if (flag) {
                    return false;
                }
                bfsQueue.add(tmp_node.getRight());
            } else {
                flag = true;
            }

        }

        return true;

    }

    //Breath First insert method
    @Override
    public void insert(E element) {
        if (root == null) {
            root = new Node<>(element, null, null);
            return;
        }

        LinkedList<Node<E>> queue = new LinkedList<>();
        queue.addLast(root);
        Node<E> node;
        do {
            node = queue.pop();
            Node<E> left = node.getLeft();
            Node<E> right = node.getRight();
            if (left == null) {
                node.setLeft(new Node<>(element, null, null));
                break;
            } else if (right == null) {
                node.setRight(new Node<>(element, null, null));
                break;
            }
            queue.addLast(left);
            queue.addLast(right);

        } while (true);
    }


}
