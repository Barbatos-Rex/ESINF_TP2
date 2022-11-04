package barbatos_rex1.utils;

public enum LoggingLevels {
    TRACE(0),
    CONFIG(1),
    DEBUG(2),
    INFO(3),
    WARNING(4),
    ALERT(5),
    ERROR(6),
    CRITICAL(7),
    FATAL(8);

    private final int level;

    LoggingLevels(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
