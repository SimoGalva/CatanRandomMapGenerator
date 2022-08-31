package frontEnd;

import engine.MainEngine;
import engine.engineParams.Params;
import frontEnd.frames.LoadSaveFrame;
import frontEnd.frames.MapFrame;
import frontEnd.frames.SettingsFrame;
import globalMap.MapHandler;
import saving.MapSavingHandler;
import utils.Constants;
import utils.exceptions.SavingInFileException;
import utils.logging.LoggingClassesEnum;
import utils.logging.SyncedLogger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import static utils.Constants.ConstantsButtons.*;

public class FErunner implements Runnable, ActionListener {
    private static final Logger logger = SyncedLogger.getLogger(LoggingClassesEnum.FE_RUNNER);

    private MapFrame frame;
    private MainEngine.MainEngineCaller mainEngineCaller;
    private SettingsFrame settingsFrame;
    private LoadSaveFrame loadSaveFrame;

    public FErunner(MainEngine.MainEngineCaller mainEngineCaller) {
        this.mainEngineCaller = mainEngineCaller;
    }

    public void runBeforeLaunch(){
        runSettingsFrame(new Params(0,0,0,0), true);
    }

    @Override
    public void run() {
        frame = new MapFrame(this);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void runSettingsFrame(Params params, boolean isBeforeLaunch) {
        settingsFrame = new SettingsFrame(params, this, isBeforeLaunch);

        settingsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        settingsFrame.pack();
        settingsFrame.setLocationRelativeTo(null);
        settingsFrame.setVisible(true);
    }

    private void runLoadSaveFrame(boolean isBeforeLaunch, boolean isLoad) {
        loadSaveFrame = new LoadSaveFrame(this, isBeforeLaunch, isLoad);

        loadSaveFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        loadSaveFrame.pack();
        loadSaveFrame.setLocationRelativeTo(null);
        loadSaveFrame.setVisible(true);
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
                this.runSettingsFrame(mainEngineCaller.getParams(), false);
                break;
            case CONFIRM_BUTTON:
                logger.info("FErunner.actionPeformed: confirm button pressed.");
                switch (e.getActionCommand()) {
                    case CONFIRM_BUTTON_SETTINGS:
                    settingsFrame.handleNewParams();
                    Params newParams = settingsFrame.getNewParams();
                    if (newParams != null && (mainEngineCaller.getParams() == null || !mainEngineCaller.getParams().equals(newParams))) {
                        boolean isChangedNumberOfPlayer = true;
                        if (mainEngineCaller.getParams() != null) {
                            isChangedNumberOfPlayer = mainEngineCaller.getParams().getNumberOfPlayer() != newParams.getNumberOfPlayer();
                        }
                        mainEngineCaller.setParams(newParams);
                        if (frame != null) {
                            if (!isChangedNumberOfPlayer) {
                                mainEngineCaller.runRefreshing();
                                frame.refreshMap();
                            } else {
                                mainEngineCaller.runRefreshing();
                                frame.dispose();
                                this.run();
                            }
                        } else {
                            mainEngineCaller.run();
                            settingsFrame.dispose();
                        }
                    }
                    if (!settingsFrame.isBeforeRun()) {
                        settingsFrame.dispose();
                    } //todo: implementare una seplice warning frame?
                    break;
                case CONFIRM_BUTTON_SAVE:
                    try {
                        if (loadSaveFrame.isThereNewContents()) {
                            String path = loadSaveFrame.retrieveContent().get("PATH");
                            String fileName = loadSaveFrame.retrieveContent().get("FILE_NAME");
                            MapSavingHandler.createInstance(path, fileName, Constants.SAVE, MapHandler.getGlobalMap());
                            loadSaveFrame.dispose();
                        } else {
                            MapSavingHandler.createInstance(Constants.SAVE, MapHandler.getGlobalMap()); //come se fosse stato cliccato il default
                            loadSaveFrame.dispose();
                        }
                    } catch (SavingInFileException se) {
                        //todo: lancio della frame di errore.
                    } catch (Exception dummy) {
                        //nothing to do, sono le loading exception non è il caso
                    }
                    break;
                }
                break;
            case SAVE_BUTTON:
                logger.info("FErunner.actionPeformed: settings button pressed.");
                this.runLoadSaveFrame( false, false);
                break;
            case DEFAULT_SAVE_LOAD_PATH_BUTTON:
                try {
                    MapSavingHandler.createInstance(Constants.SAVE, MapHandler.getGlobalMap());
                    loadSaveFrame.dispose();
                } catch (Exception ex) {
                    //nope, it must work
                }
                break;
        }
    }


}
