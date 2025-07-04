package frontEnd.frames;

import frontEnd.buttons.BrowseButton;
import frontEnd.buttons.commonButtons.ConfirmButton;
import utils.Constants;
import utils.logging.LoggingClassesEnum;
import utils.logging.SyncedLogger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
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


        pathField = new JTextField(System.getProperty("user.dir") + File.separator + "config");
        JButton browseButton = new BrowseButton(pathField, listenerFeRunner);
        JButton confirmButton = new ConfirmButton(Constants.ConstantsButtons.CONFIRM_BUTTON_PATH_SELECTION, listenerFeRunner);

        // Pannello centrale per campo + bottone sfoglia
        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        inputPanel.add(pathField, BorderLayout.CENTER);
        inputPanel.add(browseButton, BorderLayout.EAST);

        // Pannello inferiore per conferma
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        buttonPanel.add(confirmButton);

        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);


        buttonPanel.setBackground(BACKGROUND_COLOR);
        inputPanel.setBackground(BACKGROUND_COLOR);
        this.getContentPane().setBackground(BACKGROUND_COLOR);
    }
}
