package hexagon.number;

import java.util.logging.Logger;

public enum Numbers {
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    EIGHT(8),
    NINE(9),
    TEN(10),
    ELEVEN(11),
    TWELVE(12),
    M_ONE(-1);

    private int numberInt;
    private static final Logger logger = Logger.getLogger(Numbers.class.getName());

    Numbers(int numberInt) {
        this.numberInt = numberInt;
    }

    public static Numbers fromInt(int number) {
        switch (number) {
            case -1:
                return Numbers.M_ONE;
            case 2:
                return Numbers.TWO;
            case 3:
                return Numbers.THREE;
            case 4:
                return Numbers.FOUR;
            case 5:
                return Numbers.FIVE;
            case 6:
                return Numbers.SIX;
            case 8:
                return Numbers.EIGHT;
            case 9:
                return Numbers.NINE;
            case 10:
                return Numbers.TEN;
            case 11:
                return Numbers.ELEVEN;
            case 12:
                return Numbers.TWELVE;
            default:
                logger.warning("Numbers.fromInt: invalid input number ["+number+"]. Returning null.");
                return null;
        }
    }

    public Numbers fromString(String number) {
        Numbers ret =null;
        try {
            ret = Numbers.valueOf(number);
        } catch (Exception e) {
            if ("SEVEN".equals(number)) {
                logger.warning("fromString: SEVEN is not an accepted number. Returning null.");
                return null;
            }
            logger.warning("fromString: no number with such a name ["+number+"]. Returning null");
        }
        return ret;
    }
}
