package hexagon.material;

import hexagon.number.Numbers;

import java.util.logging.Logger;

public enum Materials {
    RIVER(6),
    STONE(5),
    WOOD(4),
    CLAY(3),
    WOOL(2),
    HAY(1),
    DESERT(-1),
    WATER(-2);

    private int materialCode;
    private static final Logger logger = Logger.getLogger(Numbers.class.getName());

    Materials(int materialCode) { this.materialCode = materialCode; }

    public String toString() { return this.name(); }

    public static Materials fromString(String name) {
        return Materials.valueOf(name.toUpperCase());
    }

    public static Materials fromInt(int code) {
        switch (code) {
            case 6:
                return Materials.RIVER;
            case 5:
                return Materials.STONE;
            case 4:
                return Materials.WOOD;
            case 3:
                return Materials.CLAY;
            case 2:
                return Materials.WOOL;
            case 1:
                return Materials.HAY;
            case -1:
                return Materials.DESERT;
            case -2:
                return  Materials.WATER;
            default:
                logger.warning("fromInt: invalid material code ["+code+"]. Returning null.");
                return null;
        }
    }
}
