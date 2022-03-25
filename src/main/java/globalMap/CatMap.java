package globalMap;

import hexagon.HexagonalBase;
import hexagon.material.MaterialCounter;
import island.Island;
import island.IslandController;

import java.util.HashMap;
import java.util.logging.Logger;

public class CatMap {
    private final Logger logger = Logger.getLogger(getClass().getName());

    private Island[] islands;
    private MaterialCounter materialCounter;
    private int islandsNumber;
    private int mainIslandsNumber;
    private IslandController islandController;
    private final static HashMap<String, HexagonalBase> globalMap = GlobalMapHandler.getGlobalMap(); //sincronzza la mappa sempre e comunque

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
            logger.info("globalMap.CatMap.generateMapIsland: setting center for "+ (this.islandController.getFiniteController()[i].isMainIsland() ? "main" : "") +"island number ["+(i+1)+"] (of ["+islandsNumber+"]).");
            this.islands[i] = new Island(this.islandController.getFiniteController()[i]);
            //todo: l'ultimo bug è più raro ed è qui:
            // genero le isole in odrine e quindi il centro di un isola successiva è consumato a livello di coordinate possibili non è ancora stato generato, quindi quanto lo si cerca di prelevare dalle mappe è nullo.
        }
        for (int i = 0; i < this.islandsNumber; i++) {
            logger.info("globalMap.CatMap.generateMapIsland: starting "+ (this.islandController.getFiniteController()[i].isMainIsland() ? "main" : "") +"island number ["+(i+1)+"] (of ["+islandsNumber+"]) generation process.");
            islands[i].generateIsland();
        }
        logger.info("globalMap.CatMap.generateMapIsland: generation process ended.");
    }
}
