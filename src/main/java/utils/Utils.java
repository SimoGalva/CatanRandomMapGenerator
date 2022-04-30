package utils;

import java.util.ArrayList;

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
}
