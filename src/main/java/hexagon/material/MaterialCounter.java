package hexagon.material;

import utils.NoInstanceException;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;
import java.util.logging.Logger;

public class MaterialCounter {
    private static final Logger logger = Logger.getLogger(MaterialCounter.class.getName());

    private int COUNTER_RIVER;
    private int COUNTER_STONE;
    private int COUNTER_WOOD;
    private int COUNTER_CLAY;
    private int COUNTER_WOOL;
    private int COUNTER_HAY;
    private int COUNTER_DESERT;
    private int COUNTER_WATER;

    private int totalLand;

    //todo: implementare in modo simile una classe di gestione dei porti;
    private EnumMap<Materials, Integer> materialMap;

    private  MaterialCounter(int numberOfPlayer) {
        this.setConfig(numberOfPlayer);
        materialMap = new EnumMap<Materials, Integer>(Materials.class);
        materialMap.put(Materials.RIVER,COUNTER_RIVER);
        materialMap.put(Materials.STONE,COUNTER_STONE);
        materialMap.put(Materials.WOOD,COUNTER_WOOD);
        materialMap.put(Materials.CLAY,COUNTER_CLAY);
        materialMap.put(Materials.WOOL,COUNTER_WOOL);
        materialMap.put(Materials.HAY,COUNTER_HAY);
        materialMap.put(Materials.WATER,COUNTER_WATER);
        materialMap.put(Materials.DESERT,COUNTER_DESERT);
        //todo: si potrebbe mettere in input una selezione della verione del catan così da creare scenari da 4 con i pezzi da 6 nel caso si disponga della ver 6 giocatori del gioco.
        if (numberOfPlayer == 3 || numberOfPlayer == 5 || numberOfPlayer == 6) {
            this.adaptMaterialNumber(numberOfPlayer);
        }
        int tempTotalLand = 0;
        for (Map.Entry<Materials, Integer> currEntry : this.materialMap.entrySet()) {
            if (Materials.WATER.equals(currEntry.getKey())) {
                continue;
            }
            tempTotalLand = tempTotalLand + currEntry.getValue();
        }
        this.totalLand = tempTotalLand;
        logger.info("MaterialCounter: total land ["+this.totalLand+"]");
    }

    private void setConfig(int numberOfPlayer) {
        switch (numberOfPlayer) {
            case 3:
            case 4:
                COUNTER_RIVER = 2;
                COUNTER_STONE = 5;
                COUNTER_WOOD = 5;
                COUNTER_CLAY = 5;
                COUNTER_WOOL = 5;
                COUNTER_HAY = 5;
                COUNTER_DESERT = 3;
                COUNTER_WATER = 19;
                break;
            case 5:
            case 6:
                COUNTER_RIVER = 4;
                COUNTER_STONE = 7;
                COUNTER_WOOD = 7;
                COUNTER_CLAY = 7;
                COUNTER_WOOL = 7;
                COUNTER_HAY = 7;
                COUNTER_DESERT = 4;
                COUNTER_WATER = 26;
                break;
            default:
                COUNTER_RIVER = 0;
                COUNTER_STONE = 0;
                COUNTER_WOOD = 0;
                COUNTER_CLAY = 0;
                COUNTER_WOOL = 0;
                COUNTER_HAY = 0;
                COUNTER_DESERT = 0;
                COUNTER_WATER = 0;
                break;
        }
    }

    private void adaptMaterialNumber(int numberOfPlayer) {
        MaterialHandler handler = new MaterialHandler();
        ArrayList<Materials> endedMaterialsToSkip = new ArrayList<>();
        if (numberOfPlayer == 3) {
            for (int i = 49; i > 42; i--) {
                for (Map.Entry<Materials,Integer> currEntry : this.materialMap.entrySet()) {
                    if (currEntry.getValue() == 0) {
                        endedMaterialsToSkip.add(currEntry.getKey());
                    }
                }
                Materials matirialToConsume = handler.pickRandomMaterial(MaterialHandler.LAND_WATER);
                if (!endedMaterialsToSkip.contains(matirialToConsume)) {
                    this.consumeMaterial(matirialToConsume);
                } else {
                    i++;
                }
            }
        } else if (numberOfPlayer == 5) {
            //è necessario togliere sempre almeno una casella non -1 pechè da gico ci sono 39 terreni produttivi e 38 numeri utili, essendo
            //molti i pezzi da togliere è improbabile che non venga tolto nemmeno un terreno produttivo.
            for (int i = 69; i > 56; i--) {
                for (Map.Entry<Materials,Integer> currEntry : this.materialMap.entrySet()) {
                    if (currEntry.getValue() == 0) {
                        endedMaterialsToSkip.add(currEntry.getKey());
                    }
                }
                Materials matirialToConsume = handler.pickRandomMaterial(MaterialHandler.LAND_WATER);
                if (!endedMaterialsToSkip.contains(matirialToConsume)) {
                    this.consumeMaterial(matirialToConsume);
                } else {
                    i++;
                }
            }
        } else if (numberOfPlayer == 6) {
            for (int i = 69; i > 63; i--) {
                for (Map.Entry<Materials,Integer> currEntry : this.materialMap.entrySet()) {
                    if (currEntry.getValue() == 0) {
                        endedMaterialsToSkip.add(currEntry.getKey());
                    }
                }
                //è necessario togliere sempre almeno una casella non -1 pechè da gico ci sono 39 terreni produttivi e 38 numeri utili.
                if (i == 69) {
                    this.consumeMaterial(handler.pickRandomMaterial(MaterialHandler.LAND));
                } else {
                    Materials matirialToConsume = handler.pickRandomMaterial(MaterialHandler.LAND_WATER);
                    if (!endedMaterialsToSkip.contains(matirialToConsume)) {
                        this.consumeMaterial(matirialToConsume);
                    } else {
                        i++;
                    }
                }
            }
        }
        int countMaterial = 0;
        for (Map.Entry<Materials,Integer> curEntry : this.materialMap.entrySet()) {
            countMaterial ++;
        }
        logger.info("adaptMaterialNumber: ["+numberOfPlayer+"] players, ["+countMaterial+"] materials available.");
    }

    public boolean consumeMaterial(Materials material) {
        if (material == null) {
            logger.warning("consumeMaterial: material is null. Returning false.");
            return false;
        } else {
            if (materialMap.get(material) - 1 >= 0) {
                materialMap.put(material, materialMap.get(material) - 1);
                logger.info("consumeMaterial: [" + material.toString() + "] has [" + materialMap.get(material) + "] pieces left.");
                return true;
            } else {
                logger.info("consumeMaterial: [" + material.toString() + "] is over.");
                return false;
            }
        }
    }

    public void printRemains() {
        StringBuilder byteString = new StringBuilder("getRemains: \n");
        for (Map.Entry<Materials, Integer> entry : this.materialMap.entrySet()) {
            byteString.append("- material ["+entry.getKey()+"] has ["+entry.getValue()+"] pieces left; \n");
        }
        logger.info(byteString.toString());
    }



    //implementazione singleton instance
    private static MaterialCounter singletonInstance = null;

    public static MaterialCounter getInstance() throws NoInstanceException {
        if (singletonInstance == null) {
            NoInstanceException exception = new NoInstanceException("MaterialCounter.getInstance: " + NoInstanceException.MESSAGE);
            throw exception;
        }
        return singletonInstance;
    }

    public static MaterialCounter createInstance(int numberOfPlayer) {
        return singletonInstance != null ? singletonInstance : getInstanceSafely(numberOfPlayer);
    }

    private static MaterialCounter getInstanceSafely(int numberOfPlayer) {
        if (singletonInstance == null) {
            singletonInstance = new MaterialCounter(numberOfPlayer);
        }
        return singletonInstance;
    }

    public static void clearSingletonInstance() {
        singletonInstance = null;
    }

    public int getTotalLand() {
        return this.totalLand;
    }
}
