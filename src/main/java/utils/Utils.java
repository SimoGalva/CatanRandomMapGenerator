package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Utils {
    public static int getMaxIndex(ArrayList<Integer> list) {
        int checkMax = -1;
        int ret = -1;
        if (!list.isEmpty()) {
            int countMaxIndex = -1;
            for (Integer currEntry : list) {
                countMaxIndex ++;
                if (currEntry > checkMax) {
                    checkMax = currEntry;
                    ret = countMaxIndex;
                }
            }
        }
        return ret;
    }

    public static <K,T> HashMap<K,T> duplicateMap(HashMap<K, T> mapToDuplicate) {
        HashMap<K, T> cloneMap = new HashMap<>();
        if (!mapToDuplicate.isEmpty()) {
           for (Map.Entry<K,T> currEntry : mapToDuplicate.entrySet()) {
               cloneMap.put(currEntry.getKey(), currEntry.getValue());
           }
        }
        return cloneMap;
    }
}
