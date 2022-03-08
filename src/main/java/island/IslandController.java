package island;

import hexagon.HexagonalBase;

import java.util.HashMap;
import java.util.logging.Logger;

public class IslandController {
    private final Logger logger = Logger.getLogger(getClass().getName());

    private IslandController[] finiteController = null;
    private boolean isMainIsland;
    private boolean isGenerated;
    private HashMap<String, HexagonalBase> islandMap = null;

    public HashMap<String, HexagonalBase> getIslandMap() {
        return islandMap;
    }

    public boolean isGenerated() {
        return isGenerated;
    }
    public void setGenerated(boolean generated) {
        isGenerated = generated;
    }

    public boolean isMainIsland() {
        return isMainIsland;
    }

    public IslandController[] getFiniteController() {
        return finiteController;
    }

    //sincronizza la mappa del controller con quella dell'isola, ogni modifica a questa mappa nel controllere corrisponde alla stessa per l'isola.
     public void syncMap(HashMap<String,HexagonalBase> islandMap) {
        this.islandMap = islandMap;
     }

    //implementazione singleton instance
    private static IslandController singletonInstance = null;
    //TODO: serviranno controlli sugli input di islandsNumber e mainIslandsNumber.
    private IslandController(int islandsNumber, int mainIslandsNumber) {
        finiteController = new IslandController[islandsNumber];
        for (int i = 0; i < islandsNumber; i++) {
            if (i < mainIslandsNumber) {
                finiteController[i] = new IslandController(true);
            } else {
                finiteController[i] = new IslandController(false);
            }
        }
    }

    private IslandController(boolean isMainIsland) {
        this.isMainIsland = isMainIsland;
        isGenerated = false;
    }

    public static IslandController getInstance(int islandsNumber, int mainIslandsNumber) {
        return singletonInstance != null ? singletonInstance : getInstanceSafely(islandsNumber,mainIslandsNumber);
    }

    public static IslandController getInstanceSafely(int islandsNumber, int mainIslandsNumber) {
        if (singletonInstance == null) {
            singletonInstance = new IslandController(islandsNumber, mainIslandsNumber);
        }
        return singletonInstance;
    }

}
