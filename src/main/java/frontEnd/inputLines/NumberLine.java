package frontEnd.inputLines;

import javax.swing.*;
import java.awt.*;

public class NumberLine extends JTextField {
    public NumberLine(String name) {
        this.setPreferredSize(new Dimension(50,20));
        this.setName(name);
        //todo: riprendi da qui deve lasciare scritto il valore attuale
    }

    public void setIntValue(int x) {
        setText(""+x);
    }

    public int getIntValue() {
        return Integer.parseInt(getText());
    }

}
