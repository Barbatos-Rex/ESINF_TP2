
package barbatos_rex1.structs;
import java.util.List;

/**
 * @author DEI-ESINF
 */
public class Utils {
    public static <E extends Comparable<E>> Iterable<E> sortByBST(List<E> listUnsorted) {
        BST<E> orderedTree = new BST<>();
        for (E element : listUnsorted) {
            orderedTree.insert(element);
        }
        return orderedTree.inOrder();
    }
}
