package globalMap;

import coordinate.AbstractCoordinateHandler;
import engine.MapGeneratorEngine;
import hexagon.HexagonalBase;
import hexagon.material.MaterialCounter;
import hexagon.number.NumberCounter;
import island.Island;
import island.IslandController;
import utils.exceptions.GenerationException;
import utils.exceptions.IslandNumberException;
import utils.logging.LoggingClassesEnum;
import utils.logging.SyncedLogger;

import java.util.HashMap;
import java.util.logging.Logger;
 //todo: logica del sizeCode delle mappe: la mappa da n player può essere più o meno grande di un blochetto orizzonatale. Dare scelta. (attenzione è una modifica profonda, backend e frontend)
public class CatMap {
     private static final Logger logger = SyncedLogger.getLogger(LoggingClassesEnum.CAT_MAP);

    private Island[] islands;
    private MaterialCounter materialCounter;
    private NumberCounter numberCounter;
    private int islandsNumber;
    private int mainIslandsNumber;
    private final int numberOfPlayer;
    private IslandController islandControllerWrapper;
    private AbstractCoordinateHandler coordinateHandler;
    private MapGeneratorEngine generatorEngine;
    private final static HashMap<String, HexagonalBase> globalMap = GlobalMapHandler.getGlobalMap(); //sincronzza la mappa sempre e comunque

    public Island[] getIslands() {
        return islands;
    }

    public CatMap(int islandsNumber, int mainIslandsNumber, int mainIslandWeight, int numberOfPlayer) {
        this.coordinateHandler = AbstractCoordinateHandler.getInstance(numberOfPlayer);
        this.islands = new Island[islandsNumber];
        this.materialCounter = MaterialCounter.createInstance(numberOfPlayer);
        this.numberCounter = NumberCounter.createInstance(numberOfPlayer);
        this.generatorEngine = MapGeneratorEngine.getInstance();
        this.islandsNumber = islandsNumber;
        this.mainIslandsNumber = mainIslandsNumber;
        this.numberOfPlayer = numberOfPlayer;
        //ritorna un islandController che contiere un array di IslandController (i cui sotto array saranno nulli)
        this.islandControllerWrapper = IslandController.getInstance(islandsNumber,mainIslandsNumber, mainIslandWeight);
    }

     public void generateIslands() throws GenerationException {
        for (int i = 0; i < this.islandsNumber; i++) {
            logger.info("globalMap.CatMap.generateMapIsland: setting center for "+ (this.islandControllerWrapper.getFiniteController()[i].isMainIsland() ? "main" : "") +"island number ["+(i+1)+"] (of ["+islandsNumber+"]).");
            this.islands[i] = new Island(this.islandControllerWrapper.getFiniteController()[i]);
            logger.info("globalMap.CatMap.generateMapIsland: starting "+ (this.islandControllerWrapper.getFiniteController()[i].isMainIsland() ? "main" : "") +"island number ["+(i+1)+"] (of ["+islandsNumber+"]) generation process.");
            islands[i].generateIsland();
            generatorEngine.fillIslandBorderSea(this.islandControllerWrapper.getFiniteController()[i]);
        }
        logger.info("globalMap.CatMap.generateMapIsland: generation process ended.");
        materialCounter.printRemains();
        numberCounter.printRemains();
    }

    public void postGeneratingFixing() throws IslandNumberException{
        logger.info("postGeneratingFixing: starting post generating fixes");
        GlobalMapHandler.populateLimitWaterHexagons(numberOfPlayer);
        fillOcean();
        numberOfIslandChecking();
        numberRuleChecking();
        logger.info("postGeneratingFixing: process ended.");
    }

     private void numberOfIslandChecking() throws IslandNumberException {
         logger.info("numberOfIslandChecking: checking that the number of island produced is consistent with user input.");
         generatorEngine.numberOfIslandChecking(this.islandsNumber);
     }

     public void fillOcean() {
         logger.info("fillOcean: starting to fill with the remaining oceans.");
         generatorEngine.fillOcenan();
     }

     private void numberRuleChecking() {
         logger.info("numberRuleChecking: checking that 6, 8 are far from each other.");
         generatorEngine.numberRuleChecking();
     }
 }
