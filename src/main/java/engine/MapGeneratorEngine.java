package engine;

import coordinate.HexagonalCoordinate4PHandler;
import hexagon.CentralHexagon;
import hexagon.HexagonPoint;
import hexagon.HexagonalBase;
import hexagon.material.Materials;
import hexagon.number.Numbers;
import island.IslandController;

import java.util.logging.Logger;

public class MapGeneratorEngine {
    private final Logger logger = Logger.getLogger(getClass().getName());

    private HexagonalCoordinate4PHandler coordinateHandler;
    private static final String LAND = "LAND";
    private static final String LAND_NO_RIVER = "LAND_NO_R";
    private static final String LAND_SEA = "LAND_SEA";
    private static final String SEA = "SEA";

    public void generateIslandHexPointCenter(IslandController controller) {
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
        controller.setIslandHexCenter(point);
    }

    public void generateIsland (IslandController controller) {
        HexagonPoint islandCntr = controller.getIslandHexCenter();
        int pointerCntrDim = coordinateHandler.calculatePointerDimesnsion(islandCntr);
        Materials materialCntr = MaterialHandler.pickRandomMaterial("LAND");
        Numbers numberCntr = NumberHandler.pickRandomNumber(materialCntr);
        HexagonalBase  cntrHex = new CentralHexagon(materialCntr, numberCntr, pointerCntrDim, islandCntr);
        //todo: riprendi da qui
    }


    //implementazione singleton instance
    private static MapGeneratorEngine singletonInstance = null;

    private MapGeneratorEngine() {
        this.coordinateHandler = new HexagonalCoordinate4PHandler();
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
