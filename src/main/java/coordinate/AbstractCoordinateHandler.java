package coordinate;

import hexagon.HexagonPoint;

import java.util.ArrayList;
import java.util.logging.Logger;

public abstract class AbstractCoordinateHandler {
    private static final Logger logger = Logger.getLogger("AbstractCoordinateHandler");

    public final static int distance = 3;

    protected ArrayList<String> allCoord;
    protected ArrayList<String> availableCoord;
    protected ArrayList<String> usedCoord;


    public ArrayList<String> getAllCoord() {
        return allCoord;
    }

    public ArrayList<String> getAvailableCoord() {
        return availableCoord;
    }

    public ArrayList<String> getUsedCoord() {
        return usedCoord;
    }

    public boolean existsCoordinate(HexagonPoint point){
        return allCoord.contains(point.toString());
    }

    public boolean consumeCoord(HexagonPoint hexPoint) {
        int x = hexPoint.getDiagHexCoord();
        int y = hexPoint.getRowHexCoord();
        if (availableCoord.contains(x + ":" + y)) {
            usedCoord.add(x + ":" + y);
            availableCoord.remove(x + ":" + y);
            availableCoord.trimToSize();
            logger.info("consumeCoord: selected coordinate ["+x + ":" + y+"] was consumed correctly.");
            return true;
        } else {
            logger.info("consumeCoord: cannot consume the selected coordinate ["+x + ":" + y+"].");
            return false;
        }
    }

    public int calculatePointerDimesnsion(HexagonPoint point){
        int dimensionCount = 0;
        int diagCoord = point.getDiagHexCoord();
        int rowCoord = point.getRowHexCoord();
        ArrayList<HexagonPoint> pointerCounter = new ArrayList<>();
        pointerCounter.add(new HexagonPoint(diagCoord +1, rowCoord));
        pointerCounter.add(new HexagonPoint(diagCoord, rowCoord + 1));
        pointerCounter.add(new HexagonPoint(diagCoord - 1, rowCoord + 1));
        pointerCounter.add(new HexagonPoint(diagCoord - 1, rowCoord));
        pointerCounter.add(new HexagonPoint(diagCoord, rowCoord -1));
        pointerCounter.add(new HexagonPoint(diagCoord + 1, rowCoord - 1));

        for (HexagonPoint pointToTest : pointerCounter) {
            if (allCoord.contains(pointToTest.toString())) {
                dimensionCount++;
            }
        }

        return dimensionCount;
    }

    public boolean checkCenterDistance(HexagonPoint point) {
        logger.info("checkCenterDisance: checking that the generated center is enough distant from the others.");
        boolean ret = true;
        //decomposizione del point
        String[] pointSplitted = point.toString().split(":");
        int diagCoordPoint = Integer.parseInt(pointSplitted[0]);
        int rowCoordPoint = Integer.parseInt(pointSplitted[1]);

        if (!usedCoord.isEmpty()) {
            for (String used : usedCoord) {
                String[] usedIntsAsString = used.split(":");
                int diagCoordUsed = Integer.parseInt(usedIntsAsString[0]);
                int rowCoordUsed = Integer.parseInt(usedIntsAsString[1]);
                if ((diagCoordPoint < diagCoordUsed + distance && diagCoordPoint > diagCoordUsed - distance)
                        && (rowCoordPoint < rowCoordUsed + distance && rowCoordPoint > rowCoordUsed - distance)) {
                    logger.info("checkCenterDisance: the selected coordinates ["+point.toString()+"] for center are too close to some other centers. Retrying generation.");
                    ret = false;
                    break;
                }
            }
        }
        return ret;
    }

    public abstract HexagonPoint pickRandomPoint(boolean onBorderAllowed);

    //implementazione singletonInstance
    protected static AbstractCoordinateHandler singletonInstance = null;
    protected static int numberOfPlayer = 0;

    public static AbstractCoordinateHandler getInstance(){
       return singletonInstance; //getInstance(numberOfPlayer);
    }

    public static AbstractCoordinateHandler getInstance(int numberOfPlayer) {
        if (numberOfPlayer == 0) {
            AbstractCoordinateHandler.numberOfPlayer = numberOfPlayer;
        }
        switch(numberOfPlayer) {
            case 3:
                return CoordinateHandler3P.getInstance();
            case 4:
                return CoordinateHandler4P.getInstance();
            case 5:
                return CoordinateHandler5P.getInstance();
            case 6:
                return CoordinateHandler6P.getInstance();
            default:
                AbstractCoordinateHandler.numberOfPlayer = 0;
                logger.info("getInstance: invalid number of player. Returning null.");
                return null;
        }
    }

    public static void clearSingletonInstance() {
        singletonInstance = null;
        AbstractCoordinateHandler.numberOfPlayer = 0;
    }
}
