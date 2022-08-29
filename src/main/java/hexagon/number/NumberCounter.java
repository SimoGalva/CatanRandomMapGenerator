package hexagon.number;

import utils.exceptions.NoInstanceException;
import utils.logging.LoggingClassesEnum;
import utils.logging.SyncedLogger;

import java.util.EnumMap;
import java.util.Map;
import java.util.logging.Logger;

public class NumberCounter {
    private static final Logger logger = SyncedLogger.getLogger(LoggingClassesEnum.NUMBER_COUNTER);

    private int COUNTER_TWO;
    private int COUNTER_THREE;
    private int COUNTER_FOUR;
    private int COUNTER_FIVE;
    private int COUNTER_SIX;
    private int COUNTER_EIGHT;
    private int COUNTER_NINE;
    private int COUNTER_TEN;
    private int COUNTER_ELEVEN;
    private int COUNTER_TWELVE;
    private int COUNTER_M_ONE;

    private EnumMap<Numbers, Integer> numbersMap;

    private NumberCounter(int numberOfPlayer) {
        this.setConfig(numberOfPlayer);
        numbersMap = new EnumMap<Numbers, Integer>(Numbers.class);
        numbersMap.put(Numbers.TWO, COUNTER_TWO);
        numbersMap.put(Numbers.THREE, COUNTER_THREE);
        numbersMap.put(Numbers.FOUR, COUNTER_FOUR);
        numbersMap.put(Numbers.FIVE, COUNTER_FIVE);
        numbersMap.put(Numbers.SIX, COUNTER_SIX);
        numbersMap.put(Numbers.EIGHT, COUNTER_EIGHT);
        numbersMap.put(Numbers.NINE, COUNTER_NINE);
        numbersMap.put(Numbers.TEN, COUNTER_TEN);
        numbersMap.put(Numbers.ELEVEN, COUNTER_ELEVEN);
        numbersMap.put(Numbers.TWELVE, COUNTER_TWELVE);
        numbersMap.put(Numbers.M_ONE, COUNTER_M_ONE);
    }

    private void setConfig(int numberOfPlayer) {
        switch (numberOfPlayer) {
            case 3:
            case 4:
                COUNTER_TWO = 1;
                COUNTER_THREE = 3;
                COUNTER_FOUR = 3;
                COUNTER_FIVE = 3;
                COUNTER_SIX = 3;
                COUNTER_EIGHT = 3;
                COUNTER_NINE = 3;
                COUNTER_TEN = 3;
                COUNTER_ELEVEN = 3;
                COUNTER_TWELVE = 2;
                COUNTER_M_ONE = 22;
                break;
            case 5:
            case 6:
                COUNTER_TWO = 3;
                COUNTER_THREE = 4;
                COUNTER_FOUR = 4;
                COUNTER_FIVE = 4;
                COUNTER_SIX = 4;
                COUNTER_EIGHT = 4;
                COUNTER_NINE = 4;
                COUNTER_TEN = 4;
                COUNTER_ELEVEN = 4;
                COUNTER_TWELVE = 3;
                COUNTER_M_ONE = 30;
                break;
            default:
                COUNTER_TWO = 0;
                COUNTER_THREE = 0;
                COUNTER_FOUR = 0;
                COUNTER_FIVE = 0;
                COUNTER_SIX = 0;
                COUNTER_EIGHT = 0;
                COUNTER_NINE = 0;
                COUNTER_TEN = 0;
                COUNTER_ELEVEN = 0;
                COUNTER_TWELVE = 0;
                COUNTER_M_ONE = 0;
                break;
        }
    }

    public boolean consumeNumber(Numbers number) {
        if (number == null) {
            logger.warning("consumeNumber: number is null. Returning false.");
            return false;
        }
        if (numbersMap.get(number) > 0) {
            numbersMap.put(number, numbersMap.get(number) - 1);
            logger.info("consumeNumber: ["+number.toString()+"] has ["+ numbersMap.get(number)+"] pieces left.");
            return true;
        } else {
            logger.info("consumeNumber: ["+number.toString()+"] is over.");
            return false;
        }
    }

    public void printRemains() {
        StringBuilder byteString = new StringBuilder("getRemains: \n");
        for (Map.Entry<Numbers, Integer> entry : this.numbersMap.entrySet()) {
            byteString.append("- material ["+entry.getKey()+"] has ["+entry.getValue()+"] pieces left; \n");
        }
        logger.info(byteString.toString());
    }

    //implementazione singleton instance
    private static NumberCounter singletonInstance = null;

    public static NumberCounter getInstance() throws NoInstanceException {
        if (singletonInstance == null) {
            NoInstanceException exception = new NoInstanceException("NumberCounter.getInstance: " + NoInstanceException.MESSAGE);
            throw exception;
        }
        return singletonInstance;
    }

    public static NumberCounter createInstance(int numberOfPlayer) {
        return singletonInstance != null ? singletonInstance : getInstanceSafely(numberOfPlayer);
    }

    private static NumberCounter getInstanceSafely(int numberOfPlayer) {
        if (singletonInstance == null) {
            singletonInstance = new NumberCounter(numberOfPlayer);
        }
        return singletonInstance;
    }

    public static void clearSingletonInstance() {
        singletonInstance = null;
    }
}
