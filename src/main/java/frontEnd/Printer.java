package frontEnd;

import saving.MapSavingHandler;
import utils.logging.LoggingClassesEnum;
import utils.logging.SyncedLogger;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.logging.Logger;

import static utils.Constants.SCREENSHOT;
import static utils.Utils.getNextAvailableFileName;


public class Printer {
    private static final Logger logger = SyncedLogger.getLogger(LoggingClassesEnum.PRINTER);

    public static String folderPath = MapSavingHandler.SAVING_PATH;
    private static final String PNG = "png";

    public static void saveImage(JPanel mapToPrint) {
        int width = mapToPrint.getWidth();
        int height = mapToPrint.getHeight();

        if (width <= 0 || height <= 0) {
            logger.severe("Invaid dimensions. Nothing to print.");
            return;
        }

        logger.info("Printing process starting");
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();
        mapToPrint.paintAll(g2);
        g2.dispose();

        try {
            String filePath = getNextAvailableFileName(folderPath, SCREENSHOT, PNG);
            ImageIO.write(image, "png", new File(filePath));
            logger.info("Printing process ended. Saved in" + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
