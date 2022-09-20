package frontEnd;

import engine.MainEngine;
import engine.engineParams.Params;
import frontEnd.frames.GenericErrorFrame;
import frontEnd.frames.LoadSaveFrame;
import frontEnd.frames.MapFrame;
import frontEnd.frames.SettingsFrame;
import globalMap.MapHandler;
import saving.MapSavingHandler;
import utils.Constants;
import utils.exceptions.GenericLoadingException;
import utils.exceptions.NotAPathException;
import utils.exceptions.ParamsValidatorException;
import utils.exceptions.SavingInFileException;
import utils.logging.LoggingClassesEnum;
import utils.logging.SyncedLogger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import static utils.Constants.ConstantsButtons.*;
import static utils.Constants.ConstrainsReloading.FORCE_RELOAD_FROM_SCRATCH;
import static utils.Constants.ConstrainsReloading.NORMAL_RELOADING;

public class FErunner implements Runnable, ActionListener {
    private static final Logger logger = SyncedLogger.getLogger(LoggingClassesEnum.FE_RUNNER);

    private MapFrame frame;
    private MainEngine.MainEngineCaller mainEngineCaller;
    private SettingsFrame settingsFrame;
    private LoadSaveFrame loadSaveFrame;
    private GenericErrorFrame errorFrame;

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
        loadSaveFrame = new LoadSaveFrame(this, isLoad, isBeforeLaunch);

        loadSaveFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        loadSaveFrame.pack();
        loadSaveFrame.setLocationRelativeTo(null);
        loadSaveFrame.setVisible(true);
    }

    private void runGenericErrorFrame(String errorStr) {
        errorFrame = new GenericErrorFrame(this,errorStr);

        errorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        errorFrame.pack();
        errorFrame.setLocationRelativeTo(null);
        errorFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String[] callingClassDecomposed = event.getSource().getClass().getName().split("\\.");
        String callingClass = callingClassDecomposed[callingClassDecomposed.length - 1];

        switch (callingClass) {
            case REFRESH_BUTTON:
                logger.info("FErunner.actionPeformed: refresh button pressed.");
                mainEngineCaller.runRefreshing();
                this.handleRefreshingFrame(NORMAL_RELOADING);
                break;
            case SETTINGS_BUTTON:
                logger.info("FErunner.actionPeformed: settings button pressed.");
                this.runSettingsFrame(mainEngineCaller.getParams(), false);
                break;
            case CONFIRM_BUTTON:
                logger.info("FErunner.actionPeformed: confirm button pressed.");
                switch (event.getActionCommand()) {
                    case CONFIRM_BUTTON_SETTINGS:
                        try {
                            settingsFrame.handleNewParams();
                        } catch (ParamsValidatorException pve) {
                            runGenericErrorFrame(Constants.ConstantsTextLines.ERROR_FRAME_MASSAGE_INVALID_PARAMS);
                        }
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
                                    this.handleRefreshingFrame(NORMAL_RELOADING);
                                } else {
                                    mainEngineCaller.runRefreshing();
                                    this.handleRefreshingFrame(FORCE_RELOAD_FROM_SCRATCH);
                                }
                            } else {
                                mainEngineCaller.run();
                                settingsFrame.dispose();
                            }
                            if(mainEngineCaller.hasBeenLoaded()) {
                                mainEngineCaller.updateHasBeenLoaded();
                            }
                        }
                        if (!settingsFrame.isBeforeRun()) {
                            settingsFrame.dispose();
                        }
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
                        runGenericErrorFrame(Constants.ConstantsTextLines.ERROR_FRAME_MASSAGE_SAVE);
                    } catch (NotAPathException nope) {
                        runGenericErrorFrame(Constants.ConstantsTextLines.ERROR_FRAME_MASSAGE_SAVE);
                    } catch (GenericLoadingException dummy) {/*nothing to do, sono le loading exception non è il caso*/}
                    break;
                case CONFIRM_BUTTON_LOAD:
                    String refreshingConstrain = FORCE_RELOAD_FROM_SCRATCH;
                    try {
                        if (loadSaveFrame.isThereNewContents()) {
                            String path = loadSaveFrame.retrieveContent().get("PATH");
                            String fileName = loadSaveFrame.retrieveContent().get("FILE_NAME");
                            MapSavingHandler.createInstance(path, fileName, Constants.LOAD);
                            if (MapHandler.getGlobalMap().size() == MapSavingHandler.getCurrentMap().size()) {
                                refreshingConstrain = NORMAL_RELOADING;
                            }
                            MapHandler.loadMap(MapSavingHandler.getCurrentMap());
                            mainEngineCaller.forceLoadParams();
                            loadSaveFrame.dispose();
                            this.handleRefreshingFrame(refreshingConstrain);
                        } else {
                            MapSavingHandler.createInstance(Constants.LOAD, null);
                            if (MapHandler.getGlobalMap().size() == MapSavingHandler.getCurrentMap().size()) {
                                refreshingConstrain = NORMAL_RELOADING;
                            }
                            MapHandler.loadMap(MapSavingHandler.getCurrentMap());
                            mainEngineCaller.forceLoadParams();
                            loadSaveFrame.dispose();
                            this.handleRefreshingFrame(refreshingConstrain);
                        }
                    } catch (NotAPathException noap) {
                        runGenericErrorFrame(Constants.ConstantsTextLines.ERROR_FRAME_MASSAGE_LOAD);
                    } catch (GenericLoadingException gle) {
                        runGenericErrorFrame(gle.getMessage());
                        loadSaveFrame.dispose();
                    } catch (SavingInFileException e) {
                        //not interesting
                    }
                    break;
                case CONFIRM_BUTTON_ERROR:
                    errorFrame.dispose();
                    errorFrame = null;
                    break;
                }
                break;
            case SAVE_BUTTON:
                logger.info("FErunner.actionPeformed: settings button pressed.");
                this.runLoadSaveFrame( false, false);
                break;
            case LOAD_BUTTON:
                logger.info("FErunner.actionPeformed: settings button pressed.");
                this.runLoadSaveFrame( false, true);
                break;
            case DEFAULT_SAVE_LOAD_PATH_BUTTON:
                if (Constants.SAVE.equals(event.getActionCommand())) {
                    try {
                        MapSavingHandler.createInstance(Constants.SAVE, MapHandler.getGlobalMap());
                        loadSaveFrame.dispose();
                    } catch (Exception ex) {/*nope, it must work*/}
                } else if (Constants.LOAD.equals(event.getActionCommand())) {
                    String refreshingConstrain = FORCE_RELOAD_FROM_SCRATCH;
                    try {
                        MapSavingHandler.createInstance(Constants.LOAD, null);
                        if (MapHandler.getGlobalMap().size() == MapSavingHandler.getCurrentMap().size()) {
                            refreshingConstrain = NORMAL_RELOADING;
                        }
                        MapHandler.loadMap(MapSavingHandler.getCurrentMap());
                        mainEngineCaller.forceLoadParams();
                        loadSaveFrame.dispose();
                        this.handleRefreshingFrame(refreshingConstrain);
                    } catch (GenericLoadingException gle) {
                        runGenericErrorFrame(gle.getMessage());
                        loadSaveFrame.dispose();
                    } catch (SavingInFileException e) {
                        //noting to do we are loading
                    }
                }
                break;
        }
    }

    private void handleRefreshingFrame(String constrain) {
        switch (constrain) {
            case FORCE_RELOAD_FROM_SCRATCH:
                frame.dispose();
                this.run();
                break;
            case NORMAL_RELOADING:
                frame.refreshMap();
                break;
        }
    }
}
