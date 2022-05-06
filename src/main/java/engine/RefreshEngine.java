package engine;

import coordinate.AbstractCoordinateHandler;
import engine.engineParams.Params;
import globalMap.CatMap;
import globalMap.GlobalMapHandler;
import hexagon.material.MaterialCounter;
import hexagon.number.NumberCounter;
import island.IslandController;
import utils.exceptions.GenerationException;

import java.util.logging.Logger;

public class RefreshEngine implements Runnable {
    private final Logger logger = Logger.getLogger(getClass().getName());
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
        this.newMapToRebuild = new CatMap(params.getIslandNumber(), params.getMainIslandNumber(), params.getMainIslandWeight(), params.getNumberOfPlayer());
        try {
            this.newMapToRebuild.generateIslands();
            this.newMapToRebuild.postGeneratingFixing();
        } catch (GenerationException e) {
            logger.severe("Retrying generation after failure. " + e.getMessage());
            run();
        }
    }

    private void clearSingleInstances() {
        GlobalMapHandler.clear();
        IslandController.clearSingletonInstance();
        AbstractCoordinateHandler.clearSingletonInstance();
        MapGeneratorEngine.clearSingletonInstance();
        NumberCounter.clearSingletonInstance();
        MaterialCounter.clearSingletonInstance();
    }

}
