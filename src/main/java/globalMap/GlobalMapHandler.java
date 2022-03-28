package globalMap;

import hexagon.HexagonPoint;
import hexagon.HexagonalBase;
import hexagon.material.Materials;
import hexagon.number.Numbers;

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

    public static void printMap() {
        StringBuilder byteString = new StringBuilder("printMap: \n");
        int i = 1;
        for (Map.Entry<String, HexagonalBase> entry : globalMap.entrySet()) {
            byteString.append(i+". coordinate: ["+entry.getKey()+"] has material ["+entry.getValue().getMaterial().toString()+"] with number: ["+entry.getValue().getNumber().toString()+"]; \n");
            i++;
        }
        System.out.println(byteString);
    }

    public static void doPostGeneratingFixing(int numberOfPlayer) {
        if (numberOfPlayer == 4) {
            populateMap(HexagonalBase.createInstance(Materials.WATER, Numbers.M_ONE, new HexagonPoint(-4,0)));
            populateMap(HexagonalBase.createInstance(Materials.WATER, Numbers.M_ONE, new HexagonPoint(4,0)));
        }
    }
}
