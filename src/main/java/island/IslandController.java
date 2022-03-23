package island;

import hexagon.HexagonPoint;
import hexagon.HexagonalBase;

import java.util.HashMap;
import java.util.logging.Logger;

public class IslandController {
    private final Logger logger = Logger.getLogger(getClass().getName());

    private IslandController[] finiteController = null;
    private boolean isMainIsland;
    private boolean isGenerated;
    //il numero di esagoni di un isola non è casuale, è essenzialmente predeterminato dall'utente, si può fare in modo che sia casuale, ma alivello più alto, qui deve entrare un peso fissato.
    private int numberOfHexagons;
    private HexagonPoint IslandHexCenter;
    private HashMap<String, HexagonalBase> islandMap = null;
    private final static int totalLand = 30;
    private final static int maxWeight = 10;



    public HashMap<String, HexagonalBase> getIslandMap() {
        return islandMap;
    }

    public HexagonPoint getIslandHexCenter() {
        return IslandHexCenter;
    }
    public void setIslandHexCenter(HexagonPoint islandHexCenter) {
        IslandHexCenter = islandHexCenter;
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

    public int getNumberOfHexagons() {
        return numberOfHexagons;
    }

    public IslandController[] getFiniteController() {
        return finiteController;
    }

    //sincronizza la mappa del controller con quella dell'isola, ogni modifica a questa mappa nel controllere corrisponde alla stessa per l'isola.
     public void syncMap(HashMap<String,HexagonalBase> islandMap) {
        this.islandMap = islandMap;
     }

     public void populateMap(HexagonalBase hexagonalBase){
        islandMap.put(hexagonalBase.getHexAsPoint().toString(),hexagonalBase);
         numberOfHexagons --;
     }
     public HexagonalBase getHexagonFromMap(HexagonPoint point) {
        return islandMap.get(point.toString());
     }

    //implementazione singleton instance
    private static IslandController singletonInstance = null;
    //TODO: serviranno controlli sugli input di islandsNumber e mainIslandsNumber: islandNumber>=mainIslandsNumber. E sul peso: vedi commit collegato (numero di pezzi isola).
    private IslandController(int islandsNumber, int mainIslandsNumber,int mainIslandWeight) {
        finiteController = new IslandController[islandsNumber];

        //gestione dei pesi: lavoro sulla prima main island per correzioni
        //todo: temporanei system.out
        double distributedMainIslWeight = (mainIslandWeight) / mainIslandsNumber;
        double distributedNonMainIslweight = islandsNumber != mainIslandsNumber ? (double) ((maxWeight - mainIslandWeight) / (islandsNumber-mainIslandsNumber)) : maxWeight;
        int[] numberOfPieces = new int[islandsNumber];
        int totalCheck = 0;
        for (int i = 0; i < islandsNumber; i++) {
            if (i < mainIslandsNumber) {
                numberOfPieces[i] = (int) (totalLand*(distributedMainIslWeight / maxWeight));
            } else {
                numberOfPieces[i] = (int) (totalLand*(distributedNonMainIslweight / maxWeight));
            }
            totalCheck = totalCheck + numberOfPieces[i];
        }
        if (totalCheck < totalLand) {
            numberOfPieces[0] = numberOfPieces[0] + (totalLand-totalCheck);
        } else if (totalCheck > totalLand) {
            numberOfPieces[0] = numberOfPieces[0] + (totalLand-totalCheck);
        }
        for (int i = 0; i < islandsNumber; i++) {
            if (i < mainIslandsNumber) {
                finiteController[i] = new IslandController(true, numberOfPieces[i]);
            } else {
                finiteController[i] = new IslandController(false, numberOfPieces[i]);
            }
        }
    }

    private IslandController(boolean isMainIsland, int numberOfPieces) {
        this.isMainIsland = isMainIsland;
        this.numberOfHexagons = numberOfPieces;
        this.isGenerated = false;
    }

    public static IslandController getInstance(int islandsNumber, int mainIslandsNumber, int mainIsladWeight) {
        return singletonInstance != null ? singletonInstance : getInstanceSafely(islandsNumber,mainIslandsNumber,mainIsladWeight);
    }

    public static IslandController getInstanceSafely(int islandsNumber, int mainIslandsNumber,int mainIslandWeight) {
        if (singletonInstance == null) {
            singletonInstance = new IslandController(islandsNumber, mainIslandsNumber, mainIslandWeight);
        }
        return singletonInstance;
    }

}
