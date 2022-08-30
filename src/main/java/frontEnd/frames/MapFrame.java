package frontEnd.frames;

import frontEnd.buttons.mapFrameButtons.ModifyButton;
import frontEnd.buttons.mapFrameButtons.RefreshButton;
import frontEnd.buttons.mapFrameButtons.SaveButton;
import frontEnd.buttons.mapFrameButtons.SettingsButton;
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
    private JButton settingsButton;

    public MapFrame(ActionListener listenerFErunner) {
        super();
        mapPanel = new MapPanel();
        refreshButtonPanel = new JPanel();
        otherButtonsPanel = new JPanel();
        refreshButton = new RefreshButton(listenerFErunner);
        printButton = new ModifyButton();
        saveButton = new SaveButton(listenerFErunner);
        settingsButton = new SettingsButton(listenerFErunner);

        refreshButtonPanel.setLayout(new FlowLayout(FlowLayout.LEADING,5, 0));
        refreshButtonPanel.add(refreshButton);

        otherButtonsPanel.setLayout(new FlowLayout(FlowLayout.LEFT,5, 5));
        otherButtonsPanel.add(printButton);
        otherButtonsPanel.add(saveButton);
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
        mapPanel = new MapPanel();
        this.repaint();
    }
}
