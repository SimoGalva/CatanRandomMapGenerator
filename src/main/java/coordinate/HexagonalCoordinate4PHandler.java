package coordinate;

import hexagon.HexagonPoint;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;

public class HexagonalCoordinate4PHandler {
    private final Logger logger = Logger.getLogger(getClass().getName());

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

    public HexagonalCoordinate4PHandler() {
        this.allCoord = new ArrayList<>();
        this.availableCoord = new ArrayList<>();
        this.usedCoord = new ArrayList<>();
        for (int i = -4; i <= 4; i++) {
            for (int j = -3; j <= 3; j++) {
                allCoord.add(i + ":" + j);
                availableCoord.add(i + ":" + j);
            }
        }
    }

    public boolean consumeCoord(HexagonPoint hexPoint) {
        int x = hexPoint.getRowHexCoord();
        int y = hexPoint.getDiagHexCoord();
        if (availableCoord.contains(x + ":" + y)) {
            usedCoord.add(x + ":" + y);
            availableCoord.remove(x + ":" + y);
            availableCoord.trimToSize();
        } else {
            logger.info("consumeCoord: cannot consume the selected coordinate ["+x + ":" + y+"], it's already used.");
            return false;
        }
        logger.info("consumeCoord: selected coordinate ["+x + ":" + y+"] was consumed correctly.");
        return true;
    }

    public HexagonPoint pickRandomPoint(boolean onBorderAllowed) {
        Random random = new Random();
        int rowCoord;
        int diagCoord;
        if (onBorderAllowed){
            rowCoord = random.nextInt(9) - 4;
            diagCoord = random.nextInt(7) - 3;
        } else {
            rowCoord = random.nextInt(7) - 3;
            diagCoord = random.nextInt(5) - 2;
        }
        logger.info("pickRandomPoint: random hexagonal point generated ["+rowCoord+":"+diagCoord+"] and isOnBorder["+onBorderAllowed+"]");
        return new HexagonPoint(rowCoord,diagCoord);
    }


}
