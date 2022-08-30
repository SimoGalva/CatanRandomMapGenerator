package saving;

import hexagon.HexagonalBase;
import utils.Constants;
import utils.exceptions.LoadingException;
import utils.exceptions.LoadingFileException;
import utils.exceptions.SavingInFileException;
import utils.logging.LoggingClassesEnum;
import utils.logging.SyncedLogger;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Logger;

/** Note that this class completes its purpose by only using method createInstance:
 * depending on the String flagLoadSave "load"/"save" and the presence (or not) of the second parameters it decides if it has been instanced for
 * saving or loading from file and does it directly and will save the results in its attributes.
 * Then one can retrieve both the string or the map from the object.*/

public class MapSavingHandler implements GenericDataHandler{

    private static final Logger logger = SyncedLogger.getLogger(LoggingClassesEnum.MAP_SAVING_HANDLER);

    private HashMap<String, HexagonalBase> map;
    private String stringMap; //formatted map string, it needs to be deformatted

    public HashMap<String, HexagonalBase> getMap() {
        return map;
    }
    public void setMap(HashMap<String, HexagonalBase> map) {
        this.map = map;
    }

    public String getStringMap() {
        return stringMap;
    }
    public void setStringMap(String stringMap) {
        this.stringMap = stringMap;
    }

    //public constructor: handles the choice of correct constructor depending on the operation required
    public static MapSavingHandler createInstance(String flagLoadSave, HashMap<String, HexagonalBase> map) throws LoadingFileException, LoadingException, SavingInFileException {
        if (map == null && Constants.LOAD.equals(flagLoadSave)) {
            try {
                return new MapSavingHandler();
            } catch (Exception e) {
                logger.warning("Unhandled exception in loading data.");
                return null;
            }
        } else if (map != null && Constants.SAVE.equals(flagLoadSave)) {
            try {
                return new MapSavingHandler(map);
            } catch (Exception e) {
                logger.warning("Unhandled exception in saving data.");
                return null;
            }
        } else {
            logger.severe("Error in construction of MapSavingHandler. Invalid Params, returning null.");
            return null;
        }
    }

    //constructor for loading, cleans saved map to load a new one saved in map
    private MapSavingHandler() throws LoadingFileException, LoadingException {
        boolean check1;
        boolean check2;

        map = null;
        if (stringMap == null) {
            check1 = loadFromFile(SAVING_PATH + "/" + SAVING_FILE_NAME);
        } else {
            logger.warning("Deleting current saved strigMap. Loading a new data.");
            check1 = loadFromFile(SAVING_PATH + "/" + SAVING_FILE_NAME);
        }
        if (check1) {
            logger.info("New stringMap loaded from file correctly. Starting elaboration.");
            check2 = load();
            if (check2) {
                logger.info("Loading completed. Map has been correctly restored from saved data.");
            } else {
                logger.warning("Failed loading from stringMap to map. File may be corrupted. Retrying once.");
                if (load()) {
                    logger.info("Loading completed. Map has been correctly restored from saved data.");
                } else {
                    logger.severe("Unable to load data from stringMap, throwing exception.");
                    this.stringMap = null;
                    throw new LoadingException(LoadingException.MESSAGE);
                }
            }
        } else {
            logger.severe("Unable to load data from file, throwing exception.");
            this.stringMap = null;
            throw new LoadingFileException(LoadingFileException.MESSAGE);
        }
    }

    //costructor for saving, construct a map and cleans stringMap
    private MapSavingHandler(HashMap<String, HexagonalBase> map) throws SavingInFileException {
        this.map = map;
        this.stringMap = SavingFormatter.formatSavingMap(this.map);

        if (save()) {
            logger.info("Saving process ended.");
        } else {
            logger.warning("Saving did not work correctly. Retrying one more time.");
            if (save()) {
                logger.info("Saving process ended during second try.");
            } else {
                this.stringMap = null;
                this.map = null;
                throw new SavingInFileException(SavingInFileException.MESSAGE);
            }
        }
    }

    @Override
    public boolean save() {
        File savingFile = new File(SAVING_PATH + "/" + SAVING_FILE_NAME );

        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(savingFile));
            bufferedWriter.write(stringMap);

        } catch (IOException e) {
            System.out.println("Exception occurred in saving file: " + e.getMessage());
            return false;
        } finally {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (Exception ex) {/*:(*/}
            }
        }
        return true;
    }

    //impostato come load(path) così se si volesse introdurre la possibilità di chiedere il path in input lo si può gestire quasi totalmente gratis
    @Override
    public boolean load() {
        if (this.stringMap != null) {
            this.map = SavingFormatter.deformatSavedMap(this.stringMap);
            if (!Arrays.asList(Constants.PiecesForPlayers.PLAYER_3,Constants.PiecesForPlayers.PLAYER_4,Constants.PiecesForPlayers.PLAYER_5,Constants.PiecesForPlayers.PLAYER_6).contains(this.map.size())) {
                logger.severe("Loaded data do not correspond to a available map format.");
                this.map = null;
                this.stringMap = null;
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean loadFromFile(String path) {
        File loadingFile = new File(path);
        StringBuilder contentBuilder = new StringBuilder();

        try (BufferedReader buffer = new BufferedReader(new FileReader(loadingFile))) {
            String sCurrentLine;
            while ((sCurrentLine = buffer.readLine()) != null) {
                contentBuilder.append(sCurrentLine).append(" \n");
            }
        } catch (IOException e) {
            System.out.println("Exception occurred in loading from file: " + e.getMessage());
            return false;
        }
        this.stringMap = contentBuilder.toString();
        return true;
    }
}
