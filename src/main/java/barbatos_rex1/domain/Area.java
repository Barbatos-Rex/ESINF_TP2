package barbatos_rex1.domain;

import java.util.Objects;

public class Area {

    private String code;
    private double lat;
    private double lon;
    private String name;

    public Area(String code, double lat, double lon, String name) {
        this.code = code;
        this.lat = lat;
        this.lon = lon;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Area)) return false;
        Area area = (Area) o;
        return Objects.equals(code, area.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
