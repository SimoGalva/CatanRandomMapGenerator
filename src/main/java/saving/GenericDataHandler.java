package saving;

import utils.exceptions.GenericLoadingException;
import utils.exceptions.SavingInFileException;

public interface GenericDataHandler {
    /* todo: da implementare ho creato un interfaccia perchè sarebbe sensato anche salvare l'ultima confiugazione utilizzata per il lancio del programma così
        da non dover riempire tutte le righe ei dati ogni volta. Ci sarà al lancio del codice da implementare un syncConfig che fa la configurazione dei parametri.
        SaveFormatter avrà sia i metodi di fortmattazione della mappa sia della configurazione.
        ci sarà un saving handler per map e per config. Implementeranno quest'interfaccia.
    */
    public static final String CONFIG_PATH = System.getProperty("user.dir") + "/confing";
    public static final String CONFIG_FILE_NAME = "CatMapConfig.cnfg";
    public static final String SAVING_FILE_NAME = "MapSavings.map";
    public static String SAVING_PATH = "C:/GitRep/cat-random-map-generator/savings";  //todo: fai in modo che sia deciso all'inizio dal file config
    public static final String EXTESION_MAP = ".map";

    public boolean save(String path) throws SavingInFileException;
    
    public boolean load() throws GenericLoadingException;

    public boolean loadFromFile(String path) throws GenericLoadingException;
}
