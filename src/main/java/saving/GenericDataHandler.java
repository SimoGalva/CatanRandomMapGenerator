package saving;

import utils.exceptions.GenericLoadingException;
import utils.exceptions.SavingInFileException;

public interface GenericDataHandler {

    //public static final String CONFIG_PATH = System.getProperty("user.dir") + "/confing";
    public static final String CONFIG_PATH = System.getProperty("user.home") + "/.catmap/config";
    public static final String CONFIG_FILE_NAME = "CatMapConfig.cnfg";
    public static final String SAVING_FILE_NAME = "MapSavings.map";
    public static final String EXTESION_MAP = ".map";

    public boolean save(String path) throws SavingInFileException;
    
    public boolean load() throws GenericLoadingException;

    public boolean loadFromFile(String path) throws GenericLoadingException;
}
