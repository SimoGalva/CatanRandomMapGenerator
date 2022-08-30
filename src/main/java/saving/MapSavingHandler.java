package saving;

import hexagon.HexagonalBase;
import utils.Constants;
import utils.exceptions.LoadingFileException;
import utils.logging.LoggingClassesEnum;
import utils.logging.SyncedLogger;

import java.util.HashMap;
import java.util.logging.Logger;

public class MapSavingHandler implements GenericDataHandler{

    private static final Logger logger = SyncedLogger.getLogger(LoggingClassesEnum.MAP_SAVING_HANDLER);

    private HashMap<String, HexagonalBase> map;
    private String stringMap; //formatted map string, it needs to be deformatted

    //public constructor: handle the choice of correct constructor depending on the operation required
    private static MapSavingHandler createInstance(String flagLoadSave, HashMap<String, HexagonalBase> map) throws LoadingFileException{
        if (map == null && Constants.LOAD.equals(flagLoadSave)) {
            return new MapSavingHandler(map);
        } else if (map != null && Constants.SAVE.equals(flagLoadSave)) {
            return new MapSavingHandler(map);
        } else {
            logger.severe("Error in construction of MapSavingHandler. Invalid Params, returning null.");
            return null;
        }
    }

    //constructor for loading, cleans saved map to load a new one saved in map
    private MapSavingHandler() throws LoadingFileException {
        boolean check1;
        boolean check2;

        map = null;
        if (stringMap == null) {
            check1 = loadFromFile();
        } else {
            logger.warning("Deleting current saved strigMap. Loading a new data.");
            check1 = loadFromFile();
        }
        if (check1) {
            logger.info("New stringMap loaded from file correctly. Starting elaboration.");
            check2 = load();
            if (check2) {
                logger.info("Loading completed. Map has been correctly restored from saved data.");
            }
        } else {
            logger.severe("Unable to load data from file, throwing exception.");
            this.stringMap = null;
            throw new LoadingFileException(LoadingFileException.MESSAGE);
        }
    }

    //costructor for saving, construct a map and cleans stringMap
    private MapSavingHandler(HashMap<String, HexagonalBase> map) {
        this.map = map;
    }

    @Override
    public boolean save() {

        return false;
    }

    @Override
    public boolean load() { // si deve occupare di scrivere la mappa dalla stringa
        return false;
    }

    @Override
    public boolean loadFromFile() { // si deve occupare di recuperare la stringa da File
        return false;
    }
}
