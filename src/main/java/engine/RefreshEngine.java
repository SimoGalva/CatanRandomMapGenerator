package engine;

import coordinate.AbstractCoordinateHandler;
import engine.engineParams.Params;
import globalMap.CatMap;
import globalMap.GlobalMapHandler;
import hexagon.material.MaterialCounter;
import hexagon.number.NumberCounter;
import island.IslandController;
import utils.exceptions.GenerationException;
import utils.exceptions.IslandNumberException;
import utils.logging.LoggingClassesEnum;
import utils.logging.SyncedLogger;

import java.util.logging.Logger;

public class RefreshEngine implements Runnable {
    private static final Logger logger = SyncedLogger.getLogger(LoggingClassesEnum.REFRESH_ENGINE);
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
        } catch (IslandNumberException eI) {
            logger.severe("Retrying generation for incostistent number of island. " + eI.getMessage());
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
