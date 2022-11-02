package pt.ipp.isep.dei.esinf.PL;


import pt.ipp.isep.dei.esinf.students_1190387_1190616.util.Pair;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Tree_Words_Extended extends TREE_WORDS {
    private Comparator<Integer> comparableMethod;

    public Tree_Words_Extended(Comparator<Integer> comparableMethod) {
        super();
        this.comparableMethod = comparableMethod;
    }

    public Tree_Words_Extended() {
        super();
    }

    @Override
    public Map<Integer, List<String>> getWordsOccurrences() {
        Map<Integer, List<String>> map = new TreeMap<>(comparableMethod);
        super.wordOccurrencesGenerator(root, map);
        return map;
    }

    public Pair<Pair<String, String>, Integer> diameterWithMostDistantNodes() {
        Pair<Diameter<TextWord>, Diameter<TextWord>> diameter = diameter(root);
        return new Pair<>(new Pair<>(diameter.getElem1().getElement().getWord(), diameter.getElem2().getElement().getWord()), diameter.getElem1().getRadius() + diameter.getElem2().getRadius());
    }

    private Pair<Diameter<TextWord>, Diameter<TextWord>> diameter(Node<TextWord> node) {
        if (node == null) {
            return null;
        }
        int heightL = height(node.getLeft());
        int heightR = height(node.getRight());

        if (heightL - heightR < 1 || heightL - heightR < -1) {

            Pair<Diameter<TextWord>, Diameter<TextWord>> diameterL = diameter(node.getLeft());
            Pair<Diameter<TextWord>, Diameter<TextWord>> diameterR = diameter(node.getRight());
            if (diameterL == null) {
                diameterL = new Pair<>(new Diameter<>(node.getElement(), 0), new Diameter<>(null, -1));
            }
            if (diameterR == null) {
                diameterR = new Pair<>(new Diameter<>(null, -1), new Diameter<>(node.getElement(), 0));
            }

            Diameter<TextWord> lTreated = greatestDistance(diameterL);
            Diameter<TextWord> rTreated = greatestDistance(diameterR);

            return new Pair<>(lTreated, rTreated);
        } else if (heightL > heightR) {
            return diameter(node.getLeft());
        } else {
            return diameter(node.getRight());
        }
    }

    private Diameter<TextWord> greatestDistance(Pair<Diameter<TextWord>, Diameter<TextWord>> diameter) {
        Diameter<TextWord> l = diameter.getElem1();
        Diameter<TextWord> r = diameter.getElem2();

        if (l.getElement() == null) {
            return r;
        }
        if (r.getElement() == null) {
            return l;
        }

        if (l.getRadius().compareTo(r.getRadius()) >= 0) {
            l.incrementRadius();
            return l;
        }
        r.incrementRadius();
        return r;
    }

    public TextWord getRoot() {
        return root().getElement();
    }


    private class Diameter<E extends Comparable<E>> {
        private E element;
        private Integer radius;

        public Diameter(E element, int radius) {
            this.element = element;
            this.radius = radius;
        }

        public Integer getRadius() {
            return radius;
        }

        public E getElement() {
            return element;
        }

        public void setElement(E element) {
            this.element = element;
        }

        public void incrementRadius() {
            radius++;
        }
    }

}
