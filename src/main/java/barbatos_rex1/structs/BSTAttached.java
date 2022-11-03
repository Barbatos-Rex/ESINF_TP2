package barbatos_rex1.structs;

import java.util.LinkedList;

public class BSTAttached<E extends Comparable<E>> {
    private final BST<E> bst;
    private LinkedList<E> lastResult;

    public BSTAttached(BST<E> bst) {
        this.bst = bst;
        lastResult = null;

    }

    private void searcher(BST.Node<E> node, E min, Boundary bMin, E max, Boundary bMax, LinkedList<E> queue) {

        if (node == null) {
            return;
        }

        if (min != null) {
            if (node.getElement().compareTo(min) < 0) {
                searcher(node.getRight(), min, bMin, max, bMax, queue);
                return;
            }
            if (node.getElement().compareTo(min) == 0) {
                if (bMin == Boundary.INCLUDED) {
                    queue.add(node.getElement());
                }
                searcher(node.getRight(), min, bMin, max, bMax, queue);
                return;
            }
        }


        searcher(node.getLeft(), min, bMin, max, bMax, queue);

        if (max != null) {
            if (node.getElement().compareTo(max) > 0) {
                return;
            }

            if (node.getElement().compareTo(max) == 0) {
                if (bMax == Boundary.INCLUDED) {
                    queue.add(node.getElement());
                }
                return;
            }
            if (node.getElement().compareTo(max) < 0) {
                queue.add(node.getElement());
            }
        } else {
            queue.add(node.getElement());
        }
        searcher(node.getRight(), min, bMin, max, bMax, queue);
    }

    public LinkedList<E> getAllElementsBetween(E min, Boundary bMin, E max, Boundary bMax) {
        if (!verifyParameters(min, max)) {
            E tmp = min;
            min = max;
            max = tmp;
        }
        LinkedList<E> results = new LinkedList<>();
        searcher(bst.root, min, bMin, max, bMax, results);
        lastResult = results;
        return results;
    }

    public LinkedList<E> getAllElementsHigherThan(E min, Boundary b) {
        LinkedList<E> results = new LinkedList<>();
        searcher(bst.root, min, b, null, null, results);
        lastResult = results;
        return results;

    }

    public LinkedList<E> getAllElementsLowerThan(E max, Boundary b) {
        LinkedList<E> results = new LinkedList<>();
        searcher(bst.root, null, null, max, b, results);
        lastResult = results;
        return results;
    }

    public LinkedList<E> getLastResults() {
        return lastResult;
    }

    private boolean verifyParameters(E min, E max) {
        return min.compareTo(max) < 0;
    }


    public enum Boundary {
        INCLUDED,
        EXCLUDED
    }


}
