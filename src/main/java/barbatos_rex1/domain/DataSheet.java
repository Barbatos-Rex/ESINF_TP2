package barbatos_rex1.domain;

import java.util.Map;

public class DataSheet {

    private Map<String,Area> areaMap;
    private Map<String,Flag> flagMap;
    private Map<Integer,Product> productMap;

    public DataSheet(Map<String, Area> areaMap, Map<String, Flag> flagMap, Map<Integer, Product> productMap) {
        this.areaMap = areaMap;
        this.flagMap = flagMap;
        this.productMap = productMap;
    }

    public Map<String, Area> getAreaMap() {
        return areaMap;
    }

    public Map<String, Flag> getFlagMap() {
        return flagMap;
    }

    public Map<Integer, Product> getProductMap() {
        return productMap;
    }



}
