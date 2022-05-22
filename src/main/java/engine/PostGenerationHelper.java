package engine;

import hexagon.HexagonPoint;
import hexagon.HexagonalBase;
import hexagon.material.Materials;
import hexagon.number.Numbers;
import utils.Utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class PostGenerationHelper {
    private final Logger logger = Logger.getLogger(getClass().getName());

    HexagonalBase[] tempCoupleOfNearHexagons = new HexagonalBase[2];

    public boolean AreNearThoseOnList(HashMap<String, HexagonalBase> listToCheck) {
        //todo: implementare questo, deve ritornare un booleano e salvare nell'array con la prima coppia di numeri vicini. in modo da poterla recuperare a posteriori.
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
        //todo: funzionerà come segue: lui fa una copia di global map, cilca su sixAndEight e toglie dalla copia global tutti i pointer di tutti gli elementi della lista su cui cicla
        // quindi cicla sulle rimanenze e toglie tutto il mare  e deserto dalla copia. rimarra una lista di posizioni di dove è possibile mettere 6/8 ne prenderà una random.
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

        logger.info("switchNearNumber: switching nombers of coordinate: [" + coordNumberInSwitching + "], [" + coordAvailCoordForSwitching + "].");
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
}
