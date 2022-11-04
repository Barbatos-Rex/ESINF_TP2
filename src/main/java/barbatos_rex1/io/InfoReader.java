package barbatos_rex1.io;

import barbatos_rex1.domain.Entry;
import barbatos_rex1.structs.BST;

import java.io.FileNotFoundException;

public class InfoReader extends Reader<BST<Entry>>{

    public InfoReader(String filePath) {
        super(filePath);
    }

    @Override
    public BST<Entry> processFile() throws FileNotFoundException {
        return null;
    }
}
