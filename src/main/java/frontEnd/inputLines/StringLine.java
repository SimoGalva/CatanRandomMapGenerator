package frontEnd.inputLines;

import utils.logging.LoggingClassesEnum;
import utils.logging.SyncedLogger;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Logger;

public class StringLine extends JTextField {
    private static final Logger logger = SyncedLogger.getLogger(LoggingClassesEnum.NUMBER_LINE);

    public StringLine(String name) {
        this.setPreferredSize(new Dimension(250,20));
        this.setName(name);
    }

    public void setValue(String string) {
        setText(string);
    }

    public String getString() {
        return getText();
    }

}
