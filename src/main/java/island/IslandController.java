package island;

public class IslandController {
    private IslandController[] finiteController = null;
    private boolean isMainIsland;
    private boolean isGenerated;

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
