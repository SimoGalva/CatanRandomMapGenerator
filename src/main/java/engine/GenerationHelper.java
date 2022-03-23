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

        level = level + 1;
        HexagonPoint[] currentPointer = hexagonStarter.getPointer().clone();

        for (HexagonPoint currentPoint : currentPointer) {
            if (controller.getNumberOfHexagons() > 0) {
                if (coordinateHandler.consumeCoord(currentPoint)) {
                    controller.populateMap(generateHexagon(currentPoint, restrictionOnMaterial));
                }
            } else {
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
        Materials materialCntr = null;
        Numbers numberCntr = null;

        int pointerCntrDim = coordinateHandler.calculatePointerDimesnsion(pointInGeneration);
        do {
            if (!isMaterialValid) {
                materialCntr = materialHandler.pickRandomMaterial(restrictionOnMaterial);
                isMaterialValid = materialCounter.consumeMaterial(materialCntr);
            }
            if (!isNumberValid) {
                numberCntr = numberHandler.pickRandomNumber(materialCntr);
                isNumberValid = numberCounter.consumeNumber(numberCntr);
            }
        } while (!isNumberValid && !isMaterialValid);

        return HexagonalBase.createInstance(materialCntr, numberCntr, pointerCntrDim, pointInGeneration);
    }
}
