package frontEnd.buttons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class BrowseButton extends JButton {

    public BrowseButton(JTextField targetField, ActionListener... listener) {
        super("Browse...");
        this.setPreferredSize(new Dimension(100, 20));
        this.setBackground(new Color(0xC40303)); // rosso
        this.setForeground(new Color(0xFFFFFF)); // bianco
        this.setFocusPainted(false);

        this.addActionListener((ActionEvent e) -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Select a folder");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);

            int returnValue = chooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFolder = chooser.getSelectedFile();
                targetField.setText(selectedFolder.getAbsolutePath());
            }
        });
    }
}
