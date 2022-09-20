package saving;

import engine.engineParams.Params;
import utils.exceptions.GenericLoadingException;
import utils.exceptions.SavingInFileException;
import utils.logging.LoggingClassesEnum;
import utils.logging.SyncedLogger;

import java.util.logging.Logger;

public class ConfingSavingHandler implements GenericDataHandler{
    private static final Logger logger = SyncedLogger.getLogger(LoggingClassesEnum.CONFING_SAVING_HANDLER);

    public static boolean forceConfingSync(Params params) {
        return false;
    }

    @Override
    public boolean save(String path) throws SavingInFileException {
        return false;
    }

    @Override
    public boolean load() throws GenericLoadingException {
        return false;
    }

    @Override
    public boolean loadFromFile(String path) throws GenericLoadingException {
        return false;
    }
}
