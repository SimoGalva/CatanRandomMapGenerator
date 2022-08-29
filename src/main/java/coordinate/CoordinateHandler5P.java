package coordinate;

import hexagon.HexagonPoint;
import utils.logging.LoggingClassesEnum;
import utils.logging.SyncedLogger;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;

public class CoordinateHandler5P extends AbstractCoordinateHandler{
    private static final Logger logger = SyncedLogger.getLogger(LoggingClassesEnum.COORDINATE_HANDLER_5P);

    @Override
    public HexagonPoint pickRandomPoint(boolean onBorderAllowed) {
        Random random = new Random();
        int rowCoord;
        int diagCoord;
        if (onBorderAllowed){
            diagCoord = random.nextInt(10) - 4;
            rowCoord = random.nextInt(7) - 3;
        } else {
            diagCoord = random.nextInt(8) - 3;
            rowCoord = random.nextInt(5) - 2;
        }
        logger.info("pickRandomPoint: random hexagonal point generated ["+diagCoord+":"+rowCoord+"] and isOnBorder["+onBorderAllowed+"]");
        return new HexagonPoint(diagCoord,rowCoord);
    }

    public static final CoordinateHandler5P getInstance() {
        return singletonInstance != null ? (CoordinateHandler5P) singletonInstance : getInstanceSafely();
    }

    private static CoordinateHandler5P getInstanceSafely() {
        if (singletonInstance == null) {
            singletonInstance = new CoordinateHandler5P();
        }
        return (CoordinateHandler5P) singletonInstance;
    }

    private CoordinateHandler5P() {
        //important: [5:0], [-4:0] non devono essere nelle liste
        this.allCoord = new ArrayList<>();
        this.availableCoord = new ArrayList<>();
        this.usedCoord = new ArrayList<>();
        for (int i = -4; i <= 5; i++) {
            for (int j = -3; j <= 3; j++) {
                if (i == 3 && j < 3) {
                    allCoord.add(i + ":" + j);
                    availableCoord.add(i + ":" + j);
                } else if (i == 4 && j < 2) {
                    allCoord.add(i + ":" + j);
                    availableCoord.add(i + ":" + j);
                } else if (i == 5 && j < 0) {
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
                } else if (i >= -1 && i <= 2) {
                    allCoord.add(i + ":" + j);
                    availableCoord.add(i + ":" + j);
                }
            }
        }
    }
}
