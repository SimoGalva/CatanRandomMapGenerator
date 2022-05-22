package engine;

import coordinate.AbstractCoordinateHandler;
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
import utils.Utils;
import utils.exceptions.GenerationException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class MapGeneratorEngine {
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final GenerationHelper generationHelper;
    private final PostGenerationHelper postGenerationHelper;

    private AbstractCoordinateHandler coordinateHandler;
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

    public void setIslandHexPointCenter(IslandController controller) throws GenerationException {
        HexagonPoint point;
        boolean isDoneGenerating = false;
        boolean isNearAlreadyGenerated = true;
        boolean isNearAlreadyGeneratedOverride = true;
        boolean isOnBorderAllowedOverride = false; //quando ho troppe isole principali rispetto al numero di isole permette a un certo punto di prendere anche centri sul bordo, altrimenti si blocca
        int nIter = 0;
        do {
            nIter ++;
            if (controller.isMainIsland() && isOnBorderAllowedOverride) {
                //pickRandomPoint è giustificato all'interno della classe AbstractCoordinateHandler perchè la generazione random dipende strettamente dalla dimensione del tabellone,
                // ergo dal numero di giocatori, così posso eventualmente ricilcare più codice per 6 giocatori;
                point = coordinateHandler.pickRandomPoint(false);
            } else {
                point = coordinateHandler.pickRandomPoint(true);
            }
            if (GlobalMapHandler.getGlobalMap().isEmpty() || this.generationHelper.isNearIsland(point, GlobalMapHandler.getGlobalMap())) {
                isNearAlreadyGenerated = false;
            }
            else if (nIter > 3500) {
                isOnBorderAllowedOverride = true;
                isNearAlreadyGeneratedOverride = false;
            }
            if (!isNearAlreadyGenerated || !isNearAlreadyGeneratedOverride) {
                isDoneGenerating = coordinateHandler.consumeCoord(point);
            }
        } while (!isDoneGenerating);
        logger.info("generateIslandHexPointCenter: random hexagonal point center of island generated ["+point.getDiagHexCoord()+":"+point.getRowHexCoord()+"]");
        controller.setIslandHexCenter(point);

        //POPOLAMENTO DEL CENTRO
        HexagonalBase cntrHex;
        try {
             cntrHex = this.generationHelper.generateHexagon(point, MaterialHandler.LAND);
        } catch (GenerationException e) {
            logger.info("setIslandHexPointCenter: unable to generate center with bound ["+MaterialHandler.LAND+"]. " + e.getMessage() + " Tring with LANDD.");
            try {
                cntrHex = this.generationHelper.generateHexagon(point, MaterialHandler.LANDD);
            } catch (GenerationException ex) {
                logger.info("setIslandHexPointCenter: unable to generate center with bound ["+MaterialHandler.LANDD+"]. " + ex.getMessage() + " Tring with LAND_WATER.");
                cntrHex = this.generationHelper.generateHexagon(point, MaterialHandler.LAND_WATER);
            }
        }
        controller.populateMap(cntrHex);
        logger.info("generateIsland: island center hexagon generated correctly.");
    }

    public void generateIsland (IslandController controller) throws GenerationException {
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


    public void numberRuleChecking() {
        long t0 = System.currentTimeMillis();
        logger.info("postGeneratingFixing.numberRuleChecking: starting to fix near 6 and 8.");
        HashMap<String, HexagonalBase> globalMap = Utils.duplicateMap(GlobalMapHandler.getGlobalMap());
        HashMap<String, HexagonalBase> sixAndEightMap = new HashMap<>();
        //TODO: implementa la logica, la copia della mappa permette di muoversi liberamente
        boolean isSwitchingNeeded = false;
        do {
            sixAndEightMap.clear();
            for (Map.Entry<String,HexagonalBase> hexEntry : globalMap.entrySet()) {
                if (hexEntry != null && hexEntry.getValue() != null && Arrays.asList(Numbers.SIX,Numbers.EIGHT).contains(hexEntry.getValue().getNumber())) {
                    sixAndEightMap.put(hexEntry.getKey(),hexEntry.getValue());
                }
            }
            isSwitchingNeeded = postGenerationHelper.AreNearThoseOnList(sixAndEightMap);
            if (isSwitchingNeeded) {
                postGenerationHelper.switchNearNumber(globalMap, sixAndEightMap);
            }
        } while (isSwitchingNeeded);
        logger.info("postGeneratingFixing.numberRuleChecking: near 6 and 8 fixed. Terminated in [" + (System.currentTimeMillis() - t0) + "]");
        GlobalMapHandler.updateMap(globalMap);
    }

    //implementazione singleton instance
    private static MapGeneratorEngine singletonInstance = null;

    private MapGeneratorEngine() {
        this.coordinateHandler = AbstractCoordinateHandler.getInstance();
        this.materialHandler = new MaterialHandler();
        this.numberHandler = new NumberHandler();
        this.generationHelper = new GenerationHelper();
        this.postGenerationHelper = new PostGenerationHelper();
        try {
            this.materialCounter = MaterialCounter.getInstance();
            this.numberCounter = NumberCounter.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
            this.materialCounter = null;
            this.numberCounter = null;
        }
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
