package coordinate;

import hexagon.HexagonPoint;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;

public class CoordinateHandler3P extends AbstractCoordinateHandler{
    private final Logger logger = Logger.getLogger(getClass().getName());

    @Override
    public HexagonPoint pickRandomPoint(boolean onBorderAllowed) {
        Random random = new Random();
        int rowCoord;
        int diagCoord;
        if (onBorderAllowed){
            diagCoord = random.nextInt(8) - 4;
            rowCoord = random.nextInt(7) - 3;
        } else {
            diagCoord = random.nextInt(6) - 3;
            rowCoord = random.nextInt(5) - 2;
        }
        logger.info("pickRandomPoint: random hexagonal point generated ["+diagCoord+":"+rowCoord+"] and isOnBorder["+onBorderAllowed+"]");
        return new HexagonPoint(diagCoord,rowCoord);
    }

    public static final CoordinateHandler3P getInstance() {
        return singletonInstance != null ? (CoordinateHandler3P) singletonInstance : getInstanceSafely();
    }

    private static CoordinateHandler3P getInstanceSafely() {
        if (singletonInstance == null) {
            singletonInstance = new CoordinateHandler3P();
        }
        return (CoordinateHandler3P) singletonInstance;
    }

    private CoordinateHandler3P() {
        //important: [3:0], [-4:0] non devono essere nelle liste
        this.allCoord = new ArrayList<>();
        this.availableCoord = new ArrayList<>();
        this.usedCoord = new ArrayList<>();
        for (int i = -3; i <= 4; i++) {
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
                } else if (i == -1 && j > -3) {
                    allCoord.add(i + ":" + j);
                    availableCoord.add(i + ":" + j);
                } else if (i == -2 && j > -2) {
                    allCoord.add(i + ":" + j);
                    availableCoord.add(i + ":" + j);
                } else if (i == -3 && j > 0) {
                    allCoord.add(i + ":" + j);
                    availableCoord.add(i + ":" + j);
                } else if (i >= 0 && i <= 1) {
                    allCoord.add(i + ":" + j);
                    availableCoord.add(i + ":" + j);
                }
            }
        }
    }
}
