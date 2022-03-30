package frontEnd;

import engine.MainEngine;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FErunner implements Runnable, ActionListener {
    private JFrame frame;
    private MainEngine.MainEngineCaller mainEngineCaller;

    public FErunner(MainEngine.MainEngineCaller mainEngineCaller) {
        this.mainEngineCaller = mainEngineCaller;
    }

    @Override
    public void run() {
        frame = new MapFrame(this);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //todo: implementa la lettura del ActionEvent entrante () magari altri tasti faranno riferiemtno a questo ActionListener
        mainEngineCaller.runRefreshing();
        //TODO: implementa un metodo di refersh nella classe MapFrame, può essere dato da:
        // setvisible(false) del solo JPanel map
        // instanziare un nuovo Jpanel map (così si riprinta da solo e quindi settarloa a visible).
        // in teoria dovrebbe funzioanre purchè il nuovo panel venga sostituito come attibuto della classe al posto del pane corrente.
        // non ne sono sicuro, bisogna provare.
    }
}
