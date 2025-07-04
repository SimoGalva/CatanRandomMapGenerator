package frontEnd.frames;

import frontEnd.buttons.BrowseButton;
import frontEnd.buttons.commonButtons.ConfirmButton;
import saving.MapSavingHandler;
import utils.Constants;
import utils.logging.LoggingClassesEnum;
import utils.logging.SyncedLogger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.nio.file.Paths;
import java.util.logging.Logger;

import static utils.Constants.BACKGROUND_COLOR;

public class PathChoiceFrame extends JFrame {
    private static final Logger logger = SyncedLogger.getLogger(LoggingClassesEnum.PATH_CHOICE_FRAME);
    private JTextField pathField;

    public PathChoiceFrame(ActionListener listenerFeRunner) {
        setTitle("Scegli cartella di salvataggio");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 120);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));


        pathField = new JTextField(MapSavingHandler.SAVING_PATH);
        JButton browseButton = new BrowseButton(pathField);
        JButton confirmButton = new ConfirmButton(Constants.ConstantsButtons.CONFIRM_BUTTON_PATH_SELECTION, listenerFeRunner);

        // Pannello centrale per campo + bottone sfoglia
        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        JPanel topFieldPanel = new JPanel(new BorderLayout(5, 5));
        topFieldPanel.setBackground(BACKGROUND_COLOR);
        JLabel label = new JLabel("Choose the folder for saving files:");
        label.setForeground(Color.WHITE); // se vuoi colore testo bianco
        topFieldPanel.add(label, BorderLayout.NORTH);
        topFieldPanel.add(pathField, BorderLayout.CENTER);
        topFieldPanel.add(browseButton, BorderLayout.EAST);

        inputPanel.add(topFieldPanel, BorderLayout.CENTER);

        // Pannello inferiore per conferma
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        buttonPanel.add(confirmButton);

        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);


        buttonPanel.setBackground(BACKGROUND_COLOR);
        inputPanel.setBackground(BACKGROUND_COLOR);
        this.getContentPane().setBackground(BACKGROUND_COLOR);
    }

    public void updatePath(){
        try{
            String pathInserted = pathField.getText();
            Paths.get(pathInserted); // checks if the path makes sense, not necessary exists
            MapSavingHandler.SAVING_PATH = pathInserted;
            logger.info("Updated saving path: " + MapSavingHandler.SAVING_PATH);
        } catch (Exception e) {
            logger.warning("No valid path inserted from the user. Using default: " + MapSavingHandler.SAVING_PATH);
        }
        return;
    }
}
