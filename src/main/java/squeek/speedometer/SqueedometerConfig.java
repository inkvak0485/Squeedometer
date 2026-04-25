package squeek.speedometer;

import net.minecraftforge.common.ForgeConfigSpec;

public class SqueedometerConfig {
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.BooleanValue SHOW_BACKGROUND;
    public static final ForgeConfigSpec.BooleanValue SHOW_UNITS;
    public static final ForgeConfigSpec.IntValue PRECISION;
    public static final ForgeConfigSpec.IntValue X_POS;
    public static final ForgeConfigSpec.IntValue Y_POS;
    public static final ForgeConfigSpec.IntValue PADDING;
    public static final ForgeConfigSpec.IntValue MARGIN;
    public static final ForgeConfigSpec.ConfigValue<String> X_ALIGN;
    public static final ForgeConfigSpec.ConfigValue<String> Y_ALIGN;
    public static final ForgeConfigSpec.EnumValue<SpeedUnit> SPEED_UNIT;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.push("speedometer");

        SHOW_BACKGROUND = builder.comment("Draw dark background under HUD text")
                .define("drawBackground", true);
        SHOW_UNITS = builder.comment("Show unit text next to speed")
                .define("showUnits", true);
        PRECISION = builder.comment("Decimal precision of current speed")
                .defineInRange("precision", 2, 0, 5);
        X_POS = builder.defineInRange("xPos", 0, -10000, 10000);
        Y_POS = builder.defineInRange("yPos", 0, -10000, 10000);
        PADDING = builder.defineInRange("padding", 2, 0, 40);
        MARGIN = builder.defineInRange("margin", 4, 0, 400);
        X_ALIGN = builder.comment("left, center, right")
                .define("xAlign", "left");
        Y_ALIGN = builder.comment("top, middle, bottom")
                .define("yAlign", "bottom");
        SPEED_UNIT = builder.defineEnum("speedUnit", SpeedUnit.BLOCKS_PER_SECOND);

        builder.pop();
        SPEC = builder.build();
    }
}
