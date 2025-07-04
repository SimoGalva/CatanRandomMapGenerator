package frontEnd.frames;

import frontEnd.buttons.mapFrameButtons.*;
import frontEnd.panels.MapPanel;
import utils.logging.LoggingClassesEnum;
import utils.logging.SyncedLogger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import static utils.Constants.BACKGROUND_COLOR;

public class MapFrame extends JFrame {
    private static final Logger logger = SyncedLogger.getLogger(LoggingClassesEnum.MAP_FRAME);

    private MapPanel mapPanel;
    private JPanel refreshButtonPanel;
    private JPanel otherButtonsPanel;
    private JButton refreshButton;
    private JButton printButton;
    private JButton saveButton;
    private JButton loadButton;
    private JButton settingsButton;

    public MapFrame(ActionListener listenerFErunner) {
        super();
        mapPanel = new MapPanel();
        refreshButtonPanel = new JPanel();
        otherButtonsPanel = new JPanel();
        refreshButton = new RefreshButton(listenerFErunner);
        printButton = new PrintButton(listenerFErunner);
        saveButton = new SaveButton(listenerFErunner);
        loadButton = new LoadButton(listenerFErunner);
        settingsButton = new SettingsButton(listenerFErunner);

        refreshButtonPanel.setLayout(new FlowLayout(FlowLayout.LEADING,5, 0));
        refreshButtonPanel.add(refreshButton);

        otherButtonsPanel.setLayout(new FlowLayout(FlowLayout.LEFT,5, 5));
        otherButtonsPanel.add(printButton);
        otherButtonsPanel.add(saveButton);
        otherButtonsPanel.add(loadButton);
        otherButtonsPanel.add(settingsButton);

        LayoutManager layout = new BorderLayout(-110,0);
        this.setLayout(layout);

        this.add(mapPanel, BorderLayout.CENTER);
        this.add(refreshButtonPanel,BorderLayout.WEST);
        this.add(otherButtonsPanel, BorderLayout.NORTH);

        refreshButtonPanel.setBackground(BACKGROUND_COLOR);
        otherButtonsPanel.setBackground(BACKGROUND_COLOR);
        this.getContentPane().setBackground(BACKGROUND_COLOR);
        this.setTitle("Catan Map");
        this.setResizable(false);
    }

    public void refreshMap() {
        this.getContentPane().remove(mapPanel);  // Rimuovi il vecchio
        this.mapPanel = new MapPanel();;                // Assegna il nuovo
        this.getContentPane().add(mapPanel);     // Aggiungi il nuovo
        this.revalidate();                       // Ricalcola il layout
        this.repaint();                          // Ridisegna tutto
    }

    public JPanel getMapPanel() {
        return mapPanel;
    }
}
