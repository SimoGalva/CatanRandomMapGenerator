package utils;

import utils.exceptions.UpdateException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class Utils {
    private static final Logger logger = Logger.getLogger(Utils.class.getName());

    public static int getMaxIndex(ArrayList<Integer> list) {
        int checkMax = -1;
        int ret = -1;
        if (!list.isEmpty()) {
            int countMaxIndex = -1;
            for (Integer currEntry : list) {
                countMaxIndex++;
                if (currEntry > checkMax) {
                    checkMax = currEntry;
                    ret = countMaxIndex;
                }
            }
        }
        return ret;
    }

    public static <K, T> HashMap<K, T> duplicateMap(HashMap<K, T> mapToDuplicate) {
        HashMap<K, T> cloneMap = new HashMap<>();
        if (!mapToDuplicate.isEmpty()) {
            for (Map.Entry<K, T> currEntry : mapToDuplicate.entrySet()) {
                cloneMap.put(currEntry.getKey(), currEntry.getValue());
            }
        }
        return cloneMap;
    }

    // thi method returns the keyValue of the first not null elemnt of the map.
    public static <K, T> K getMapFirstEntryKey(HashMap<K, T> map) {
        K key = null;
        for (Map.Entry<K, T> entry : map.entrySet()) {
            if (entry.getValue() != null) {
                key = entry.getKey();
                break;
            }
        }
        return key;
    }

    public static <K, T> void updateMap(HashMap<K, T> mapToUpdate, HashMap<K, T> newMap) throws UpdateException {
        if (!mapToUpdate.keySet().equals(newMap.keySet())) {
            throw new UpdateException("updateMap: " + UpdateException.MESSAGE);
        }
        for (K keyValue : mapToUpdate.keySet()) {
            mapToUpdate.put(keyValue, newMap.get(keyValue));
        }
    }
}
