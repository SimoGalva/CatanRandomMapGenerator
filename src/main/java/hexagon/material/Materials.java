package hexagon.material;

import hexagon.number.Numbers;

import java.awt.*;
import java.util.logging.Logger;

public enum Materials {
    RIVER(6, new Color(0x84F1E1)),
    STONE(5, new Color(0x838288)),
    WOOD(4, new Color(0x095B02)),
    CLAY(3, new Color(0x813B03)),
    WOOL(2, new Color(0x7DE14B)),
    HAY(1, new Color(0xFFC400)),
    DESERT(-1, new Color(0xF5BF6E)),
    WATER(-2, new Color(0x0552C5));

    private int materialCode;
    private Color colorValue;

    private static final Logger logger = Logger.getLogger(Numbers.class.getName());

    Materials(int materialCode, Color colorValue) {
        this.materialCode = materialCode;
        this.colorValue = colorValue;
    }

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

    public Color getColorValue() {
        return colorValue;
    }
}
