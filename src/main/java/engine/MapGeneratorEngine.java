package engine;

import coordinate.HexagonalCoordinate4PHandler;
import hexagon.HexagonPoint;
import hexagon.HexagonalBase;
import hexagon.material.MaterialCounter;
import hexagon.material.MaterialHandler;
import hexagon.number.NumberCounter;
import hexagon.number.NumberHandler;
import island.IslandController;

import java.util.logging.Logger;

public class MapGeneratorEngine {
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final GenerationHelper generationHelper;

    private HexagonalCoordinate4PHandler coordinateHandler;
    private MaterialHandler materialHandler;
    private NumberHandler numberHandler;
    private MaterialCounter materialCounter;
    private NumberCounter numberCounter;

    private static final String LAND = "LAND";
    private static final String LAND_NO_RIVER = "LAND_NO_R";
    private static final String LAND_SEA = "LAND_SEA";
    private static final String SEA = "SEA";

    public void setIslandHexPointCenter(IslandController controller) {
        HexagonPoint point;
        boolean isDoneGenerating = false;
        boolean isCenterFarEnoughFromOthers = false;
        do {
            if (controller.isMainIsland()) {
                //pickRandomPoint è giustificato all'interno della classe HexagonalCoordinate4PHandler perchè la generazione random dipende strettamente dalla dimensione del tabellone,
                // ergo dal numero di giocatori, così posso eventualmente ricilcare più codice per 6 giocatori;
                point = coordinateHandler.pickRandomPoint(false);
            } else {
                point = coordinateHandler.pickRandomPoint(true);
            }
            isCenterFarEnoughFromOthers = coordinateHandler.checkCenterDisance(point);
            if (isCenterFarEnoughFromOthers) {
                isDoneGenerating = coordinateHandler.consumeCoord(point);
            }
        } while (!isCenterFarEnoughFromOthers || !isDoneGenerating);
        logger.info("generateIslandHexPointCenter: random hexagonal point center of island generated ["+point.getDiagHexCoord()+":"+point.getRowHexCoord()+"]");
        controller.setIslandHexCenter(point);
    }

    public void generateIsland (IslandController controller) {
        //POPOLAMENTO DEL CENTRO
        HexagonPoint islandCntr = controller.getIslandHexCenter();
        HexagonalBase cntrHex = GenerationHelper.generateHexagon(islandCntr, MaterialHandler.LAND);
        controller.populateMap(cntrHex);
        logger.info("generateIsland: island center hexagon generated correctly.");

        //POPOLAMENTO DELL'ISOLA
        if (controller.getNumberOfHexagons() > 0) {
            this.generationHelper.generationThroughPointers(cntrHex, controller);
        } else {
            logger.severe("generateIsland: fatal error. Current island has [0] hexagons allowed.");
            return;
        }

/*
        todo: riprendi da qui
        1    c'è da implementare la costruizione dell resto dell'isola. L'idea è la seguente:
             cicla sul pointer del centro e lo riempie;
             prende un elemento a caso del pointer e quindi cicla sul suo pointer, questo finchè l'isola non è completa.
             questo con chiave di materiale LANDD.
             Generate tutte le isole in CatMap faccio il rempimento del mare nei quadrati rimasti.
             Si potrebbe desiderare che a un certo punto anche il mare sia messo nell'isola, tuttavia ha bisogno di più controlli e una gestione particolare: non è parte dell'isola.
             Lo implementerei in una seconda tornata.
*/
    }




    //implementazione singleton instance
    private static MapGeneratorEngine singletonInstance = null;

    private MapGeneratorEngine() {
        this.coordinateHandler = HexagonalCoordinate4PHandler.getInstance();
        this.materialHandler = new MaterialHandler();
        this.numberHandler = new NumberHandler();
        this.materialCounter = MaterialCounter.getInstance();
        this.numberCounter = NumberCounter.getInstance();
        this.generationHelper = new GenerationHelper();
    }

    public static final MapGeneratorEngine getInstance() {
        return singletonInstance != null ? singletonInstance : getInstanceSafely();
    }

    private static MapGeneratorEngine getInstanceSafely() {
        if (singletonInstance == null) {
            singletonInstance = new MapGeneratorEngine();
        }
        return  singletonInstance;
    }
}
