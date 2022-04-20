package globalMap;

import hexagon.HexagonPoint;
import hexagon.HexagonalBase;
import hexagon.material.Materials;
import hexagon.number.Numbers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GlobalMapHandler {
    public static HashMap<String, HexagonalBase> globalMap = new HashMap<>();

    public static void populateMap(HexagonalBase hexagonalBase) {
        globalMap.put(hexagonalBase.getHexAsPoint().toString(),hexagonalBase);
    }

    public static HashMap<String, HexagonalBase> getGlobalMap() {
        return globalMap;
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
        if (numberOfPlayer == 4) {
            populateMap(HexagonalBase.createInstance(Materials.WATER, Numbers.M_ONE, new HexagonPoint(-4,0)));
            populateMap(HexagonalBase.createInstance(Materials.WATER, Numbers.M_ONE, new HexagonPoint(4,0)));
        }
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

    public static int calculateSize() {
        //todo: sostituire i -1 con i valori corretti
        switch (globalMap.size()) {
            case 44: // 3 giocatori
                return -1;
            case 51: // 4 giocatori
                return 9;
            case 58: // 5 giocatori
                return -1;
            case 65: // 6 giocatori
                return -1;
            default: // errore da qualche parte
                return 0;
        }
    }

    public static void clear() {
        globalMap.clear();
    }
}
