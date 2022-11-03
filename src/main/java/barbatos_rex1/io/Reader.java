package barbatos_rex1.io;

import java.io.FileNotFoundException;

public abstract class Reader<T> {

    protected String filePath;

    public Reader(String filePath) {
        this.filePath = filePath;
    }

    public abstract T processFile() throws FileNotFoundException;

}
