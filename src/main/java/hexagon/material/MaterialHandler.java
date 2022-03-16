package hexagon.material;

import hexagon.number.Numbers;

import java.util.Random;
import java.util.logging.Logger;

public class MaterialHandler {
    private static final Logger logger = Logger.getLogger(Numbers.class.getName());

    public static final String LAND = "LAND";
    public static final String LAND_WATER = "LAND_WATER";
    public static final String WATER = "WATER";
    public static final String LANDD = "LAND_AND_DESERT";

    public Materials pickRandomMaterial(String bound) {
        logger.info("pickRandomMaterial: generating random material with bound ["+bound.toUpperCase()+"].");
        Materials ret = null;
        Random random;
        switch (bound.toUpperCase()) {
            case LAND:
                random = new Random();
                ret = Materials.fromInt(random.nextInt(6)+1);
                break;
            case LANDD:
                random = new Random();
                ret = Materials.fromInt(random.nextInt(8)-1);
                break;
            case LAND_WATER:
                random = new Random();
                ret = Materials.fromInt(random.nextInt(9)-2);
                break;
            case WATER:
                ret = Materials.WATER;
                break;
            default:
                logger.warning("pickRandomMaterial: unacceptable bound ["+bound.toUpperCase()+"]. Returning null.");
                return null;
        }
        return ret;
    }
}
