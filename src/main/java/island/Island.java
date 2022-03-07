package island;

import engine.MapGeneratorEngine;
import hexagon.HexagonPoint;
import hexagon.HexagonalBase;

import java.util.HashMap;
import java.util.List;

public class Island {
    private List<HexagonalBase> islandHexagons;
    private IslandController controller;
    private HexagonPoint IslandHexCenter;
    private MapGeneratorEngine generatorEngine;
    private HashMap<String,HexagonalBase> islandCoordHexagonMap;

    public Island(IslandController controller) {
        this.generatorEngine = MapGeneratorEngine.getInstance();
        this.controller = controller;
        //questo generate genera random, è più interessante metterlo da input utente?
        this.IslandHexCenter = generatorEngine.generateIslandHexPointCenter(controller);
    }

    public void generateIsland() {
        if (!controller.isGenerated()) {
            generatorEngine.generateIsland(controller);
        }
        controller.setGenerated(true);
    }
}
