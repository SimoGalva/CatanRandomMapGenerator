package utils;

import utils.exceptions.UpdateException;
import utils.logging.LoggingClassesEnum;
import utils.logging.SyncedLogger;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class Utils {
    private static final Logger logger = SyncedLogger.getLogger(LoggingClassesEnum.UTILS);

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

    public static <K> ArrayList<K> listFromArray(K[] arrayToList) {
        ArrayList<K> ret = new ArrayList<>();
        for (int i = 0; i < arrayToList.length; i++) {
            ret.add(arrayToList[i]);
        }
        return ret;
    }

    public static String getNextAvailableFileName(String folder, String baseName, String extension) {
        File dir = new File(folder);
        if (!dir.exists()) {
            dir.mkdirs(); // Crea la cartella se non esiste
        }

        int counter = 1;
        String fileName;
        File file;

        do {
            fileName = baseName + counter + "." + extension;
            file = new File(dir, fileName);
            counter++;
        } while (file.exists());

        return file.getAbsolutePath();
    }
}
