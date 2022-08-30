package utils.logging;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class SyncedLogger {

    public static final String loggingPathGlobal = "C:/Users/simop/OneDrive/Documenti/Logs/logFile.log";
    public static FileHandler fileHandlerhGlobal;
    public static final String loggingPath = "C:/Users/simop/OneDrive/Documenti/Logs/";
    public static final String loggingPathExtension = ".log";

    public static Logger getLogger(LoggingClassesEnum className) {
        Logger ret = Logger.getLogger(className.getValue());
        String loggingPathSingle = loggingPath + className.getValue() + loggingPathExtension;

        try {

            FileHandler fileHandlerhGlobal = createOrGetGlobalHandler();;
            SimpleFormatter formatterGlobal = new SimpleFormatter();
            fileHandlerhGlobal.setFormatter(formatterGlobal);

            FileHandler fileHandlerhSingleton = new FileHandler(loggingPathSingle);
            SimpleFormatter formatterSingleton = new SimpleFormatter();
            fileHandlerhGlobal.setFormatter(formatterSingleton);

            ret.addHandler(fileHandlerhGlobal);
            ret.addHandler(fileHandlerhSingleton);
        } catch(Exception e) {
            //:(
        }
        return ret;
    }

    private static FileHandler createOrGetGlobalHandler() throws IOException {
        if (fileHandlerhGlobal == null) {
            File directory = new File(loggingPath);
            purgeDirectory(directory);
            fileHandlerhGlobal = new FileHandler(loggingPathGlobal);
        }
        return fileHandlerhGlobal;
    }

    private static void purgeDirectory(File dir) {
        for (File file: dir.listFiles()) {
            if (file.isDirectory())
                purgeDirectory(file);
            file.delete();
        }
    }
}
