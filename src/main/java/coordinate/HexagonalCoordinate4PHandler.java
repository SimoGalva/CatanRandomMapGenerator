package coordinate;

import hexagon.HexagonPoint;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;

public class HexagonalCoordinate4PHandler {
    private final Logger logger = Logger.getLogger(getClass().getName());

    public final static int distance = 3;

    private final ArrayList<String> allCoord;
    private final ArrayList<String> availableCoord;
    private final ArrayList<String> usedCoord;

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

    public HexagonalCoordinate4PHandler() {
        this.allCoord = new ArrayList<>();
        this.availableCoord = new ArrayList<>();
        this.usedCoord = new ArrayList<>();
        for (int i = -4; i <= 4; i++) {
            for (int j = -3; j <= 3; j++) {
                if (i == 2 && j < 3) {
                    allCoord.add(i + ":" + j);
                    availableCoord.add(i + ":" + j);
                } else if (i == 3 && j < 2) {
                    allCoord.add(i + ":" + j);
                    availableCoord.add(i + ":" + j);
                } else if (i == 4 && j < 0) {
                    allCoord.add(i + ":" + j);
                    availableCoord.add(i + ":" + j);
                } else if (i == -2 && j > -3) {
                    allCoord.add(i + ":" + j);
                    availableCoord.add(i + ":" + j);
                } else if (i == -3 && j > -2) {
                    allCoord.add(i + ":" + j);
                    availableCoord.add(i + ":" + j);
                } else if (i == -4 && j > 0) {
                    allCoord.add(i + ":" + j);
                    availableCoord.add(i + ":" + j);
                } else if (i >= -1 && i <= 1) {
                    allCoord.add(i + ":" + j);
                    availableCoord.add(i + ":" + j);
                }
            }
        }
    }

    public boolean consumeCoord(HexagonPoint hexPoint) {
        int x = hexPoint.getDiagHexCoord();
        int y = hexPoint.getRowHexCoord();
        if (availableCoord.contains(x + ":" + y)) {
            usedCoord.add(x + ":" + y);
            availableCoord.remove(x + ":" + y);
            availableCoord.trimToSize();
        } else {
            logger.info("consumeCoord: cannot consume the selected coordinate ["+x + ":" + y+"].");
            return false;
        }
        logger.info("consumeCoord: selected coordinate ["+x + ":" + y+"] was consumed correctly.");
        return true;
    }

    public boolean checkCenterDisance(HexagonPoint point) {
        logger.info("checkCenterDisance: checking that the generated center is enough distant from the others.");
        boolean ret = true;
        //decomposizione del point
        String[] pointSplitted = point.toString().split(":");
        int diagCoordPoint = Integer.parseInt(pointSplitted[0]);
        int rowCoordPoint = Integer.parseInt(pointSplitted[1]);

        if (usedCoord.isEmpty()) {
            ret = true;
        } else {
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

    public HexagonPoint pickRandomPoint(boolean onBorderAllowed) {
        Random random = new Random();
        int rowCoord;
        int diagCoord;
        if (onBorderAllowed){
            diagCoord = random.nextInt(9) - 4;
            rowCoord = random.nextInt(7) - 3;
        } else {
            diagCoord = random.nextInt(7) - 3;
            rowCoord = random.nextInt(5) - 2;
        }
        logger.info("pickRandomPoint: random hexagonal point generated ["+diagCoord+":"+rowCoord+"] and isOnBorder["+onBorderAllowed+"]");
        return new HexagonPoint(diagCoord,rowCoord);
    }

    public int calculatePointerDimesnsion(HexagonPoint point) {
        int x = point.getDiagHexCoord();
        int y = point.getRowHexCoord();
        if (Math.abs(x) < 4 && Math.abs(y) < 3 && !(Math.abs(x) == 3 && y == 0)) {
            return 6;
        } else if (Math.abs(x) == 3 && y == 0) {
            return 5;
        } else if ((Math.abs(x) == 4 && Math.abs(y) == 3)
                    || (x == -1 && y == -3)
                    || (x == 1 && y == 3)
                    || (x == 3 && y == 1)
                    || (x == 4 && y == -1)
                    || (x == -3 && y == -1)
                    || (x == -4 && y == 1)) {
            return 3;
        } else {
            return 4;
        }
    }

}
