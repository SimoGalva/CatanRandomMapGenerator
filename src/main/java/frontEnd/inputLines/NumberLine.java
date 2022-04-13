package frontEnd.inputLines;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Logger;

public class NumberLine extends JTextField {
    private final Logger logger = Logger.getLogger(getClass().getName());

    public NumberLine(String name) {
        this.setPreferredSize(new Dimension(50,20));
        this.setName(name);
        //todo: riprendi da qui deve lasciare scritto il valore attuale
    }

    public void setIntValue(int x) {
        setText(""+x);
    }

    public Integer getIntValue() {
        Integer value = null;
        try {
            value = Integer.parseInt(getText());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return value;
    }

}
