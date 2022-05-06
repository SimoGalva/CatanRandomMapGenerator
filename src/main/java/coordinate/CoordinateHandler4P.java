package coordinate;

import hexagon.HexagonPoint;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;

public class CoordinateHandler4P extends AbstractCoordinateHandler{
    private final Logger logger = Logger.getLogger(getClass().getName());

    @Override
    public HexagonPoint pickRandomPoint(boolean onBorderAllowed) {
        Random random = new Random();
        int rowCoord;
        int diagCoord;
        if (onBorderAllowed){
            diagCoord = random.nextInt(9) - 4;
            rowCoord = random.nextInt(7) - 3;
        } else {
            boolean isOnBorder;
             do {
                diagCoord = random.nextInt(7) - 3;
                rowCoord = random.nextInt(5) - 2;

                isOnBorder = (diagCoord == rowCoord && (diagCoord == 2 || diagCoord ==-2)) || (diagCoord == -3 && rowCoord == -1) || (diagCoord == 3 && rowCoord == 1);
             } while (isOnBorder);
        }
        logger.info("pickRandomPoint: random hexagonal point generated ["+diagCoord+":"+rowCoord+"] and isOnBorder["+onBorderAllowed+"]");
        return new HexagonPoint(diagCoord,rowCoord);
    }

    public static final CoordinateHandler4P getInstance() {
        return singletonInstance != null ? (CoordinateHandler4P) singletonInstance : getInstanceSafely();
    }

    private static CoordinateHandler4P getInstanceSafely() {
        if (singletonInstance == null) {
            singletonInstance = new CoordinateHandler4P();
        }
        return (CoordinateHandler4P) singletonInstance;
    }

    private CoordinateHandler4P() {
        //important: [4:0], [-4:0] non devono essere nelle liste
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

}
