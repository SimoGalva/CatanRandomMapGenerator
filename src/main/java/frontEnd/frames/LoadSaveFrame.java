package frontEnd.frames;

import frontEnd.buttons.commonButtons.ConfirmButton;
import frontEnd.buttons.loadSaveFrameButtons.DefaultLoadSavePathButton;
import frontEnd.inputLines.StringLine;
import utils.Constants;
import utils.exceptions.NotAPathException;
import utils.logging.LoggingClassesEnum;
import utils.logging.SyncedLogger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.logging.Logger;

import static utils.Constants.BACKGROUND_COLOR;
import static utils.Constants.ConstantsTextLines.FILE_NAME;
import static utils.Constants.ConstantsTextLines.PATH;

public class LoadSaveFrame extends JFrame {
    private static final Logger logger = SyncedLogger.getLogger(LoggingClassesEnum.SETTINGS_FRAME);

    private final JPanel paramsPanel;
    private final JPanel buttonPanel;
    private final JButton confirmButton;
    private final JButton defaultPathButton;
    private final StringLine pathLine;
    private final StringLine fileNameLine;



    private boolean isBeforeRun;

    public LoadSaveFrame(ActionListener listenerFeRunner, boolean isLoad, boolean isBeforeRun) {
        super();
        this.isBeforeRun = isBeforeRun;

        paramsPanel = new JPanel();
        buttonPanel = new JPanel();
        if (isLoad) {
            confirmButton = new ConfirmButton(Constants.ConstantsButtons.CONFIRM_BUTTON_LOAD, listenerFeRunner);
            defaultPathButton = new DefaultLoadSavePathButton(Constants.LOAD, listenerFeRunner);
        } else {
            confirmButton = new ConfirmButton(Constants.ConstantsButtons.CONFIRM_BUTTON_SAVE, listenerFeRunner);
            defaultPathButton = new DefaultLoadSavePathButton(Constants.SAVE, listenerFeRunner);
        }
        pathLine = new StringLine(PATH);
        fileNameLine = new StringLine(FILE_NAME);

        this.paramsPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                constraints.gridx = i;
                constraints.gridy = j;
                if (i == 1) {
                    if (j == 0) {
                        pathLine.setText("");
                        paramsPanel.add(pathLine, constraints);
                    } else {
                        fileNameLine.setText("");
                        paramsPanel.add(fileNameLine, constraints);
                    }
                } else {
                    if (j == 0) {
                        paramsPanel.add(new JLabel("Path: "), constraints);
                        constraints.gridx += 2;
                    } else {
                        paramsPanel.add(new JLabel("File Name: "), constraints);
                        constraints.gridx += 2;
                    }
                }
            }
        }

        this.buttonPanel.setLayout(new FlowLayout(FlowLayout.LEADING,10, 0));
        this.buttonPanel.add(confirmButton);
        this.buttonPanel.add(defaultPathButton);

        LayoutManager layout = new BorderLayout(-110,0);
        this.setLayout(layout);

        this.add(paramsPanel, BorderLayout.CENTER);
        this.add(buttonPanel,BorderLayout.SOUTH);

        paramsPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setBackground(BACKGROUND_COLOR);
        this.getContentPane().setBackground(BACKGROUND_COLOR);
        this.setPreferredSize(new Dimension(400,120));
        this.setResizable(false);
        if (isLoad) {
            this.setTitle("Load");
        } else {
            this.setTitle("Save");
        }
    }

    public boolean isThereNewContents() throws NotAPathException {
        String pathMayBe;
        boolean isTherePath = false;
        String fileNameMayBe;
        boolean isThereFileName = false;

        pathMayBe = this.pathLine.getString();
        Path pathToCheck = Paths.get(pathMayBe);
        if (pathMayBe != null && Files.exists(pathToCheck) && Files.isDirectory(pathToCheck)) {
            isTherePath = true;
        } else if (!Files.exists((pathToCheck))) {
            logger.info("Invalid Path, it doesen't exist.");
            throw new NotAPathException(NotAPathException.MESSAGE_PATH_DONT_EXIST);
        } else if (Files.exists((pathToCheck)) && !Files.isDirectory(pathToCheck)) {
            logger.info("Invalid Path, it isn't a directory.");
            throw new NotAPathException(NotAPathException.MESSAGE_ISNT_DIRECTORY);
        }
        fileNameMayBe = this.fileNameLine.getString();
        if (fileNameMayBe != null) {
            isThereFileName = true;
        }

        return isThereFileName && isTherePath;
    }

    public HashMap<String,String> retrieveContent() {
        HashMap<String, String> ret = new HashMap<String, String>();
        ret.put("PATH", pathLine.getString());
        ret.put("FILE_NAME", fileNameLine.getString());
        return ret;
    }

    public boolean isBeforeRun() {
        return isBeforeRun;
    }
}
