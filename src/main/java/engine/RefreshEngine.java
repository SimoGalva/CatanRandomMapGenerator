package engine;

import coordinate.HexagonalCoordinate4PHandler;
import engine.engineParams.Params;
import globalMap.CatMap;
import globalMap.GlobalMapHandler;
import hexagon.material.MaterialCounter;
import hexagon.number.NumberCounter;
import island.IslandController;

public class RefreshEngine implements Runnable {
    private Params params;
    private CatMap newMapToRebuild;

    public CatMap getNewMapToRebuild() {
        return newMapToRebuild;
    }

    public RefreshEngine(Params params) {
        this.params = params;
    }

    @Override
    public void run() {
        clearSingleInstances();
        this.newMapToRebuild = new CatMap(params.getIslandNumber(), params.getMainIslandNumber(), params.getMainIslandWeight());
        this.newMapToRebuild.generateIslands();
        this.newMapToRebuild.postGeneratingFixing();
    }

    private void clearSingleInstances() {
        GlobalMapHandler.clear();
        IslandController.clearSingletonInstance();
        HexagonalCoordinate4PHandler.clearSingletonInstance();
        MapGeneratorEngine.clearSingletonInstance();
        NumberCounter.clearSingletonInstance();
        MaterialCounter.clearSingletonInstance();
    }

}
