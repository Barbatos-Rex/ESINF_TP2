package barbatos_rex1.domain;

import java.util.Objects;

public class Flag {
    private String code;
    private String description;

    public Flag(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Flag)) return false;
        Flag flag = (Flag) o;
        return Objects.equals(code, flag.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
