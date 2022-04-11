package engine;

import coordinate.HexagonalCoordinate4PHandler;
import globalMap.GlobalMapHandler;
import hexagon.HexagonPoint;
import hexagon.HexagonalBase;
import hexagon.material.MaterialCounter;
import hexagon.material.MaterialHandler;
import hexagon.material.Materials;
import hexagon.number.NumberCounter;
import hexagon.number.NumberHandler;
import hexagon.number.Numbers;
import island.IslandController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

public class MapGeneratorEngine {
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final GenerationHelper generationHelper;

    private HexagonalCoordinate4PHandler coordinateHandler;
    private MaterialHandler materialHandler;
    private NumberHandler numberHandler;
    private MaterialCounter materialCounter;
    private NumberCounter numberCounter;
    private int deepingCountPostMapping = 0;
    private static final int maxPostGeneratingFixing = 50;

    private static final String LAND = "LAND";
    private static final String LAND_NO_RIVER = "LAND_NO_R";
    private static final String LAND_SEA = "LAND_SEA";
    private static final String SEA = "SEA";

    public void setIslandHexPointCenter(IslandController controller) {
        HexagonPoint point;
        boolean isDoneGenerating = false;
        boolean isNearAlreadyGenerated = false;
        boolean isNearAlreadyGeneratedOverride = false;
        int nIter = 0;
        do {
            nIter ++;
            if (controller.isMainIsland()) {
                //pickRandomPoint è giustificato all'interno della classe HexagonalCoordinate4PHandler perchè la generazione random dipende strettamente dalla dimensione del tabellone,
                // ergo dal numero di giocatori, così posso eventualmente ricilcare più codice per 6 giocatori;
                point = coordinateHandler.pickRandomPoint(false);
            } else {
                point = coordinateHandler.pickRandomPoint(true);
            }
            if (GlobalMapHandler.getGlobalMap().isEmpty() || this.generationHelper.isNearIsland(point, GlobalMapHandler.getGlobalMap())) {
                isNearAlreadyGenerated = true;
            }
            else if (nIter > 100) {
                isNearAlreadyGeneratedOverride = true;
            }
            if (isNearAlreadyGenerated || isNearAlreadyGeneratedOverride) {
                isDoneGenerating = coordinateHandler.consumeCoord(point);
            }
        } while ((!isNearAlreadyGenerated && !isNearAlreadyGeneratedOverride) || !isDoneGenerating);
        logger.info("generateIslandHexPointCenter: random hexagonal point center of island generated ["+point.getDiagHexCoord()+":"+point.getRowHexCoord()+"]");
        controller.setIslandHexCenter(point);

        //POPOLAMENTO DEL CENTRO
        HexagonalBase cntrHex = this.generationHelper.generateHexagon(point, MaterialHandler.LAND);
        controller.populateMap(cntrHex);
        logger.info("generateIsland: island center hexagon generated correctly.");
    }

    public void generateIsland (IslandController controller) {
        HexagonalBase cntrHex = controller.getHexagonFromMap(controller.getIslandHexCenter());
        if (controller.getNumberOfHexagons() > 0) {
            this.generationHelper.generationThroughPointers(cntrHex, controller);
        } else {
            logger.severe("generateIsland: fatal error. Current island has [0] hexagons allowed.");
            return;
        }
    }

    public void fillIslandBorderSea(IslandController controller) {
        ArrayList<String> avialableCoord = (ArrayList<String>) coordinateHandler.getAvailableCoord().clone();
        HashMap<String, HexagonalBase> currentIslandMap = controller.getIslandMap();

        for (String coordEntry : avialableCoord) {
            HexagonPoint pointEntry = new HexagonPoint(coordEntry);
            if (generationHelper.isNearIsland(pointEntry, currentIslandMap)
                && materialCounter.consumeMaterial(Materials.WATER)
                && numberCounter.consumeNumber(Numbers.M_ONE)
                && coordinateHandler.consumeCoord(pointEntry)) {

                    HexagonalBase oceanBase = HexagonalBase.createInstance(Materials.WATER, Numbers.M_ONE, coordinateHandler.calculatePointerDimesnsion(pointEntry), pointEntry);
                    GlobalMapHandler.populateMap(oceanBase);
            }
        }
    }

    public void fillOcenan() {
        ArrayList<String> avialableCoord = (ArrayList<String>) coordinateHandler.getAvailableCoord().clone();
        for (String availableCoord : avialableCoord) {
            HexagonPoint currentPoint = new HexagonPoint(availableCoord);
            if (materialCounter.consumeMaterial(Materials.WATER)
                && numberCounter.consumeNumber(Numbers.M_ONE)
                && coordinateHandler.consumeCoord(currentPoint)) {

                HexagonalBase oceanBase = HexagonalBase.createInstance(Materials.WATER, Numbers.M_ONE, currentPoint);
                GlobalMapHandler.populateMap(oceanBase);
            }
        }
    }



    //implementazione singleton instance
    private static MapGeneratorEngine singletonInstance = null;

    private MapGeneratorEngine() {
        this.coordinateHandler = HexagonalCoordinate4PHandler.getInstance();
        this.materialHandler = new MaterialHandler();
        this.numberHandler = new NumberHandler();
        this.materialCounter = MaterialCounter.getInstance();
        this.numberCounter = NumberCounter.getInstance();
        this.generationHelper = new GenerationHelper();
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

    public static void clearSingletonInstance() {
        singletonInstance = null;
    }
}
