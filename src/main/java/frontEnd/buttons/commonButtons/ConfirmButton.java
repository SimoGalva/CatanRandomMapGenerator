package frontEnd.buttons.commonButtons;

import utils.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ConfirmButton extends JButton {
    public ConfirmButton(String name, ActionListener... listener) {
        this.setText("confirm");
        if(Constants.ConstantsButtons.CONFIRM_BUTTON_SETTINGS.equals(name)) {
            this.setPreferredSize(new Dimension(365, 20));
        } else if (Constants.ConstantsButtons.CONFIRM_BUTTON_SAVE.equals(name)) {
            this.setPreferredSize(new Dimension(180, 20));
        } else if (Constants.ConstantsButtons.CONFIRM_BUTTON_LOAD.equals(name)) {
            this.setPreferredSize(new Dimension(180, 20));
        } else if (Constants.ConstantsButtons.CONFIRM_BUTTON_ERROR.equals(name)) {
            //its adaptive and is calculated depending on error frame dimension
            this.setPreferredSize(new Dimension(100, 20));
        }
        this.setBackground(new Color(0xC40303));
        this.setForeground(new Color(0xFFFFFFF));
        this.setFocusPainted(false);
        for (int i = 0; i < listener.length; i++) {
            this.addActionListener(listener[i]);
        }
        this.setActionCommand(name);
    }
}