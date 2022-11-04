package barbatos_rex1.structs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * @author DEI-ESINF
 */
public class TREE_WORDS extends BST<TextWord> {

    public void createTree() throws FileNotFoundException {
        Scanner readfile = new Scanner(new File("src/PL/xxx.xxx"));
        while (readfile.hasNextLine()) {
            String[] pal = readfile.nextLine().split("(\\,)|(\\s)|(\\.)");
            for (String word : pal)
                if (word.length() > 0)
                    insert(new TextWord(word, 1));
        }
        readfile.close();
    }

    /**
     * Inserts a new word in the tree, or increments the number of its occurrences.
     *
     * @param element
     */
    @Override
    public void insert(TextWord element) {
        root = insert(element, root);
    }

    private Node<TextWord> insert(TextWord element, Node<TextWord> node) {
        if (node == null) {
            return new Node<>(element, null, null);
        }

        if (node.getElement().compareTo(element) == 0) {
            node.getElement().incOcorrences();
            return node;
        }

        if (node.getElement().compareTo(element) < 0) {
            node.setRight(insert(element, node.getRight()));

        }

        if (node.getElement().compareTo(element) > 0) {
            node.setLeft(insert(element, node.getLeft()));
        }
        return node;
    }

    /**
     * Returns a map with a list of words for each occurrence found.
     *
     * @return a map with a list of words for each occurrence found.
     */
    public Map<Integer, List<String>> getWordsOccurrences() {
        Map<Integer, List<String>> map = new HashMap<>();
        wordOccurrencesGenerator(root, map);
        return map;
    }

    protected void wordOccurrencesGenerator(Node<TextWord> node, Map<Integer, List<String>> map) {
        if (node == null) {
            return;
        }

        if (!map.containsKey(node.getElement().getOcorrences())) {
            map.put(node.getElement().getOcorrences(), new LinkedList<>());
        }
        map.get(node.getElement().getOcorrences()).add(node.getElement().getWord());

        wordOccurrencesGenerator(node.getLeft(), map);
        wordOccurrencesGenerator(node.getRight(), map);

    }


}
