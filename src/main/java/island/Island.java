package island;

import engine.MapGeneratorEngine;
import hexagon.HexagonalBase;

import java.util.HashMap;
import java.util.logging.Logger;

public class Island {
    private final Logger logger = Logger.getLogger(getClass().getName());

    private IslandController controller;
    private MapGeneratorEngine generatorEngine;
    private HashMap<String,HexagonalBase> islandCoordHexagonMap;

    public Island(IslandController controller) {
        this.generatorEngine = MapGeneratorEngine.getInstance();
        this.controller = controller;
        this.islandCoordHexagonMap = new HashMap<>();
        this.controller.syncMap(this.islandCoordHexagonMap);
        //questo generate genera random, è più interessante metterlo da input utente?
        generatorEngine.generateIslandHexPointCenter(controller);
        logger.info("Island: instance created with \n " +
                    "-> center in: ["+this.controller.getIslandHexCenter().toString()+"] \n" +
                    "-> is ["+(this.controller.isMainIsland() ? "main" : "not main")+"] \n" +
                    "-> number of hexagon: ["+this.controller.getNumberOfHexagons()+"]");
    }

    public void generateIsland() {
        if (!controller.isGenerated()) {
            logger.info("Island.generateIsland: starting generating the island.");
            generatorEngine.generateIsland(controller);
            controller.setGenerated(true);
        } else {
            logger.info("Island.generateIsland: island already generated.");
        }
    }
}
