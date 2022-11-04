package barbatos_rex1.domain;

import java.util.Objects;

public class Product {

    private int code;
    private String ccp;
    private String name;

    public Product(int code, String ccp, String name) {
        this.code = code;
        this.ccp = ccp;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getCcp() {
        return ccp;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return code == product.code;
    }

    @Override
    public int hashCode() {
        return code;
    }
}
