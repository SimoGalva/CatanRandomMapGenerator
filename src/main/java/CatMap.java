import hexagon.material.MaterialCounter;
import island.Island;
import island.IslandController;

import java.util.logging.Logger;

public class CatMap {
    private final Logger logger = Logger.getLogger(getClass().getName());

    private Island[] islands;
    private MaterialCounter materialCounter;
    private int islandsNumber;
    private int mainIslandsNumber;
    private IslandController islandController;

    public Island[] getIslands() {
        return islands;
    }

    public CatMap(int islandsNumber, int mainIslandsNumber, int mainIslandWeight) {
        this.islands = new Island[islandsNumber];
        this.materialCounter = MaterialCounter.getInstance();
        this.islandsNumber = islandsNumber;
        this.mainIslandsNumber = mainIslandsNumber;
        //ritorna un islandController che contiere un array di IslandController (i cui sotto array saranno nulli)
        this.islandController = IslandController.getInstance(islandsNumber,mainIslandsNumber, mainIslandWeight);
    }

    public void generateMapIslands() {
        for (int i = 0; i < this.islandsNumber; i++) {
            logger.info("CatMap.generateMapIsland: setting center for "+ (this.islandController.getFiniteController()[i].isMainIsland() ? "main" : "") +"island number ["+(i+1)+"] (of ["+islandsNumber+"]).");
            this.islands[i] = new Island(this.islandController.getFiniteController()[i]);
        }
        for (int i = 0; i < this.islandsNumber; i++) {
            logger.info("CatMap.generateMapIsland: starting "+ (this.islandController.getFiniteController()[i].isMainIsland() ? "main" : "") +"island number ["+(i+1)+"] (of ["+islandsNumber+"]) generation process.");
            islands[i].generateIsland();
        }
        logger.info("CatMap.generateMapIsland: generation process ended.");
    }
}
