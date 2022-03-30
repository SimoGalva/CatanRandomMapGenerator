package frontEnd.buttons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RefreshButton extends JButton implements ActionListener {
    public RefreshButton(ActionListener listenerFeRunner) {
        this.setText("refresh");
        this.setPreferredSize(new Dimension(100,150));
        this.setBackground(new Color(0xC40303));
        this.setForeground(new Color(0xFFFFFFF));
        this.setFocusPainted(false);
        this.addActionListener(this);
        this.addActionListener(listenerFeRunner);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //todo: questo spostalo nell'FErunner qui metti il loge e se serve altro da fare interno, altimenti elimina.
    }
}
