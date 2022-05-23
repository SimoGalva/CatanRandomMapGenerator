package engine;

import hexagon.HexagonPoint;
import hexagon.HexagonalBase;
import hexagon.material.Materials;
import hexagon.number.Numbers;
import utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class PostGenerationHelper {
    private final Logger logger = Logger.getLogger(getClass().getName());

    HexagonalBase[] tempCoupleOfNearHexagons = new HexagonalBase[2];

    //checking for numbers
    public boolean AreNearThoseOnList(HashMap<String, HexagonalBase> listToCheck) {
        boolean areThereNearSixEight = false;

        for (Map.Entry<String,HexagonalBase> pointerBase : listToCheck.entrySet()) {
            HexagonPoint[] currPointer = pointerBase.getValue().getPointer();
            for (Map.Entry<String,HexagonalBase> checkingBase : listToCheck.entrySet()) {
                for (HexagonPoint currPointerEntry : currPointer) {
                    if (checkingBase.getValue().getHexAsPoint().toString().equals(currPointerEntry.toString())) {
                        tempCoupleOfNearHexagons[0] = pointerBase.getValue();
                        tempCoupleOfNearHexagons[1] = checkingBase.getValue();
                        areThereNearSixEight = true;
                        break;
                    }
                    if (areThereNearSixEight) break;
                }
                if (areThereNearSixEight) break;
            }
            if (areThereNearSixEight) break;
        }
        return areThereNearSixEight;
    }

    public void switchNearNumber(HashMap<String, HexagonalBase> globalMap, HashMap<String, HexagonalBase> sixAndEightMap) {
        HashMap<String, HexagonalBase> globalCloneInCleaning = Utils.duplicateMap(globalMap);
        for (Map.Entry<String,HexagonalBase> currSixEightEntry: sixAndEightMap.entrySet()) {
            try {
                globalCloneInCleaning.remove(currSixEightEntry.getKey());
                for (HexagonPoint currPointer : currSixEightEntry.getValue().getPointer()) {
                    globalCloneInCleaning.remove(currPointer.toString());
                }
            } catch (Exception e) {
                logger.severe("switchNearNumber: Unhandled exception in removing point and pointer.");
                e.printStackTrace();
            }
        }
        HashMap<String, HexagonalBase> tempGlobalCloneClone = Utils.duplicateMap(globalCloneInCleaning);
        for (Map.Entry<String,HexagonalBase> currGlobalCloneEntry : tempGlobalCloneClone.entrySet()) {
            if (Arrays.asList(Materials.WATER,Materials.DESERT).contains(currGlobalCloneEntry.getValue().getMaterial())) {
                try {
                    globalCloneInCleaning.remove(currGlobalCloneEntry.getKey());
                } catch (Exception e) {
                    logger.severe("switchNearNumber: Unhandled exception in removing desert and water.");
                    e.printStackTrace();
                }
            }
        }
        String coordNumberInSwitching = this.tempCoupleOfNearHexagons[0].getHexAsPoint().toString();
        String coordAvailCoordForSwitching = globalCloneInCleaning.get(Utils.getMapFirstEntryKey(globalCloneInCleaning)).getHexAsPoint().toString();

        logger.info("switchNearNumber: switching numbers of coordinate: [" + coordNumberInSwitching + "], [" + coordAvailCoordForSwitching + "].");
        Numbers tempNumber1 = Numbers.fromString(globalMap.get(coordNumberInSwitching).getNumber().name());
        Numbers tempNumber2 = Numbers.fromString(globalMap.get(coordAvailCoordForSwitching).getNumber().name());
        globalMap.get(coordNumberInSwitching).setNumber(tempNumber2);
        globalMap.get(coordAvailCoordForSwitching).setNumber(tempNumber1);

        if (tempNumber1.equals(globalMap.get(coordNumberInSwitching).getNumber())
            || tempNumber2.equals(globalMap.get(coordAvailCoordForSwitching).getNumber())) {
            logger.severe("switchNearNumber: something went wrong in switching process. No switching done.");
        }

        //clearing the attribute that contains switching in cause.
        this.tempCoupleOfNearHexagons = new HexagonalBase[2];
    }

    //checking for island number
    public int countIslands(HashMap<String, HexagonalBase> globalMap) {
        HashMap<String, HexagonalBase> waterCleanedGlobalMap = Utils.duplicateMap(globalMap);
        for (Map.Entry<String,HexagonalBase> currEntry : globalMap.entrySet()) {
            HexagonalBase entryHexagon = currEntry.getValue();
            if (Materials.WATER.equals(entryHexagon.getMaterial())) {
                waterCleanedGlobalMap.remove(currEntry.getKey());
            }
        }

        int count = 0;
        int startingLevel;
        for (; !waterCleanedGlobalMap.isEmpty(); count++) {
            startingLevel = 0;
            ArrayList<HexagonalBase> associatedLand = new ArrayList<>();
            associatedLand.add(waterCleanedGlobalMap.get(Utils.getMapFirstEntryKey(waterCleanedGlobalMap)));
            associateNearLand(associatedLand, waterCleanedGlobalMap, startingLevel);
            if (count > 30) {
                break;
            }
        }

        return count;
    }

    private void associateNearLand(ArrayList<HexagonalBase> associatedLandList, HashMap<String, HexagonalBase> mapInSubject, int level) {
        level++;
        logger.info("associateNearLand: entering level [" + level + "]");
        ArrayList<HexagonalBase> tempHexagnalBaseToStore = new ArrayList<>();
        ArrayList<String> tempStringToStore = new ArrayList<>();

        for (HexagonalBase baseTogetNearTo : associatedLandList) {
            ArrayList<HexagonPoint> pointerAsList = Utils.listFromArray(baseTogetNearTo.getPointer());
            ArrayList<String> pointerStringAsList = new ArrayList<>();
            for (HexagonPoint tempPoint : pointerAsList) {
                pointerStringAsList.add(tempPoint.toString());
            }
            for (Map.Entry<String, HexagonalBase> mapEntry : mapInSubject.entrySet()) {
                if (pointerStringAsList.contains(mapEntry.getKey()) && !tempStringToStore.contains(mapEntry.getKey())) {
                    tempHexagnalBaseToStore.add(mapEntry.getValue());
                    tempStringToStore.add(mapEntry.getKey());
                }
            }
        }
        if (!tempHexagnalBaseToStore.isEmpty()) {
            for (HexagonalBase toStore : tempHexagnalBaseToStore) {
                mapInSubject.remove(toStore.getHexAsPoint().toString());
                associatedLandList.add(toStore);
            }
            logger.info("associateNearLand: entering next level of association.");
            associateNearLand(associatedLandList, mapInSubject, level);
        } else if (tempHexagnalBaseToStore.isEmpty() && level == 1 && associatedLandList.size() == 1) {
            logger.info("associateNearLand: processing banal island. Process ended. Returning.");
            mapInSubject.remove(associatedLandList.get(0).getHexAsPoint().toString());
        }
        else {
            logger.info("associateNearLand: rebuild whole island. Returning.");
        }
    }

}
