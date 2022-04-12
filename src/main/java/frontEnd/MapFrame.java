package frontEnd;

import frontEnd.buttons.ModifyButton;
import frontEnd.buttons.RefreshButton;
import frontEnd.buttons.SaveButton;
import frontEnd.buttons.SettingsButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MapFrame extends JFrame {
    private MapPanel mapPanel;
    private JPanel refreshButtonPanel;
    private JPanel otherButtonsPanel;
    private JButton refreshButton;
    private JButton printButton;
    private JButton saveButton;
    private JButton settingsButton;

    private final Color backgroundColor = new Color(0x6E6E72);

    public MapFrame(ActionListener listenerFErunner) {
        super();
        mapPanel = new MapPanel();
        refreshButtonPanel = new JPanel();
        otherButtonsPanel = new JPanel();
        refreshButton = new RefreshButton(listenerFErunner);
        printButton = new ModifyButton();
        saveButton = new SaveButton();
        settingsButton = new SettingsButton();

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

        refreshButtonPanel.setBackground(backgroundColor);
        otherButtonsPanel.setBackground(backgroundColor);
        this.getContentPane().setBackground(backgroundColor);
    }

    public void refreshMap() {
        mapPanel = new MapPanel();
        this.repaint();
    }
}
