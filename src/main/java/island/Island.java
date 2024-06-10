package island;

import engine.MapGeneratorEngine;
import hexagon.HexagonalBase;
import utils.exceptions.GenerationException;
import utils.logging.LoggingClassesEnum;
import utils.logging.SyncedLogger;

import java.util.HashMap;
import java.util.logging.Logger;

public class Island {
    private static final Logger logger = SyncedLogger.getLogger(LoggingClassesEnum.ISLAND);

    private IslandController controller;
    private MapGeneratorEngine generatorEngine;
    private HashMap<String,HexagonalBase> islandCoordHexagonMap;

    public Island(IslandController controller) throws GenerationException {
        this.generatorEngine = MapGeneratorEngine.getInstance();
        this.controller = controller;
        this.islandCoordHexagonMap = new HashMap<>();
        this.controller.syncMap(this.islandCoordHexagonMap);
        //questo generate genera random, è più interessante metterlo da input utente?
        generatorEngine.setIslandHexPointCenter(controller);
        logger.info("Island: instance created with \n " +
                    "-> center in: ["+this.controller.getIslandHexCenter().toString()+"] \n" +
                    "-> is ["+(this.controller.isMainIsland() ? "main" : "not main")+"] \n" +
                    "-> number of hexagon: ["+this.controller.getNumberOfHexagons()+"]");
    }

    public void generateIsland() throws GenerationException {
        if (!controller.isGenerated()) {
            logger.info("Island.generateIsland: starting generating the island.");
            generatorEngine.generateIsland(controller);
            controller.setGenerated(true);
        } else {
            logger.info("Island.generateIsland: island already generated.");
        }
    }
}
