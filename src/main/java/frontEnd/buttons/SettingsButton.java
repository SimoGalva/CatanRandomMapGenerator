package frontEnd.buttons;

import javax.swing.*;
import java.awt.*;

public class SettingsButton extends JButton {
    public SettingsButton() {
        this.setText("settings");
        this.setPreferredSize(new Dimension(100,20));
        this.setBackground(new Color(0xC40303));
        this.setForeground(new Color(0xFFFFFFF));
        this.setFocusPainted(false);
    }
}