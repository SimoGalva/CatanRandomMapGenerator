package globalMap;

import engine.MapGeneratorEngine;
import hexagon.HexagonalBase;
import hexagon.material.MaterialCounter;
import hexagon.number.NumberCounter;
import island.Island;
import island.IslandController;

import java.util.HashMap;
import java.util.logging.Logger;

public class CatMap {
    private final Logger logger = Logger.getLogger(getClass().getName());

    private Island[] islands;
    private MaterialCounter materialCounter;
    private NumberCounter numberCounter;
    private int islandsNumber;
    private int mainIslandsNumber;
    private final int numberOfPlayer = 4;
    private IslandController islandController;
    private MapGeneratorEngine generatorEngine;
    private final static HashMap<String, HexagonalBase> globalMap = GlobalMapHandler.getGlobalMap(); //sincronzza la mappa sempre e comunque

    public Island[] getIslands() {
        return islands;
    }

    public CatMap(int islandsNumber, int mainIslandsNumber, int mainIslandWeight) {
        this.islands = new Island[islandsNumber];
        this.materialCounter = MaterialCounter.getInstance();
        this.numberCounter = NumberCounter.getInstance();
        this.generatorEngine = MapGeneratorEngine.getInstance();
        this.islandsNumber = islandsNumber;
        this.mainIslandsNumber = mainIslandsNumber;
        //ritorna un islandController che contiere un array di IslandController (i cui sotto array saranno nulli)
        this.islandController = IslandController.getInstance(islandsNumber,mainIslandsNumber, mainIslandWeight);
    }

    public void generateIslands() {
        for (int i = 0; i < this.islandsNumber; i++) {
            logger.info("globalMap.CatMap.generateMapIsland: setting center for "+ (this.islandController.getFiniteController()[i].isMainIsland() ? "main" : "") +"island number ["+(i+1)+"] (of ["+islandsNumber+"]).");
            this.islands[i] = new Island(this.islandController.getFiniteController()[i]);
        }
        for (int i = 0; i < this.islandsNumber; i++) {
            logger.info("globalMap.CatMap.generateMapIsland: starting "+ (this.islandController.getFiniteController()[i].isMainIsland() ? "main" : "") +"island number ["+(i+1)+"] (of ["+islandsNumber+"]) generation process.");
            islands[i].generateIsland();
        }
        logger.info("globalMap.CatMap.generateMapIsland: generation process ended.");
        materialCounter.printRemains();
        numberCounter.printRemains();
    }

    public void fillOcean() {
        logger.info("fillOcean: starting to fill with the remaining oceans.");
        generatorEngine.fillOcenan();
    }

    public void postGeneratingFixing() {
        logger.info("postGeneratingFixing: starting post generating fixes");
        switch (this.numberOfPlayer) {
            case 4:
                GlobalMapHandler.doPostGeneratingFixing(4);
                break;
        }
    }
}
