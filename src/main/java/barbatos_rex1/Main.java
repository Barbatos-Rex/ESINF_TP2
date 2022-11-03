package barbatos_rex1;

import barbatos_rex1.utils.FileLogger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Main {
    private final FileLogger LOGGER = FileLogger.get(Main.class);

    public void logAll() {
        LOGGER.trace("trace");
        LOGGER.config("config");
        LOGGER.debug("debug");
        LOGGER.info("info");
        LOGGER.warning("warning");
        LOGGER.alert("alert");
        LOGGER.error("error");
        LOGGER.critical("critical");
        LOGGER.fatal("fatal", new RuntimeException("Test Exception", new Exception()));
    }


    public static void main(String[] args) throws IOException {

        Properties props = System.getProperties();
        props.load(new FileReader("src/main/resources/application.properties"));
        FileLogger.init(props);

        new Main().logAll();


        FileLogger.save();

    }
}