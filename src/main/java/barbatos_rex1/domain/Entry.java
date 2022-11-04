package barbatos_rex1.domain;

import java.util.Comparator;
import java.util.Map;

public class Entry implements Comparable<Entry> {

    private static Comparator<Entry> comparator;



    private final Area area;
    private final Product product;
    private final Flag flag;
    private final int year;
    private final int value;

    public Entry(Area area, Product product, Flag flag, int year, int value) {
        this.area = area;
        this.product = product;
        this.flag = flag;
        this.year = year;
        this.value = value;
    }

    public Area getArea() {
        return area;
    }

    public Product getProduct() {
        return product;
    }

    public Flag getFlag() {
        return flag;
    }

    public int getYear() {
        return year;
    }

    public int getValue() {
        return value;
    }

    @Override
    public int compareTo(Entry o) {
        return 0;
    }
}
