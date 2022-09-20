package engine;

import engine.engineParams.Params;
import frontEnd.FErunner;
import globalMap.CatanMap;
import globalMap.MapHandler;
import utils.Constants;
import utils.exceptions.GenerationException;
import utils.exceptions.IslandNumberException;
import utils.logging.LoggingClassesEnum;
import utils.logging.SyncedLogger;

import java.util.logging.Logger;


public class MainEngine implements Runnable {
    private static final Logger logger = SyncedLogger.getLogger(LoggingClassesEnum.MAIN_ENGINE);

    private FErunner frontRunner;
    private CatanMap map;
    public Params params;
    public boolean hasBeenLoaded = false;

    public MainEngine() {
        this.frontRunner = new FErunner(new MainEngineCaller());
        frontRunner.runBeforeLaunch();
    }

    @Override
    public void run() {
        this.map = new CatanMap(params.getIslandNumber(), params.getMainIslandNumber(),params.getMainIslandWeight(), params.getNumberOfPlayer());
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
            if (hasBeenLoaded) {
                hasBeenLoaded = false;
                params = Params.getRandomConstrainedParams(Constants.RANDOM_LOCK_NUMBER_PLAYER, params.getNumberOfPlayer());
                logger.info("Created new random params: [ " + params.toString() + " ] ");
            }
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

        //setta a dei valori ad hoc tutti i parametri a seguito di un load from file.
        public void forceLoadParams() {
            logger.info("forcing loading params: [-100,-100,-100," + MapHandler.calculateNumberOfPlayerForFront() + "].");
            hasBeenLoaded = true;
            params = new Params(-100,-100,-100, MapHandler.calculateNumberOfPlayerForFront());
        }
    }
}
