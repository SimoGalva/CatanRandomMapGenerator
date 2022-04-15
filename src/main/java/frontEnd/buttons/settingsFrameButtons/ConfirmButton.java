package frontEnd.buttons.settingsFrameButtons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ConfirmButton extends JButton {
    public ConfirmButton(ActionListener... listener) {
        this.setText("confirm");
        this.setPreferredSize(new Dimension(365,20));
        this.setBackground(new Color(0xC40303));
        this.setForeground(new Color(0xFFFFFFF));
        this.setFocusPainted(false);
        for (int i = 0; i < listener.length; i++) {
            this.addActionListener(listener[i]);
        }
    }
}