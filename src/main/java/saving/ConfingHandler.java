package saving;

import utils.exceptions.GenericLoadingException;
import utils.exceptions.SavingInFileException;
import utils.logging.LoggingClassesEnum;
import utils.logging.SyncedLogger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

import static utils.Constants.NEXT_LINE;
import static utils.Constants.SEPARATOR;

public class ConfingHandler implements GenericDataHandler{
    private static final Logger logger = SyncedLogger.getLogger(LoggingClassesEnum.CONFING_SAVING_HANDLER);

    private int islandNumberSync;
    private int mainIslandNumberSync;
    private int mainIslandWeightSync;
    private int numberOfPlayerSync;

    private String confingString;

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
        this.confingString = confingString.toString();

        try {
            if (this.save(CONFIG_PATH)) ret = true;
            else ret = false;
        } catch (SavingInFileException e) {
            ret = false;
        }

        logger.info("Confing sync process ended.");
        return ret;
    }

    public boolean resumeConfing() {
        return true;
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
            bufferedWriter.write(confingString);

        } catch (IOException e) {
            System.out.println("Exception occurred in saving confing file: " + e.getMessage());
            throw new SavingInFileException(SavingInFileException.CONFING_MESSAGE);
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
        return false;
    }

    @Override
    public boolean loadFromFile(String path) throws GenericLoadingException {
        return false;
    }



    // Singleton instance implementation
    private static ConfingHandler singletonInstance = null;

    public static ConfingHandler getInstance() {
        return singletonInstance == null ? getInstanceSafely() : singletonInstance;
    }

    private static ConfingHandler getInstanceSafely() {
        if (singletonInstance == null) {
            singletonInstance = new ConfingHandler();
        }
        return singletonInstance;
    }
}
