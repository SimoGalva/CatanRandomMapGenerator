package frontEnd.buttons.mapFrameButtons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class RefreshButton extends JButton {
    public RefreshButton(ActionListener listenerFeRunner) {
        this.setText("refresh");
        this.setPreferredSize(new Dimension(100,150));
        this.setBackground(new Color(0xC40303));
        this.setForeground(new Color(0xFFFFFFF));
        this.setFocusPainted(false);
        this.addActionListener(listenerFeRunner);
    }
}
