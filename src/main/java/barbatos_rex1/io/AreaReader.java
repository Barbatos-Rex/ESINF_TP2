package barbatos_rex1.io;

import barbatos_rex1.domain.Area;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AreaReader extends Reader<Map<String, Area>> {
    public AreaReader(String filePath) {
        super(filePath);
    }

    @Override
    public Map<String, Area> processFile() throws FileNotFoundException {
        Map<String, Area> result = new HashMap<>();
        Scanner sc = new Scanner(new File(filePath));
        int lineCounter = 2;
        sc.nextLine();
        while (sc.hasNextLine()) {
            try {
                String[] line = sc.nextLine().split(",");
                Area a = new Area(line[0], Double.parseDouble(line[1]), Double.parseDouble(line[2]), line[3]);
                result.put(a.getCode(), a);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            lineCounter++;
        }
        return result;
    }
}
