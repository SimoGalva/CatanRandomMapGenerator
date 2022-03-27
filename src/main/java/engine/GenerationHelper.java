package engine;

import coordinate.HexagonalCoordinate4PHandler;
import hexagon.HexagonPoint;
import hexagon.HexagonalBase;
import hexagon.material.MaterialCounter;
import hexagon.material.MaterialHandler;
import hexagon.material.Materials;
import hexagon.number.NumberCounter;
import hexagon.number.NumberHandler;
import hexagon.number.Numbers;
import island.IslandController;

import java.util.Random;
import java.util.logging.Logger;

public class GenerationHelper {
    private final Logger logger = Logger.getLogger(getClass().getName());

    private IslandController controller;
    private boolean isMainIsland;
    private boolean seaAllowed;
    private final static Random random = new Random();
    private static final HexagonalCoordinate4PHandler coordinateHandler = HexagonalCoordinate4PHandler.getInstance();

    protected void generationThroughPointers(HexagonalBase hexagonStarter, IslandController controller) {
        this.controller = controller;
        this.isMainIsland = controller.isMainIsland();
        int level = 0;
        generationThroughPointers(hexagonStarter, level);
    }

    private void generationThroughPointers(HexagonalBase hexagonStarter, int level) {
        logger.info("generationThroughPointers: starting generation al level ["+level+"] from hexagon ["+hexagonStarter.getHexAsPoint().toString()+"].");
        boolean isDoneGenerating = false;
        if (isMainIsland && level < 2) { //il limite due messo indicativo a caso bisogna ragionarci in base a come escono i risultati
            this.seaAllowed = false;
        } else {
            this.seaAllowed = true;
        }
        String restrictionOnMaterial = seaAllowed ? MaterialHandler.LANDD : MaterialHandler.LAND_WATER; //il deserto è sempre ammesso tranne nel centro dell'isola

        HexagonPoint[] currentPointer = hexagonStarter.getPointer().clone();
        for (HexagonPoint currentPoint : currentPointer) {
            if (controller.getNumberOfHexagons() > 0) {
                if (coordinateHandler.consumeCoord(currentPoint)) {
                    controller.populateMap(generateHexagon(currentPoint, restrictionOnMaterial));
                }
            } else {
                logger.info("generationThroughPointer: all hexagon for the island are populated, isDoneGenerating = ["+true+"].");
                isDoneGenerating = true;
                break;
            }
        }

        if (!isDoneGenerating) {
            generationThroughPointers(controller.getHexagonFromMap(currentPointer[random.nextInt(currentPointer.length)]), level);
        } else {
            logger.info("generationThroughPointers: recursive generation complete.");
        }
    }

    public static HexagonalBase generateHexagon(HexagonPoint pointInGeneration, String restrictionOnMaterial) {
        MaterialCounter materialCounter = MaterialCounter.getInstance();
        NumberCounter numberCounter = NumberCounter.getInstance();
        MaterialHandler materialHandler = new MaterialHandler();
        NumberHandler numberHandler = new NumberHandler();

        boolean isNumberValid = false;
        boolean isMaterialValid = false;
        Materials material = null;
        Numbers number = null;

        int pointerDim = coordinateHandler.calculatePointerDimesnsion(pointInGeneration);
        do {
            if (!isMaterialValid) {
                material = materialHandler.pickRandomMaterial(restrictionOnMaterial);
                isMaterialValid = materialCounter.consumeMaterial(material);
                if (!isMaterialValid) {
                    material = null;
                }
            }
            if (!isNumberValid) {
                number = numberHandler.pickRandomNumber(material);
                isNumberValid = numberCounter.consumeNumber(number);
                if (!isNumberValid) {
                    number = null;
                }
            }
        } while ((!isNumberValid && !isMaterialValid) || number == null || material == null);

        return HexagonalBase.createInstance(material, number, pointerDim, pointInGeneration);
    }
}
