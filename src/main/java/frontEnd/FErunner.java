package frontEnd;

import engine.MainEngine;
import utils.Constants;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

public class FErunner implements Runnable, ActionListener {
    private final Logger logger = Logger.getLogger(getClass().getName());

    private MapFrame frame;
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
        String[] callingClassDecomposed = e.getSource().getClass().getName().split("\\.");
        String callingClass = callingClassDecomposed[callingClassDecomposed.length - 1];

        switch (callingClass) {
            case Constants.REFRESH_BUTTON_CLASS:
                logger.info("FErunner.actionPeformed: refresh button pressed.");
                mainEngineCaller.runRefreshing();
                //todo: riprendi implementazione da qui, bisogna refreshare la MapFrame.
                //MapFrame.refreshMap();
                break;
        }
        //TODO: implementa un metodo di refersh nella classe MapFrame, può essere dato da:
        // setvisible(false) del solo JPanel map
        // instanziare un nuovo Jpanel map (così si riprinta da solo e quindi settarloa a visible).
        // in teoria dovrebbe funzioanre purchè il nuovo panel venga sostituito come attibuto della classe al posto del pane corrente.
        // non ne sono sicuro, bisogna provare.
    }
}
