package barbatos_rex1.io;

import barbatos_rex1.domain.Area;
import barbatos_rex1.domain.Product;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ProductReader extends Reader<Map<Integer, Product>> {
    public ProductReader(String filePath) {
        super(filePath);
    }

    @Override
    public Map<Integer, Product> processFile() throws FileNotFoundException {
        Map<Integer, Product> result = new HashMap<>();
        Scanner sc = new Scanner(new File(filePath));
        int lineCounter = 2;
        while (sc.hasNextLine()) {
            try {
                String[] line = sc.nextLine().split(",");
                Product p = new Product(Integer.parseInt(line[0]), line[1], line[2]);
                result.put(p.getCode(), p);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            lineCounter++;
        }
        return result;
    }
}
