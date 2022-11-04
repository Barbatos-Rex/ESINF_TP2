package barbatos_rex1.io;

import barbatos_rex1.domain.Area;
import barbatos_rex1.domain.Flag;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FlagReader extends Reader<Map<String, Flag>> {


    public FlagReader(String filePath) {
        super(filePath);
    }

    @Override
    public Map<String, Flag> processFile() throws FileNotFoundException {
        Map<String, Flag> result = new HashMap<>();
        Scanner sc = new Scanner(new File(filePath));
        int lineCounter = 2;
        sc.nextLine();
        while (sc.hasNextLine()) {
            try {
                String[] line = sc.nextLine().split(",");
                Flag f = new Flag(line[0], line[1]);
                result.put(f.getCode(), f);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            lineCounter++;
        }
        return result;
    }

}
