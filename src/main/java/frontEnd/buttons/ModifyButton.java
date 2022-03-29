package frontEnd.buttons;

import javax.swing.*;
import java.awt.*;

public class ModifyButton extends JButton {
    public ModifyButton() {
        this.setText("modify");
        this.setPreferredSize(new Dimension(100,20));
        this.setBackground(new Color(0xC40303));
        this.setForeground(new Color(0xFFFFFFF));
        this.setFocusPainted(false);
    }
}
