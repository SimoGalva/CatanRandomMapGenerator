package frontEnd;

import engine.MainEngine;
import engine.pojo.Params;
import frontEnd.frames.MapFrame;
import frontEnd.frames.SettingsFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import static utils.ConstantsFrontEnd.ConstantsButtons.*;

public class FErunner implements Runnable, ActionListener {
    private final Logger logger = Logger.getLogger(getClass().getName());

    private MapFrame frame;
    private MainEngine.MainEngineCaller mainEngineCaller;
    private SettingsFrame settingsFrame;

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

    private void runSettingsFrame(Params params) {
        settingsFrame = new SettingsFrame(params, this);

        settingsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        settingsFrame.pack();
        settingsFrame.setLocationRelativeTo(null);
        settingsFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String[] callingClassDecomposed = e.getSource().getClass().getName().split("\\.");
        String callingClass = callingClassDecomposed[callingClassDecomposed.length - 1];

        switch (callingClass) {
            case REFRESH_BUTTON:
                logger.info("FErunner.actionPeformed: refresh button pressed.");
                mainEngineCaller.runRefreshing();
                frame.refreshMap();
                break;
            case SETTINGS_BUTTON:
                logger.info("FErunner.actionPeformed: settings button pressed.");
                this.runSettingsFrame(mainEngineCaller.getParams());
                break;
            case CONFIRM_BUTTON:
                logger.info("FErunner.actionPeformed: confirm button pressed.");
                Params newParams = settingsFrame.getNewParams();
                if (newParams != null && !mainEngineCaller.getParams().equals(newParams)) {
                    mainEngineCaller.setParams(newParams);
                    mainEngineCaller.runRefreshing();
                    frame.refreshMap();
                }
                settingsFrame.dispose();
                break;
        }
    }


}
