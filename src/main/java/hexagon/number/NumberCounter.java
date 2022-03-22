package hexagon.number;

import java.util.EnumMap;
import java.util.logging.Logger;

public class NumberCounter {
    private final Logger logger = Logger.getLogger(getClass().getName());

    private final int COUNTER_TWO = 1;
    private final int COUNTER_THREE = 3;
    private final int COUNTER_FOUR = 3;
    private final int COUNTER_FIVE = 3;
    private final int COUNTER_SIX = 3;
    private final int COUNTER_EIGHT = 3;
    private final int COUNTER_NINE = 3;
    private final int COUNTER_TEN = 3;
    private final int COUNTER_ELEVEN = 3;
    private final int COUNTER_TWELVE = 2;
    private final int COUNTER_M_ONE = 22;

    private EnumMap<Numbers, Integer> numbersMap;

    private NumberCounter() {
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

    //implementazione singleton instance
    private static NumberCounter singletonInstance = null;

    public static NumberCounter getInstance() {
        return singletonInstance != null ? singletonInstance : getInstanceSafely();
    }

    private static NumberCounter getInstanceSafely() {
        if (singletonInstance == null) {
            singletonInstance = new NumberCounter();
        }
        return singletonInstance;
    }
}
