package hexagon.number;

import hexagon.material.Materials;
import utils.logging.LoggingClassesEnum;
import utils.logging.SyncedLogger;

import java.util.Arrays;
import java.util.Random;
import java.util.logging.Logger;

public class NumberHandler {
    private static final Logger logger = SyncedLogger.getLogger(LoggingClassesEnum.NUMBER_HANDLER);

    public Numbers pickRandomNumber(Materials material) {
        if (material == null) {
            logger.warning("pickRandomNumber: material is null. Returning null.");
            return null;
        }
        logger.info("pickRandomNumber: generating a number for ["+material.toString()+"]");
        if (Arrays.asList(Materials.DESERT, Materials.WATER).contains(material)) {
            return Numbers.M_ONE;
        } else {
            Random random = new Random();
            Numbers number = Numbers.fromInt(random.nextInt(12)+1);
            while(number == null) {
                number = Numbers.fromInt(random.nextInt(12)+1);
            }
            return number;
        }
    }
}
