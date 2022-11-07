package barbatos_rex1.structs;

import barbatos_rex1.utils.FileLogger;
import barbatos_rex1.utils.LoggingLevels;

/**
 * <p><b>Searchable AVL</b></p>
 *
 * <p><b>IMPORTANT: EVEN THOUGH THE NAME HAS BST, IT IS AN AVL</b></p>
 *
 * <p>A simple wrapper for the AVL that allows for parallel searching</p>
 * @param <T> The element that is to be indexed
 */
public class SearchableBST<T extends Comparable<T>> extends AVL<T> {
    //For logging purposes, can be removed without consequences (If SEARCHER_LOGGER is also removed)
    private static FileLogger LOGGER = FileLogger.get(SearchableBST.class);

    /**
     * <p><b>Searcher class</b></p>
     *
     *
     * <p>The objective of this class is defining the search engine of the SearchableBST, in other words, this is the responsible
     * fot the search mechanism in SearchableBST</p>
     * <p>It implements runnable for optimizations purposes with Threads, with can be altered</p>
     *
     * @param <T> The same type of the SearchableBST
     */
    private static class Searcher<T extends Comparable<T>> implements Runnable {
        // For logging purposes, can be removed without consequences, (If SEARCHER_LOGGER is also removed)
        private static FileLogger SEARCHER_LOGGER = FileLogger.get(Searcher.class, LoggingLevels.DEBUG, LOGGER);
        // Current node information for search comparison and logic comparison (aka verify if null and cascade to children)
        private Node<T> node;
        // Reference to new SearchableTree
        private SearchableBST<T> resultTree;
        // Clause for searching, for search comparison with node and predefined values
        private SearchClause<T> searchClause;

        public Searcher(Node<T> node, SearchableBST<T> resultTree, SearchClause<T> searchClause) {
            this.node = node;
            this.resultTree = resultTree;
            this.searchClause = searchClause;
        }

        /**
         * <p><b>Method to be called when a search is requested</b></p>
         *
         * <p></p>
         *
         */
        @Override
        public void run() {
            //Verifies if there is any more tree
            if (node == null) {
                return;
            }
            //Calls searchClause to see if element matches with wanted criteria and if so, adds to new tree
            if (searchClause.test(node.getElement())) {
                resultTree.insert(node.getElement());
            }

            //Prepares the call for left and right search
            Thread tleft = new Thread(new Searcher<>(node.getLeft(), resultTree, searchClause));
            Thread tright = new Thread(new Searcher<>(node.getRight(), resultTree, searchClause));
            //Calls the left and right search
            tleft.start();
            tright.start();
            try {
                //Join threads for waiting results and limit overhang on CPU usage (and to a less extent limit zombie threads creation)
                tleft.join();
                tright.join();
            } catch (InterruptedException e) {// If oppsie happens because "taking to long"
                SEARCHER_LOGGER.error("Could not join threads, results may be skewed",e);
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * <p><b>Method used for searching in the AVL</b></p>
     *
     * <p>Requires a search clause to effectively search on tree.</p>
     * <br>
     * <p>Again, Thread logic can be removed for sequential use, increasing the search time, but not impacting the complexity</p>
     *
     * <br>
     * <p><b>Method complexity: O(n)</b></p>
     * <p>This complexity happens because the Thread will iterate over all nodes in AVL, checking with match with the wanted criteria, adding to new tree</p>
     * @param searchClause The clause to search
     * @return A new SearchableTree with all the information that matches the searchCriteria
     */
    public SearchableBST<T> search(SearchClause<T> searchClause){
        //Creates new tree, not destroying the first tree
        SearchableBST<T> result = new SearchableBST<>();
        // Initializes first call to root
        Thread t = new Thread(new Searcher<>(root(),result,searchClause),"SearchableBST.search.root");
        // Calls for root
        t.start();
        try {
            //Join threads for waiting results (being necessary here!)
            t.join();
            return result;
        } catch (InterruptedException e) { // If oppsie happens because "taking to long"
            LOGGER.error("Could not search, results may be skewed",e);
            Thread.currentThread().interrupt();
        }
        //Returns new Tree
        return result;
    }

    /**
     * Insert wrapper for AVL.insert(), that is Thread safe, can be removed if Thread safety is not priority (Search strategy is changed)
     * @param element The element to insert
     */
    @Override
    public void insert(T element) {
        synchronized (SearchableBST.class) {
            super.insert(element);
        }
    }
}
