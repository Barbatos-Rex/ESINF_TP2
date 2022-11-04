package barbatos_rex1.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

public class FileLogger {
    private static final Map<String, FileLogger> loggers=new HashMap<>();
    private static String rootDirPath;
    private static LoggingLevels defaultLevel;
    private static boolean hasMasterLogger=true;
    private static FileLogger masterLogger;



    public static void init(Properties props){
        rootDirPath = props.getProperty("logger.rootDir","./log");
        defaultLevel = LoggingLevels.valueOf(props.getProperty("logger.defaultLevel","TRACE"));
        hasMasterLogger = Boolean.parseBoolean(props.getProperty("logger.hasMaster","true"));
        if (hasMasterLogger){
            masterLogger=new FileLogger("Master");
            loggers.put("_master_",masterLogger);
        }
    }

    public static void save(){
        File loggingDir = new File(String.format("%s/%s",rootDirPath,new SimpleDateFormat("HH_mm_ss").format(new Date())));
        loggingDir.mkdirs();
        loggers.values().forEach(logger -> persist(loggingDir,logger));
    }

    private static void persist(File loggingDir,FileLogger logger){
        File loggerFile = new File(String.format("%s/%s.log",loggingDir.getPath(),logger.loggerName));
        loggerFile.getParentFile().mkdirs();
        try(PrintWriter pw = new PrintWriter(loggerFile)){
            logger.buffer.forEach(pw::println);
        } catch (FileNotFoundException e) {
            System.err.println("Could not create logger file. Login in console instead.");
            logger.buffer.forEach(System.err::println);
        }
    }

    public static final FileLogger get(Class<?> rClass){
        return get(rClass,defaultLevel,masterLogger);
    }

    public static final FileLogger get(Class<?> rClass,LoggingLevels level){
        return get(rClass,level,masterLogger);
    }

    public static final FileLogger get(Class<?> rClass,LoggingLevels level,FileLogger parent){
        if (!loggers.containsKey(rClass.getCanonicalName())){
            FileLogger l = new FileLogger(rClass.getCanonicalName().replace(".","/"),level,parent);
            loggers.put(rClass.getCanonicalName(),l);
            return l;
        }
        return loggers.get(rClass.getCanonicalName());
    }





    private String loggerName;
    private LoggingLevels level;
    private FileLogger parent;
    private List<String> buffer;

    private FileLogger(String loggerName) {
        this(loggerName,defaultLevel,masterLogger);
    }

    private FileLogger(String loggerName, LoggingLevels level) {
        this(loggerName,level,masterLogger);
    }

    private FileLogger(String loggerName, LoggingLevels level, FileLogger parent) {
        this.loggerName = loggerName;
        this.level = level;
        this.parent = parent;
        this.buffer=new LinkedList<>();
    }

    private String formatMessage(String message, LoggingLevels level){
        return String.format("[%s][%s] %s",new SimpleDateFormat("HH:mm:ss:SSS").format(new Date()),level.name(),message);
    }

    private String formatMessage(String message, LoggingLevels level,Exception e){
        return String.format("[%s][%s] %s\n%s",new SimpleDateFormat("HH:mm:ss:SSS").format(new Date()),level.name(),message, Arrays.toString(e.getStackTrace()));
    }

    private void cascade(String message,LoggingLevels level){
        if (parent!=null){
            parent.parentLog(String.format("[%s]",this.loggerName)+message,level);
        }
    }

    private void parentLog(String s, LoggingLevels level) {
        if (this.level.getLevel()<=level.getLevel()){
            buffer.add( s);
            cascade(s,level);
        }
    }

    private void log(String message, LoggingLevels level) {
        if (this.level.getLevel()<=level.getLevel()){
            String formattedStr = formatMessage(message,level);
            buffer.add( formattedStr);
            cascade(formattedStr,level);
        }
    }

    private void log(String message, LoggingLevels level, Exception e) {
        if (this.level.getLevel()<=level.getLevel()){
            String formattedStr = formatMessage(message,level,e);
            buffer.add( formattedStr);
            cascade(formattedStr,level);
        }
    }

    public void trace(String message){
        log(message,LoggingLevels.TRACE);
    }

    public void trace(String message,Exception e){
        log(message,LoggingLevels.TRACE,e);
    }

    public void config(String message){
        log(message,LoggingLevels.CONFIG);
    }

    public void config(String message,Exception e){
        log(message,LoggingLevels.CONFIG,e);
    }

    public void debug(String message){
        log(formatMessage(message,LoggingLevels.CONFIG),LoggingLevels.CONFIG);
    }

    public void debug(String message,Exception e){
        log(message,LoggingLevels.DEBUG,e);
    }

    public void info(String message){
        log(message,LoggingLevels.INFO);
    }

    public void info(String message,Exception e){
        log(message,LoggingLevels.INFO,e);
    }

    public void warning(String message)
    {
        log(message,LoggingLevels.WARNING);;
    }
    public void warning(String message,Exception e){
        log(message,LoggingLevels.WARNING,e);
    }

    public void alert(String message){
        log(message,LoggingLevels.ALERT);
    }

    public void alert(String message,Exception e){
        log(message,LoggingLevels.ALERT,e);
    }

    public void error(String message){
        log(message,LoggingLevels.ERROR);
    }

    public void error(String message,Exception e){
        log(message,LoggingLevels.ERROR,e);
    }

    public void critical(String message){
        log(message,LoggingLevels.CRITICAL);
    }

    public void critical(String message,Exception e){
        log(message,LoggingLevels.CRITICAL,e);
    }

    public void fatal(String message){
        log(message,LoggingLevels.FATAL);
    }

    public void fatal(String message,Exception e){
        log(message,LoggingLevels.FATAL,e);
    }


}
