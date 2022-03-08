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

    public CatMap(int islandsNumber, int mainIslandsNumber) {
        this.islands = new Island[islandsNumber];
        this.materialCounter = MaterialCounter.getInstance();
        this.islandsNumber = islandsNumber;
        this.mainIslandsNumber = mainIslandsNumber;
        //ritorna un islandController che contiere un array di IslandController (i cui sotto array saranno nulli)
        this.islandController = IslandController.getInstance(islandsNumber,mainIslandsNumber);
    }

    public void generateMapIsland() {
        for (int i = 0; i < this.islandsNumber; i++) {
           logger.info("CatMap.generateMapIsland: starting islands generation process.");
           this.islands[i] = new Island(this.islandController.getFiniteController()[i]);
           islands[i].generateIsland();
        }
        logger.info("CatMap.generateMapIsland: generation process ended.");
    }
}
