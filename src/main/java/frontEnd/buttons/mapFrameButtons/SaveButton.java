package frontEnd.buttons.mapFrameButtons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SaveButton extends JButton {
    public SaveButton(ActionListener listenerFeRunner) {
        this.setText("save");
        this.setPreferredSize(new Dimension(100,20));
        this.setBackground(new Color(0xC40303));
        this.setForeground(new Color(0xFFFFFFF));
        this.setFocusPainted(false);
        this.addActionListener(listenerFeRunner);
    }
}
