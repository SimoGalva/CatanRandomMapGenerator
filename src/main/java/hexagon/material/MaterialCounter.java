package hexagon.material;

import java.util.EnumMap;
import java.util.logging.Logger;

public class MaterialCounter {
    private final Logger logger = Logger.getLogger(getClass().getName());

    private final int COUNTER_RIVER = 2;
    private final int COUNTER_STONE = 6;
    private final int COUNTER_WOOD = 6;
    private final int COUNTER_CLAY = 6;
    private final int COUNTER_WOOL = 6;
    private final int COUNTER_HAY = 6;
    private final int COUNTER_WATER = 19;
    private final int COUNTER_DESERT = 3;

    //todo: implementare in modo simile una classe di gestione dei porti;
    private EnumMap<Materials, Integer> materialMap;

    private  MaterialCounter() {
        materialMap.put(Materials.RIVER,COUNTER_RIVER);
        materialMap.put(Materials.STONE,COUNTER_STONE);
        materialMap.put(Materials.WOOD,COUNTER_WOOD);
        materialMap.put(Materials.CLAY,COUNTER_CLAY);
        materialMap.put(Materials.WOOL,COUNTER_WOOL);
        materialMap.put(Materials.HAY,COUNTER_HAY);
        materialMap.put(Materials.WATER,COUNTER_WATER);
        materialMap.put(Materials.DESERT,COUNTER_DESERT);

    }

    public boolean consumeMaterial(Materials material) {
        if (material == null) {
            logger.warning("consumeMaterial: material is null. Returning false.");
            return false;
        }
        if (materialMap.get(material)-1 >= 0) {
            materialMap.put(material,materialMap.get(material)-1);
            logger.info("consumeMaterial: ["+material.toString()+"] has ["+ materialMap.get(material)+"] pieces left.");
            return true;
        } else {
            logger.info("consumeMaterial: ["+material.toString()+"] is over.");
            return false;
        }
    }




    //implementazione singleton instance
    private static MaterialCounter singletonInstance = null;

    public static MaterialCounter getInstance() {
        return singletonInstance != null ? singletonInstance : getInstanceSafely();
    }

    private static MaterialCounter getInstanceSafely() {
        if (singletonInstance == null) {
            singletonInstance = new MaterialCounter();
        }
        return singletonInstance;
    }
}
