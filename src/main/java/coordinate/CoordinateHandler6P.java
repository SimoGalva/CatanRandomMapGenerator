package coordinate;

import hexagon.HexagonPoint;
import utils.logging.LoggingClassesEnum;
import utils.logging.SyncedLogger;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;

public class CoordinateHandler6P extends AbstractCoordinateHandler{
    private static final Logger logger = SyncedLogger.getLogger(LoggingClassesEnum.COORDINATE_HANDLER_6P);

    @Override
    public HexagonPoint pickRandomPoint(boolean onBorderAllowed) {
        Random random = new Random();
        int rowCoord;
        int diagCoord;
        if (onBorderAllowed){
            diagCoord = random.nextInt(11) - 5;
            rowCoord = random.nextInt(7) - 3;
        } else {
            diagCoord = random.nextInt(9) - 4;
            rowCoord = random.nextInt(5) - 2;
        }
        logger.info("pickRandomPoint: random hexagonal point generated ["+diagCoord+":"+rowCoord+"] and isOnBorder["+onBorderAllowed+"]");
        return new HexagonPoint(diagCoord,rowCoord);
    }

    public static final CoordinateHandler6P getInstance() {
        return singletonInstance != null ? (CoordinateHandler6P) singletonInstance : getInstanceSafely();
    }

    private static CoordinateHandler6P getInstanceSafely() {
        if (singletonInstance == null) {
            singletonInstance = new CoordinateHandler6P();
        }
        return (CoordinateHandler6P) singletonInstance;
    }

    private CoordinateHandler6P() {
        //important: [5:0], [-5:0] non devono essere nelle liste
        this.allCoord = new ArrayList<>();
        this.availableCoord = new ArrayList<>();
        this.usedCoord = new ArrayList<>();
        for (int i = -5; i <= 5; i++) {
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
                } else if (i == -3 && j > -3) {
                    allCoord.add(i + ":" + j);
                    availableCoord.add(i + ":" + j);
                } else if (i == -4 && j > -2) {
                    allCoord.add(i + ":" + j);
                    availableCoord.add(i + ":" + j);
                } else if (i == -5 && j > 0) {
                    allCoord.add(i + ":" + j);
                    availableCoord.add(i + ":" + j);
                } else if (i >= -2 && i <= 2) {
                    allCoord.add(i + ":" + j);
                    availableCoord.add(i + ":" + j);
                }
            }
        }
    }
}
