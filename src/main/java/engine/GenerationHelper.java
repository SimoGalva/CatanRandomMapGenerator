package engine;

import coordinate.AbstractCoordinateHandler;
import globalMap.GlobalMapHandler;
import hexagon.HexagonPoint;
import hexagon.HexagonalBase;
import hexagon.material.MaterialCounter;
import hexagon.material.MaterialHandler;
import hexagon.material.Materials;
import hexagon.number.NumberCounter;
import hexagon.number.NumberHandler;
import hexagon.number.Numbers;
import island.IslandController;
import utils.GenerationException;
import utils.pojo.SwitchingHexagons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

public class GenerationHelper {
    private final Logger logger = Logger.getLogger(getClass().getName());

    private IslandController controller;
    private boolean isMainIsland;
    private boolean seaAllowed;
    private final static Random random = new Random();
    private AbstractCoordinateHandler coordinateHandler;

    public GenerationHelper() {
        coordinateHandler = AbstractCoordinateHandler.getInstance();
    }

    protected void generationThroughPointers(HexagonalBase hexagonStarter, IslandController controller) throws GenerationException {
        this.controller = controller;
        this.isMainIsland = controller.isMainIsland();
        int level = 0;
        generationThroughPointers(hexagonStarter, level);
    }

    private void generationThroughPointers(HexagonalBase hexagonStarter, int level) throws GenerationException {
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
            level++;
            if (level > 3500) {
                throw new GenerationException("generationThrughPointers: " + GenerationException.MESSAGE);
            }
            generationThroughPointers(controller.getHexagonFromMap(currentPointer[random.nextInt(currentPointer.length)]), level);
        } else {
            logger.info("generationThroughPointers: recursive generation complete.");
        }
    }

    public HexagonalBase generateHexagon(HexagonPoint pointInGeneration, String restrictionOnMaterial) {
        MaterialCounter materialCounter;
        NumberCounter numberCounter;
        try {
            materialCounter = MaterialCounter.getInstance();
            numberCounter = NumberCounter.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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

    public boolean isNearIsland(HexagonPoint hexagonalBaseToTest, HashMap<String, HexagonalBase> islandMap) {
        boolean ret = false;
        for (Map.Entry<String, HexagonalBase> mapEntry : islandMap.entrySet()) {
            HexagonPoint[] pointerToTest = mapEntry.getValue().getPointer();
            for (HexagonPoint pointInTest : pointerToTest) {
                if (pointInTest.toString().equals(hexagonalBaseToTest.toString())) {
                    return true;
                }
            }
        }
        return ret;
    }
    public boolean isNearIsland(HexagonalBase hexagonalBaseToTest, HashMap<String, HexagonalBase> islandMap) {
        return isNearIsland(hexagonalBaseToTest.getHexAsPoint(),islandMap);
    }

    public SwitchingHexagons switchWithRandomNearbyIslandSea(HexagonalBase hexagonToSwitch, HashMap<String, HexagonalBase> islandMapOfTestingHexagon) {
        logger.info("switchWithRandomNearbyIslandSea: starting selecting sea hexagon to switch.");
        Random random = new Random();
        SwitchingHexagons ret;
        ArrayList<HexagonalBase> seaList = GlobalMapHandler.getSeaHexagons();
        ArrayList<HexagonalBase> nearIslandSeaList = new ArrayList<>();
        for (HexagonalBase seaEntry : seaList) {
            if(this.isNearIsland(seaEntry, islandMapOfTestingHexagon)) {
                nearIslandSeaList.add(seaEntry);
            }
        }
        if (nearIslandSeaList.isEmpty()) {
            logger.info("switchWithRandomNearbyIslandSea: not switching any hexagon. Returning null.");
            return null;
        } else {
            HexagonalBase seaHexagonToSwitch = nearIslandSeaList.get(random.nextInt(nearIslandSeaList.size()));
            ret = new SwitchingHexagons(hexagonToSwitch.getHexAsPoint().toString(), seaHexagonToSwitch.getHexAsPoint().toString());
            logger.info("switchWithRandomNearbyIslandSea: switched hexagon ["+hexagonToSwitch.getHexAsPoint().toString()+"] with WATER hexagon ["+seaHexagonToSwitch.getHexAsPoint().toString()+"]");
            GlobalMapHandler.switchHexagons(hexagonToSwitch, seaHexagonToSwitch);
            return ret;
        }
    }
}
