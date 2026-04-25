package squeek.speedometer;

public enum SpeedUnit {
    BLOCKS_PER_TICK(1.0D, "bpt", "blocks/tick"),
    BLOCKS_PER_SECOND(0.05D, "bps", "blocks/sec"),
    METERS_PER_SECOND(0.05D, "m/s", "meters/sec"),
    KILOMETERS_PER_HOUR(0.0138889D, "km/h", "km/hour"),
    MILES_PER_HOUR(0.022352D, "mph", "miles/hour");

    private final double blocksPerTickInUnit;
    private final String shortLabel;
    private final String label;

    SpeedUnit(double blocksPerTickInUnit, String shortLabel, String label) {
        this.blocksPerTickInUnit = blocksPerTickInUnit;
        this.shortLabel = shortLabel;
        this.label = label;
    }

    public double convertFromBlocksPerTick(double value) {
        return value / blocksPerTickInUnit;
    }

    public String shortLabel() {
        return shortLabel;
    }

    @Override
    public String toString() {
        return label;
    }
}
