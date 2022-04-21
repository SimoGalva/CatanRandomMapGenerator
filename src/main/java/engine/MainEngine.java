package engine;

import engine.engineParams.Params;
import frontEnd.FErunner;
import globalMap.CatMap;

import java.util.logging.Logger;


public class MainEngine implements Runnable {
    private final Logger logger = Logger.getLogger(getClass().getName());

    private FErunner frontRunner;
    private CatMap map;
    public Params params;

    //TODO: non riesco a far rispettare i parametri esattamente diciamo che sono dei max corrisponedenti acconsentiti?
    // tienine conto quanto implementi l'input utente. Il paras conterrà tutti gli input utente quindi andrà adattato volta per volta.
    public MainEngine() {
        this.frontRunner = new FErunner(new MainEngineCaller());
        frontRunner.runBeforeLaunch();
    }

    @Override
    public void run() {
        this.map = new CatMap(params.getIslandNumber(), params.getMainIslandNumber(),params.getMainIslandWeight(), params.getNumberOfPlayer());
        this.map.generateIslands();
        this.map.postGeneratingFixing();
        //todo: implementare un postMappingChecking che controlla la conformità della mappa ai parametri e in caso negativo lanci un referesh automatico.
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
