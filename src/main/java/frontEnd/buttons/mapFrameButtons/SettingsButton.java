package frontEnd.buttons.mapFrameButtons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SettingsButton extends JButton {
    public SettingsButton(ActionListener listenerFeRunner) {
        this.setText("settings");
        this.setPreferredSize(new Dimension(100,20));
        this.setBackground(new Color(0xC40303));
        this.setForeground(new Color(0xFFFFFFF));
        this.addActionListener(listenerFeRunner);
        this.setFocusPainted(false);
    }
}