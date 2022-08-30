package saving;

public interface GenericDataHandler {
    /* todo: da implementare ho creato un interfaccia perchè sarebbe sensato anche salvare l'ultima confiugazione utilizzata per il lancio del programma così
        da non dover riempire tutte le righe ei dati ogni volta. Ci sarà al lancio del codice da implementare un syncConfig che fa la configurazione dei parametri.
        SaveFormatter avrà sia i metodi di fortmattazione della mappa sia della configurazione.
        ci sarà un saving handler per map e per config. Implementeranno quest'interfaccia.
    */
    public static final String configPath = System.getProperty("user.dir");
    public static final String configFileName = "CatMapConfig.config";
    public static final String savingFileName = "MapSavings.map";

    String savingPath = "C:/GitRep/cat-random-map-generator"; //temp: sarà il file config a deciderlo

    public boolean save();
    
    public boolean load();

    public boolean loadFromFile();
}
