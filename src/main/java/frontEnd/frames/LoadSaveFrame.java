package frontEnd.frames;

import frontEnd.buttons.commonButtons.ConfirmButton;
import frontEnd.inputLines.StringLine;
import saving.MapSavingHandler;
import utils.Constants;
import utils.exceptions.NotAPathException;
import utils.logging.LoggingClassesEnum;
import utils.logging.SyncedLogger;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
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

    private final JPanel paramsPanel = new JPanel(new GridBagLayout());
    private final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));

    private final JButton confirmButton;
    private final JButton browseButton = new JButton("Browse...");

    private final StringLine pathLine = new StringLine(PATH);
    private final StringLine fileNameLine = new StringLine(FILE_NAME);

    private final boolean isBeforeRun;
    private final boolean isLoad;

    public LoadSaveFrame(ActionListener listenerFeRunner, boolean isLoad, boolean isBeforeRun) {
        super();
        this.isBeforeRun = isBeforeRun;
        this.isLoad = isLoad;
        this.pathLine.setText(MapSavingHandler.SAVING_PATH);

        if (isLoad) {
            this.confirmButton = new ConfirmButton(Constants.ConstantsButtons.CONFIRM_BUTTON_LOAD, listenerFeRunner);
        } else {
            this.confirmButton = new ConfirmButton(Constants.ConstantsButtons.CONFIRM_BUTTON_SAVE, listenerFeRunner);
        }

        setupBrowseAction();
        setupParamsPanel();
        setupButtonPanel();

        this.setLayout(new BorderLayout());
        this.add(paramsPanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);

        paramsPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setBackground(BACKGROUND_COLOR);
        this.getContentPane().setBackground(BACKGROUND_COLOR);

        this.setPreferredSize(new Dimension(430, isLoad ? 100 : 140));
        this.setResizable(false);
        this.setTitle(isLoad ? "Load" : "Save");
    }

    private void setupBrowseAction() {
        browseButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();

            String currentPath = pathLine.getString();
            File initialDir = (currentPath != null && !currentPath.isBlank()) ? new File(currentPath) : null;
            if (initialDir != null && initialDir.exists()) {
                chooser.setCurrentDirectory(initialDir);
            }

            if (isLoad) {
                chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                chooser.setFileFilter(new FileNameExtensionFilter("Map files", "map"));
                chooser.setAcceptAllFileFilterUsed(false);
                chooser.setDialogTitle("Select a .map file to load");

                int result = chooser.showOpenDialog(this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    Path selected = chooser.getSelectedFile().toPath();
                    pathLine.setText(selected.toString());
                }
            } else {
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                chooser.setDialogTitle("Select directory to save map");

                int result = chooser.showOpenDialog(this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    pathLine.setText(chooser.getSelectedFile().toPath().toString());
                }
            }
        });
    }

    private void setupParamsPanel() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        browseButton.setBackground(new Color(0xC40303)); // rosso
        browseButton.setForeground(new Color(0xFFFFFF)); // bianco

        // Path label + path field + browse
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        paramsPanel.add(new JLabel("Path:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 3.0;
        paramsPanel.add(pathLine, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0;
        paramsPanel.add(browseButton, gbc);

        // File name only for save
        if (!isLoad) {
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 1;
            gbc.weightx = 1.0;
            paramsPanel.add(new JLabel("File Name:"), gbc);

            gbc.gridx = 1;
            gbc.gridwidth = 2;
            paramsPanel.add(fileNameLine, gbc);
        }
    }

    private void setupButtonPanel() {
        buttonPanel.add(confirmButton);
    }

    public boolean isThereNewContents() throws NotAPathException {
        String pathMayBe = pathLine.getString();
        Path pathToCheck = Paths.get(isLoad ? pathMayBe : Paths.get(pathMayBe).toAbsolutePath().toString());

        if (!Files.exists(pathToCheck)) {
            boolean wasCreated = new File(pathMayBe).mkdirs();
        }

        if (isLoad) {
            if (!Files.isRegularFile(pathToCheck)) {
                throw new NotAPathException("Selected file does not exist or is not a file.");
            }
            return true;
        } else {
            if (!Files.isDirectory(pathToCheck)) {
                throw new NotAPathException(NotAPathException.MESSAGE_ISNT_DIRECTORY);
            }
            return fileNameLine.getString() != null && !fileNameLine.getString().isBlank();
        }
    }

    public HashMap<String, String> retrieveContent() {
        HashMap<String, String> ret = new HashMap<>();
        ret.put("PATH", pathLine.getString());
        ret.put("FILE_NAME", isLoad ? extractFileName(pathLine.getString()) : fileNameLine.getString());
        return ret;
    }

    private String extractFileName(String fullPath) {
        Path path = Paths.get(fullPath);
        return path.getFileName().toString();
    }

    public boolean isBeforeRun() {
        return isBeforeRun;
    }
}
