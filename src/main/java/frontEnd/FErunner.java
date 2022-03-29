package frontEnd;

import frontEnd.buttons.ModifyButton;
import frontEnd.buttons.RefreshButton;
import frontEnd.buttons.SaveButton;

import javax.swing.*;
import java.awt.*;

public class FErunner implements Runnable{
    @Override
    public void run() {        JFrame frame = new JFrame();

        MapPanel mapPanel = new MapPanel();
        JPanel refreshButtonPanel = new JPanel();
        JPanel otherButtonsPanel = new JPanel();
        JButton refreshButton = new RefreshButton();
        JButton printButton = new ModifyButton();
        JButton saveButton = new SaveButton();

        refreshButtonPanel.setLayout(new FlowLayout(FlowLayout.LEADING,5, 0));
        refreshButtonPanel.add(refreshButton);

        otherButtonsPanel.setLayout(new FlowLayout(FlowLayout.LEFT,5, 5));
        otherButtonsPanel.add(printButton);
        otherButtonsPanel.add(saveButton);

        LayoutManager layout = new BorderLayout(-110,0);
        frame.setLayout(layout);

        frame.add(mapPanel, BorderLayout.CENTER);
        frame.add(refreshButtonPanel,BorderLayout.WEST);
        frame.add(otherButtonsPanel, BorderLayout.NORTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
