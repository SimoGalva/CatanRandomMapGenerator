package frontEnd.buttons.mapFrameButtons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoadButton extends JButton {
    public LoadButton(ActionListener listenerFeRunner) {
        this.setText("load");
        this.setPreferredSize(new Dimension(100,20));
        this.setBackground(new Color(0xC40303));
        this.setForeground(new Color(0xFFFFFFF));
        this.setFocusPainted(false);
        this.addActionListener(listenerFeRunner);
    }
}
