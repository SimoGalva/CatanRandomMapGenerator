package saving;

import coordinate.AbstractCoordinateHandler;
import hexagon.HexagonPoint;
import hexagon.HexagonalBase;
import hexagon.material.Materials;
import hexagon.number.Numbers;
import utils.Utils;
import utils.exceptions.LoadingException;
import utils.logging.LoggingClassesEnum;
import utils.logging.SyncedLogger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static utils.Constants.NEXT_LINE;
import static utils.Constants.SEPARATOR;

public class SavingFormatter {
    private static final Logger logger = SyncedLogger.getLogger(LoggingClassesEnum.SAVING_FORMATTER);

    public static String formatSavingMap(HashMap<String, HexagonalBase> map) {
        logger.info("Starting to format map.");

        StringBuilder formatted = new StringBuilder();
        String diagCoordStr;
        String rowCoordStr;
        String materialStr;
        String numberStr;
        String pointerDim;
        int i = 1;

        for (Map.Entry<String, HexagonalBase> entry : map.entrySet()) {
            diagCoordStr = entry.getKey().split(":")[0];
            rowCoordStr = entry.getKey().split(":")[1];
            materialStr = entry.getValue().getMaterial().toString();
            numberStr = entry.getValue().getNumber().toString();
            pointerDim = String.valueOf(entry.getValue().getPointer().length);
            if (map.entrySet().size() != i){
                formatted.append(diagCoordStr).append(SEPARATOR).append(rowCoordStr).append(SEPARATOR).append(materialStr).append(SEPARATOR).append(numberStr).append(SEPARATOR).append(pointerDim).append(SEPARATOR).append(NEXT_LINE);
            } else {
                formatted.append(diagCoordStr).append(SEPARATOR).append(rowCoordStr).append(SEPARATOR).append(materialStr).append(SEPARATOR).append(numberStr).append(SEPARATOR).append(pointerDim).append(SEPARATOR);
            }
            i++;
        }

        logger.info("Ended formatting map. Returning formatted String.");
        return formatted.toString();
    }

    public static HashMap<String, HexagonalBase> deformatSavedMap(String formattedMap) throws LoadingException {
        logger.info("Starting to deformat map String.");

        AbstractCoordinateHandler coordinateHandler = AbstractCoordinateHandler.getInstance();
        HashMap<String, HexagonalBase> deformattedMap = new HashMap<String, HexagonalBase>();

        String diagCoordStr;
        String rowCoordStr;
        String materialStr;
        String numberStr;
        String pointerDim;

        HexagonalBase tempHexagonalBase = null;
        Materials tempMaterial = null;
        Numbers tempNumber = null;
        HexagonPoint tempHexagonalPoint = null;
        int tempPointerDim = -1;
        String tempEntryKey = null;

        List<String> linesList;
        String[] linesArray = formattedMap.split(NEXT_LINE);
        linesList = Utils.listFromArray(linesArray);

        for (String line : linesList) {
            String[] lineData = line.split(SEPARATOR);

            diagCoordStr = lineData[0];
            rowCoordStr = lineData[1];
            materialStr = lineData[2];
            numberStr = lineData[3];
            pointerDim = lineData[4];

            tempHexagonalPoint = new HexagonPoint(diagCoordStr + ":" + rowCoordStr);
            tempEntryKey = tempHexagonalPoint.toString();
            tempMaterial = Materials.fromString(materialStr);
            tempNumber = Numbers.fromString(numberStr);
            tempPointerDim = Integer.parseInt(pointerDim);

            if (tempHexagonalPoint != null && tempEntryKey != null && tempMaterial != null && tempNumber != null && tempPointerDim >= 0) {
                tempHexagonalBase = HexagonalBase.createInstance(tempMaterial, tempNumber, tempPointerDim, tempHexagonalPoint);
                deformattedMap.put(tempEntryKey, tempHexagonalBase);
            } else {
                logger.warning("Something went wrong in deformatting String map: \n" +
                        "[" + formattedMap + "]. \n" +
                        "Throwing exception.");
                throw new LoadingException(LoadingException.MESSAGE);
            }

            tempHexagonalPoint = null;
            tempEntryKey = null;
            tempMaterial = null;
            tempNumber = null;
            tempPointerDim = -1;
        }

        logger.info("Ended formatting map. Returning rebuilt map.");
        return deformattedMap;
    }

    //this method is here for tests (to be implemented)
    public static String printMap(HashMap<String, HexagonalBase> deformattedMap) {
        StringBuilder byteString = new StringBuilder("printMap: \n");
        int i = 1;
        for (Map.Entry<String, HexagonalBase> entry : deformattedMap.entrySet()) {
            byteString.append(i+". coordinate: ["+entry.getKey()+"] has material ["+entry.getValue().getMaterial().toString()+"] with number: ["+entry.getValue().getNumber().toString()+"]; \n");
            i++;
        }
        System.out.println(byteString);
        return byteString.toString();
    }
}
