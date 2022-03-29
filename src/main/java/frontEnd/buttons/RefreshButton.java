package frontEnd.buttons;

import javax.swing.*;
import java.awt.*;

public class RefreshButton extends JButton {
    public RefreshButton() {
        this.setText("refresh");
        this.setPreferredSize(new Dimension(100,150));
        this.setBackground(new Color(0xC40303));
        this.setForeground(new Color(0xFFFFFFF));
        this.setFocusPainted(false);
    }
}
