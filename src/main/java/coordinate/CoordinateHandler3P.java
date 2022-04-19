package coordinate;

import hexagon.HexagonPoint;

import java.util.logging.Logger;

public class CoordinateHandler3P extends AbstractCoordinateHandler{
    private final Logger logger = Logger.getLogger(getClass().getName());

    @Override
    public HexagonPoint pickRandomPoint(boolean onBorderAllowed) {
        return null;
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
}
