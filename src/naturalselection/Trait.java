package naturalselection;

public enum Trait {
    MAX_SIZE, SPEED, VIRILITY;
    public static final double DEFAULT_MAX_SIZE = 15;
    public static final double DEFAULT_SPEED = 0.25;
    public static final double DEFAULT_VIRILITY = 8;

    public static final double MAX_SIZE_VARIANCE = 1;
    public static final double SPEED_VARIANCE = 0.1;
    public static final double VIRILITY_VARIANCE = 0.5;
}
