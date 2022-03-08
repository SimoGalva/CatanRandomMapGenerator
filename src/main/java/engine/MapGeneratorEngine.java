package engine;

import engine.utility.HexagonalCoordinate4PHandler;
import hexagon.HexagonPoint;
import hexagon.material.MaterialCounter;
import island.IslandController;

import java.util.logging.Logger;

public class MapGeneratorEngine {
    private final Logger logger = Logger.getLogger(getClass().getName());

    private MaterialCounter materialCounter;
    private HexagonalCoordinate4PHandler coordinateHandler;

    public HexagonPoint generateIslandHexPointCenter(IslandController controller) {
        HexagonPoint point;
        boolean isDoneGenerating = false;
        do {
            if (controller.isMainIsland()) {
                //pickRandomPoint è giustificato all'interno della classe HexagonalCoordinate4PHandler perchè la generazione random dipende strettamente dalla dimensione del tabellone,
                // ergo dal numero di giocatori, così posso eventualmente ricilcare più codice per 6 giocatori;
                point = coordinateHandler.pickRandomPoint(false);
            } else {
                point = coordinateHandler.pickRandomPoint(true);
            }
            isDoneGenerating = coordinateHandler.consumeCoord(point);
        } while (!isDoneGenerating);
        logger.info("pickRandomPoint: random hexagonal point center of island generated ["+point.getRowHexCoord()+":"+point.getDiagHexCoord()+"]");
        return point;
    }

    public void generateIsland (IslandController controller) {
        //TODO: genera l'isola senza restituire nulla: aggiorna il controller che automaticamente aggiorna l'isola.
    }

    //implementazione singleton instance
    private static MapGeneratorEngine singletonInstance = null;

    private MapGeneratorEngine() {
        this.coordinateHandler = new HexagonalCoordinate4PHandler();
        this.materialCounter = MaterialCounter.getInstance();
    }

    public static final MapGeneratorEngine getInstance() {
        return singletonInstance != null ? singletonInstance : getInstanceSafely();
    }

    private static MapGeneratorEngine getInstanceSafely() {
        if (singletonInstance == null) {
            singletonInstance = new MapGeneratorEngine();
        }
        return  singletonInstance;
    }
}
