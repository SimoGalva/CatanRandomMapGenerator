package coordinate;

import hexagon.HexagonPoint;

import java.util.logging.Logger;

public class CoordinateHandler6P extends AbstractCoordinateHandler{
    private final Logger logger = Logger.getLogger(getClass().getName());

    @Override
    public HexagonPoint pickRandomPoint(boolean onBorderAllowed) {
        return null;
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
}
