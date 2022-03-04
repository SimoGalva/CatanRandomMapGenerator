import hexagon.material.MaterialCounter;
import island.Island;
import island.IslandController;

public class CatMap {
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
            this.islands[i] = new Island(this.islandController.getFiniteController()[i]);
        }
    }
}
