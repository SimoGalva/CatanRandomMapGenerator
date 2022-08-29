package engine;

import engine.engineParams.Params;
import frontEnd.FErunner;
import globalMap.CatMap;
import utils.exceptions.GenerationException;
import utils.exceptions.IslandNumberException;
import utils.logging.LoggingClassesEnum;
import utils.logging.SyncedLogger;

import java.util.logging.Logger;


public class MainEngine implements Runnable {
    private static final Logger logger = SyncedLogger.getLogger(LoggingClassesEnum.MAIN_ENGINE);

    private FErunner frontRunner;
    private CatMap map;
    public Params params;

    public MainEngine() {
        this.frontRunner = new FErunner(new MainEngineCaller());
        frontRunner.runBeforeLaunch();
    }

    @Override
    public void run() {
        this.map = new CatMap(params.getIslandNumber(), params.getMainIslandNumber(),params.getMainIslandWeight(), params.getNumberOfPlayer());
        try {
            this.map.generateIslands();
            this.map.postGeneratingFixing();
        } catch (GenerationException e) {
            logger.severe("Retrying generation after failure. " + e.getMessage());
            refresh();
        } catch (IslandNumberException eI) {
            logger.severe("Retrying generation for incostistent number of island. " + eI.getMessage());
            refresh();
        }
        frontRunner.run();
    }

    public void refresh() {
        RefreshEngine refreshEngine = new RefreshEngine(params);
        refreshEngine.run();
        this.map = refreshEngine.getNewMapToRebuild();
        logger.info("refresh: refreshing process ended");
    }


    public class MainEngineCaller {
        //classe che può essere istanziata da altre classi per chiamare metodi del Main engine
        public void runRefreshing() {
            refresh();
        }

        public Params getParams() {
            return params;
        }

        public void setParams(Params paramsNew) {
            params = paramsNew;
        }

        public void run() {
            MainEngine.this.run();
        }
    }
}
