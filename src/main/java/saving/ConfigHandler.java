package saving;

import utils.exceptions.GenericLoadingException;
import utils.exceptions.LoadingException;
import utils.exceptions.LoadingFileException;
import utils.exceptions.SavingInFileException;
import utils.logging.LoggingClassesEnum;
import utils.logging.SyncedLogger;

import java.io.*;
import java.util.Arrays;
import java.util.logging.Logger;

import static saving.MapSavingHandler.SAVING_PATH;
import static utils.Constants.NEXT_LINE;
import static utils.Constants.SEPARATOR;

public class ConfigHandler implements GenericDataHandler{
    private static final Logger logger = SyncedLogger.getLogger(LoggingClassesEnum.CONFING_SAVING_HANDLER);

    private int islandNumberSync = 0;
    private int mainIslandNumberSync = 0;
    private int mainIslandWeightSync = 0;
    private int numberOfPlayerSync = 0;

    private String configString = "";

    //costruttore della signleton instance: lancia anche il caricamento dei dati
    private ConfigHandler(boolean isInLaunch) throws GenericLoadingException {
        try {
            if (loadFromFile(CONFIG_PATH)) {
                if (configString != null && !"".equals(configString)) {
                    logger.info("Config data loaded from file. Starting to load file content.");
                    if (load()) logger.info("Confing data correctly loaded from file and readable from class.");
                    else throw new LoadingException(LoadingException.CONFIG_MESSAGE);
                } else {
                    throw new LoadingFileException(LoadingFileException.CONFIG_MESSAGE);
                }
            } else {
                logger.warning("Failed to load data from file: file is corrupted or not found. Setting params to 0.");
            }
        } catch (GenericLoadingException e) {
            if (isInLaunch)
                throw e;
        }
    }

    public boolean forceConfingSync(int islandNumber, int mainIslandNumber, int mainIslandWeight, int numberOfPlayer) {
        logger.info("Starting confing sync process.");
        boolean ret;

        islandNumberSync = islandNumber;
        mainIslandNumberSync = mainIslandNumber;
        mainIslandWeightSync = mainIslandWeight;
        numberOfPlayerSync = numberOfPlayer;

        StringBuilder confingString = new StringBuilder();
        confingString.append(islandNumberSync).append(SEPARATOR).append(mainIslandNumberSync).append(SEPARATOR).append(mainIslandWeightSync).append(SEPARATOR).append(numberOfPlayer).append(SEPARATOR);
        confingString.append(NEXT_LINE).append(SAVING_PATH);
        this.configString = confingString.toString();

        try {
            if (this.save(CONFIG_PATH)) ret = true;
            else ret = false;
        } catch (SavingInFileException e) {
            ret = false;
        }

        logger.info("Confing sync process ended.");
        return ret;
    }

    public int[] retrieveData() {
        int[] ret = new int[4];
        ret[0] = islandNumberSync;
        ret[1] = mainIslandNumberSync;
        ret[2] = mainIslandWeightSync;
        ret[3] = numberOfPlayerSync;
        return ret;
    }

    @Override
    public boolean save(String path) throws SavingInFileException {
        //controlla che la cartella esista
        File directory = new File(String.valueOf(path));
        if (!directory.exists()) {
            directory.mkdir();
        }

        path = path + "/" + CONFIG_FILE_NAME;
        logger.info("Saving confing data in path [" + path + "].");
        File confingFile = new File(path);

        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(confingFile));
            bufferedWriter.write(configString);

        } catch (IOException e) {
            logger.warning("Exception occurred in saving confing file: " + e.getMessage());
            throw new SavingInFileException(SavingInFileException.CONFING_MESSAGE);
        } finally {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (Exception ex) {/*:(*/
                ex.printStackTrace();}
            }
        }

        return true;
    }

    @Override
    public boolean load() throws GenericLoadingException {
        logger.info("Starting to load content of the config file.");
        boolean ret = false;
        try {
            String[] decomposedConfigString = configString.split("\n");
            if (decomposedConfigString.length > 1) {
                String pathInConfig = decomposedConfigString[1].trim();
                File testDir = new File(pathInConfig);
                if (testDir.exists()) {
                    logger.info("Saving path changed with what found in config file: [" + pathInConfig + "]");
                    MapSavingHandler.setSavingPathFromConfig(pathInConfig);
                } else {
                    logger.info("Saving path in config file was not available. Using default [" + SAVING_PATH + "].");
                }
            }  else {
                logger.info("Saving path in config file not found. Using default [" + SAVING_PATH + "].");
            }

            String[] paramsList = decomposedConfigString[0].split(";");
            islandNumberSync = Integer.parseInt(paramsList[0]);
            mainIslandNumberSync = Integer.parseInt(paramsList[1]);
            mainIslandWeightSync = Integer.parseInt(paramsList[2]);
            numberOfPlayerSync = Integer.parseInt(paramsList[3]);
            if (Arrays.asList(islandNumberSync,mainIslandNumberSync,mainIslandWeightSync).contains(-100)) {
                logger.info("Last session was left with a loading. No params to load from config file. Setting everything to 0.");
                islandNumberSync = 0;
                mainIslandNumberSync = 0;
                mainIslandWeightSync = 0;
                numberOfPlayerSync = 0;
            }
            ret = true;
        } catch (NumberFormatException e) {
            ret = false;
            throw new LoadingException(LoadingException.CONFIG_MESSAGE);
        } finally {
            logger.info("Ended loading config file content.");
            configString = "";
        }
        return ret;
    }

    @Override
    public boolean loadFromFile(String path) throws GenericLoadingException {
        path = path + "/" + CONFIG_FILE_NAME;
        logger.info("Starting to load from file [" + path + "]");
        File loadingFile = new File(path);
        StringBuilder contentBuilder = new StringBuilder();

        try (BufferedReader buffer = new BufferedReader(new FileReader(loadingFile))) {
            String sCurrentLine;
            while ((sCurrentLine = buffer.readLine()) != null) {
                contentBuilder.append(sCurrentLine).append(" \n");
            }
        } catch (IOException e) {
            logger.warning("Exception occurred in loading config from file: " + e.getMessage());
            throw new LoadingFileException(LoadingFileException.CONFIG_MESSAGE);
        }
        this.configString = contentBuilder.toString();
        logger.info("Ended loading from file [" + path + "]");
        return true;
    }



    // Singleton instance implementation
    private static ConfigHandler singletonInstance = null;

    public static ConfigHandler getInstance() throws GenericLoadingException {
        return singletonInstance == null ? getInstanceSafely(false) : singletonInstance;
    }

    public static ConfigHandler getInstance(boolean isInLaunch) throws GenericLoadingException {
        return singletonInstance == null ? getInstanceSafely(isInLaunch) : singletonInstance;
    }

    private static ConfigHandler getInstanceSafely(boolean isInLaunch) throws GenericLoadingException {
        if (singletonInstance == null) {
            singletonInstance = new ConfigHandler(isInLaunch);
        }
        return singletonInstance;
    }

    public static void clearSingletonInstance() {
        singletonInstance = null;
    }
}
