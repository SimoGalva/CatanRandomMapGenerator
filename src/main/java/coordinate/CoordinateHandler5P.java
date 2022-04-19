package coordinate;

import hexagon.HexagonPoint;

import java.util.logging.Logger;

public class CoordinateHandler5P extends AbstractCoordinateHandler{
    private final Logger logger = Logger.getLogger(getClass().getName());

    @Override
    public HexagonPoint pickRandomPoint(boolean onBorderAllowed) {
        return null;
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
}
