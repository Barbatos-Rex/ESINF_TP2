package barbatos_rex1;

import barbatos_rex1.structs.SearchClause;
import barbatos_rex1.structs.SearchableBST;
import barbatos_rex1.utils.FileLogger;

import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class Main {
    private final FileLogger LOGGER = FileLogger.get(Main.class);

    public void logAll() {
        LOGGER.trace("trace");
        LOGGER.config("config");
        LOGGER.debug("debug");
        LOGGER.info("info");
        LOGGER.warning("warning");
        LOGGER.alert("alert");
        LOGGER.error("error");
        LOGGER.critical("critical");
        LOGGER.fatal("fatal", new RuntimeException("Test Exception", new Exception()));
    }

    // Example of entity to be indexed by tree
    private static class TestClass implements Comparable<TestClass>{
        private String str;
        private int i;
        private double d;

        public TestClass(String str, int i, double d) {
            this.str = str;
            this.i = i;
            this.d = d;
        }

        public String getStr() {
            return str;
        }

        public int getI() {
            return i;
        }

        public double getD() {
            return d;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof TestClass)) return false;
            TestClass testClass = (TestClass) o;
            return i == testClass.i && Double.compare(testClass.d, d) == 0 && Objects.equals(str, testClass.str);
        }

        @Override
        public int hashCode() {
            return Objects.hash(str, i, d);
        }

        @Override
        public String toString() {
            return "TestClass{" +
                    "str='" + str + '\'' +
                    ", i=" + i +
                    ", d=" + d +
                    '}';
        }

        @Override
        public int compareTo(TestClass o) {
            int result=0;
            if ((result=str.compareToIgnoreCase(o.str))==0){
                if ((result=Integer.compare(i,o.i))==0){
                    return Double.compare(d,o.d);
                }
            }
            return result;
        }
    }
    // Example of search clause implementation
    private static class TestSearchingClause1 extends SearchClause<TestClass> {
        @Override
        public boolean matchCriteria(TestClass element) {
            return element.d>2.1 && element.i<3;
        }
    }

    // Example of search clause implementation
    private static class TestSearchingClause2 extends SearchClause<TestClass>{

        @Override
        public boolean matchCriteria(TestClass element) {
            return element.str.equalsIgnoreCase("A") || element.d==2.5;
        }
    }


    public static void main(String[] args) throws IOException {

        Properties props = System.getProperties();
        props.load(new FileReader("src/main/resources/application.properties"));
        FileLogger.init(props);
        String stat = "ABC";
        new Main().logAll();

        // SearchableTree creation
        SearchableBST<TestClass> tree = new SearchableBST<>();
        //Tree populate (Populates the tree with a controlled set of data)
        for (int i = 0; i < 5; i++) {
            for (double j = 0; j < 5; j+=.5) {
                for (int k = 0; k < stat.length(); k++) {
                    tree.insert(new TestClass(""+stat.charAt(k),i,j));
                }
            }
        }
        // Prints original tree for fairness and validation
        System.out.println(tree);
        // Break line for visualization proposes
        System.out.println();
        // Prints search results from comparison criteria 1 in tree form, remove comments for linear print
        System.out.println(tree.search(new TestSearchingClause1()/*.inOrder()*/));
        // Break line for visualization proposes
        System.out.println();
        // Prints search results from comparison criteria 1 in tree form, remove comments for linear print
        System.out.println(tree.search(new TestSearchingClause2()/*.inOrder()*/));




        FileLogger.save();

    }
}