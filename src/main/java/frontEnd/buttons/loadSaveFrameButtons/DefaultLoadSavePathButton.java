package frontEnd.buttons.loadSaveFrameButtons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class DefaultLoadSavePathButton extends JButton {
    public DefaultLoadSavePathButton(ActionListener... listener) {
        this.setText("use default");
        this.setPreferredSize(new Dimension(180, 20));
        this.setBackground(new Color(0xC40303));
        this.setForeground(new Color(0xFFFFFFF));
        this.setFocusPainted(false);
        for (int i = 0; i < listener.length; i++) {
            this.addActionListener(listener[i]);
        }
    }
}
