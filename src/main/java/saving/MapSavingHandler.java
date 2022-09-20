package saving;

import hexagon.HexagonalBase;
import utils.Constants;
import utils.exceptions.GenericLoadingException;
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

    private static HashMap<String, HexagonalBase> map;
    private static String stringMap; //formatted map string, it needs to be deformatted
    private static String pathOverride = null;
    private static String fileNameOverride = null;

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

    //to call for save
    public static MapSavingHandler createInstance(String path, String fileName,String flagLoadSave, HashMap<String, HexagonalBase> map) throws GenericLoadingException, SavingInFileException {
        pathOverride = path;
        fileNameOverride = fileName.trim();
        return createInstance(flagLoadSave,map);
    }

    //to call for load
    public static MapSavingHandler createInstance(String path, String fileName,String flagLoadSave) throws GenericLoadingException, SavingInFileException {
        pathOverride = path;
        fileNameOverride = fileName.trim();
        return createInstance(flagLoadSave,null);
    }

    //public constructor: handles the choice of correct constructor depending on the operation required it uses default path, fileName
    public static MapSavingHandler createInstance(String flagLoadSave, HashMap<String, HexagonalBase> map) throws GenericLoadingException, SavingInFileException {
        if (map == null && Constants.LOAD.equals(flagLoadSave)) {
            try {
                return new MapSavingHandler();
            } catch (Exception e) {
                logger.warning("Unhandled exception in loading data.");
                throw e;
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
    private MapSavingHandler() throws GenericLoadingException {
            boolean check1;
            boolean check2;
            String path;
            String fileName;

            if (pathOverride == null) {
                path = SAVING_PATH;
            } else {
                path = pathOverride;
            }
            if (fileNameOverride == null) {
                fileName = SAVING_FILE_NAME;
            } else {
                fileName = fileNameOverride + EXTESION_MAP;
            }

            map = null;
            if (stringMap == null) {
                check1 = loadFromFile(path + "/" + fileName);
            } else {
                logger.warning("Deleting current saved strigMap. Loading a new data.");
                check1 = loadFromFile(path + "/" + fileName);
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

        String path;
        String fileName;

        if (pathOverride == null) {
            path = SAVING_PATH;
        } else {
            path = pathOverride;
        }
        if (fileNameOverride == null) {
            fileName = SAVING_FILE_NAME;
        } else {
            fileName = fileNameOverride + EXTESION_MAP;
        }

        if (save(path + "/" + fileName)) {
            logger.info("Saving process ended.");
        } else {
            logger.warning("Saving did not work correctly. Retrying one more time.");
            if (save(path + "/" + fileName)) {
                logger.info("Saving process ended during second try.");
            } else {
                this.stringMap = null;
                this.map = null;
                throw new SavingInFileException(SavingInFileException.MESSAGE);
            }
        }
    }

    //impostato come save(path) così se si volesse introdurre la possibilità di chiedere il path in input lo si può gestire quasi totalmente gratis
    @Override
    public boolean save(String path) {
        File savingFile = new File(path);

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

    @Override
    public boolean load() throws GenericLoadingException {
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

    //impostato come loadFromFile(path) così se si volesse introdurre la possibilità di chiedere il path in input lo si può gestire quasi totalmente gratis
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

    public static HashMap<String, HexagonalBase> getCurrentMap() {
        return map;
    }
}
