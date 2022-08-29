package globalMap;

import hexagon.HexagonPoint;
import hexagon.HexagonalBase;
import hexagon.material.Materials;
import hexagon.number.Numbers;
import utils.Utils;
import utils.exceptions.UpdateException;
import utils.logging.LoggingClassesEnum;
import utils.logging.SyncedLogger;
import utils.pojo.DiagSettingsHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class GlobalMapHandler {
    private static final Logger logger = SyncedLogger.getLogger(LoggingClassesEnum.GLOBAL_MAP_HANDLER);
    public static HashMap<String, HexagonalBase> globalMap = new HashMap<>();

    public static void populateMap(HexagonalBase hexagonalBase) {
        globalMap.put(hexagonalBase.getHexAsPoint().toString(),hexagonalBase);
    }

    public static HashMap<String, HexagonalBase> getGlobalMap() {
        return globalMap;
    }


    public static void updateMap(HashMap<String, HexagonalBase> newMap) {
        if (newMap.size() == globalMap.size()) {
            logger.info("updateMap: updating map with new one.");
            try {
                Utils.updateMap(globalMap, newMap);
            } catch (UpdateException e) {
                logger.severe(e.getMessage());
            }
        } else {
            logger.warning("updateMap: update failed, incompatible sizes. Current map size [" + globalMap.size() + "], new map size [" + newMap.size() + "] ");
        }
    }

    public static String printMap() {
        StringBuilder byteString = new StringBuilder("printMap: \n");
        int i = 1;
        for (Map.Entry<String, HexagonalBase> entry : globalMap.entrySet()) {
            byteString.append(i+". coordinate: ["+entry.getKey()+"] has material ["+entry.getValue().getMaterial().toString()+"] with number: ["+entry.getValue().getNumber().toString()+"]; \n");
            i++;
        }
        System.out.println(byteString);
        return byteString.toString();
    }

    //questo metodo popola gli esagono estremanti con WATER (-4,0), (4,0) nel caso di 4Player
    public static void populateLimitWaterHexagons(int numberOfPlayer) {
        switch (numberOfPlayer ) {
            case 3:
                populateMap(HexagonalBase.createInstance(Materials.WATER, Numbers.M_ONE, new HexagonPoint(-3,0)));
                populateMap(HexagonalBase.createInstance(Materials.WATER, Numbers.M_ONE, new HexagonPoint(4,0)));
                break;
            case 4:
                populateMap(HexagonalBase.createInstance(Materials.WATER, Numbers.M_ONE, new HexagonPoint(-4,0)));
                populateMap(HexagonalBase.createInstance(Materials.WATER, Numbers.M_ONE, new HexagonPoint(4,0)));
                break;
            case 5:
                populateMap(HexagonalBase.createInstance(Materials.WATER, Numbers.M_ONE, new HexagonPoint(-4,0)));
                populateMap(HexagonalBase.createInstance(Materials.WATER, Numbers.M_ONE, new HexagonPoint(5,0)));
                break;
            case 6:
                populateMap(HexagonalBase.createInstance(Materials.WATER, Numbers.M_ONE, new HexagonPoint(-5,0)));
                populateMap(HexagonalBase.createInstance(Materials.WATER, Numbers.M_ONE, new HexagonPoint(5,0)));
                break;
        }
        printMap();
    }

    public static ArrayList<HexagonalBase> getSeaHexagons() {
        ArrayList<HexagonalBase> ret = new ArrayList<>();
        for (Map.Entry<String, HexagonalBase> mapEntry : globalMap.entrySet()) {
            if (Materials.WATER.equals(mapEntry.getValue().getMaterial())) {
                ret.add(mapEntry.getValue());
            }
        }
        return ret;
    }

    public static void switchHexagons(HexagonalBase hexagonToSwitch, HexagonalBase seaHexagonToSwitch) {
        String tempHexagonToSwitch = hexagonToSwitch.getHexAsPoint().toString();
        String tempSeaHexagonToSwitch = seaHexagonToSwitch.getHexAsPoint().toString();
        hexagonToSwitch.setHexAsPoint(tempSeaHexagonToSwitch);
        seaHexagonToSwitch.setHexAsPoint(tempHexagonToSwitch);

        globalMap.put(tempHexagonToSwitch, seaHexagonToSwitch);
        globalMap.put(tempSeaHexagonToSwitch, hexagonToSwitch);
    }

    public static DiagSettingsHolder calculateDiagSettings() {
        switch (globalMap.size()) {
            case 44: // 3 giocatori
                return new DiagSettingsHolder(1,0);
            case 51: // 4 giocatori
                return new DiagSettingsHolder(0,0);
            case 58: // 5 giocatori
                return new DiagSettingsHolder(0,1);
            case 65: // 6 giocatori
                return new DiagSettingsHolder(-1,1);
            default: // errore da qualche parte
                return null;
        }
    }

    public static int calculateNumberOfPlayerForFront() {
        switch (globalMap.size()) {
            case 44: // 3 giocatori
                return 3;
            case 51: // 4 giocatori
                return 4;
            case 58: // 5 giocatori
                return 5;
            case 65: // 6 giocatori
                return 6;
            default: // errore da qualche parte
                return 0;
        }
    }

    public static int calculateRadiuos() {
        switch (globalMap.size()) {
            case 44: // 3 giocatori
                return 450;
            case 51: // 4 giocatori
                return 450;
            case 58: // 5 giocatori
                return 450;
            case 65: // 6 giocatori
                return 550;
            default: // errore da qualche parte
                return 0;
        }
    }

    public static void clear() {
        globalMap.clear();
    }
}
