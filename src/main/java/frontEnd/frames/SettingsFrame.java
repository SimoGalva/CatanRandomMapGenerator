package frontEnd.frames;

import engine.engineParams.Params;
import engine.engineParams.ParamsValidator;
import frontEnd.buttons.commonButtons.ConfirmButton;
import frontEnd.inputLines.NumberLine;
import utils.Constants;
import utils.exceptions.ParamsValidatorException;
import utils.logging.LoggingClassesEnum;
import utils.logging.SyncedLogger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import static utils.Constants.BACKGROUND_COLOR;
import static utils.Constants.ConstantsTextLines.*;

public class SettingsFrame extends JFrame {
    private static final Logger logger = SyncedLogger.getLogger(LoggingClassesEnum.SETTINGS_FRAME);

    private final JPanel paramsPanel;
    private final JPanel buttonPanel;
    private final JButton confirmButton;
    private final NumberLine islandNumberLine;
    private final NumberLine mainIslandNumberLine;
    private final NumberLine mainIslandWeightLine;
    private final NumberLine playerNumberLine;

    private boolean isBeforeRun;

    private Params startingParams;
    private Params newParams;

    public SettingsFrame(Params params, ActionListener listenerFeRunner, boolean isBeforeRun) {
        super();
        this.startingParams = params;
        this.isBeforeRun = isBeforeRun;

        paramsPanel = new JPanel();
        buttonPanel = new JPanel();
        confirmButton = new ConfirmButton(Constants.ConstantsButtons.CONFIRM_BUTTON_SETTINGS, listenerFeRunner);
        islandNumberLine = new NumberLine(ISLAND_NUMBER);
        mainIslandNumberLine = new NumberLine(MAIN_ISLAND_NUMBER);
        mainIslandWeightLine = new NumberLine(MAIN_ISLAND_WEIGHT);
        playerNumberLine = new NumberLine(PLAYER_NUMBER);

        this.paramsPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                constraints.gridx = i;
                constraints.gridy = j;
                if (i == 1) {
                    if (j == 0) {
                        islandNumberLine.setText(String.valueOf(params.getIslandNumber()));
                        paramsPanel.add(islandNumberLine, constraints);
                    } else if (j == 1) {
                        mainIslandNumberLine.setText(String.valueOf(params.getMainIslandNumber()));
                        paramsPanel.add(mainIslandNumberLine, constraints);
                    } else if (j == 2) {
                        mainIslandWeightLine.setText(String.valueOf(params.getMainIslandWeight()));
                        paramsPanel.add(mainIslandWeightLine, constraints);
                    } else {
                        playerNumberLine.setText(String.valueOf(params.getNumberOfPlayer()));
                        paramsPanel.add(playerNumberLine, constraints);
                    }
                } else {
                    if (j == 0) {
                        paramsPanel.add(new JLabel("Number of Island"), constraints);
                        constraints.gridx += 2;
                        paramsPanel.add(new JLabel(""), constraints);
                        constraints.gridx += 1;
                        paramsPanel.add(new JLabel("(max 5 for 3,4 P, max 6 for 5,6 P)"), constraints);
                    } else if (j == 1) {
                        paramsPanel.add(new JLabel("Main Island Number"), constraints);
                        constraints.gridx += 2;
                        paramsPanel.add(new JLabel(""), constraints);
                        constraints.gridx += 1;
                        paramsPanel.add(new JLabel("(1, Number of Island)"), constraints);
                    } else if (j == 2) {
                        paramsPanel.add(new JLabel("Main Island Weight"), constraints);
                        constraints.gridx += 2;
                        paramsPanel.add(new JLabel(""), constraints);
                        constraints.gridx += 1;
                        paramsPanel.add(new JLabel("(1, 10)"), constraints);
                    } else {
                        paramsPanel.add(new JLabel("Number of Players"), constraints);
                        constraints.gridx += 2;
                        paramsPanel.add(new JLabel(""), constraints);
                        constraints.gridx += 1;
                        paramsPanel.add(new JLabel("(3, 4, 5, 6)"), constraints);
                    }
                }
            }
        }

        this.buttonPanel.setLayout(new FlowLayout(FlowLayout.LEADING,10, 0));
        this.buttonPanel.add(confirmButton);

        LayoutManager layout = new BorderLayout(-110,0);
        this.setLayout(layout);

        this.add(paramsPanel, BorderLayout.CENTER);
        this.add(buttonPanel,BorderLayout.SOUTH);

        paramsPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setBackground(BACKGROUND_COLOR);
        this.getContentPane().setBackground(BACKGROUND_COLOR);
        this.setPreferredSize(new Dimension(400,250));
        this.setResizable(false);
        this.setTitle("Settings");
    }

    private boolean isThereNewContents() {
        Integer islandNumberMayBe = 0;
        boolean isThereNewIslandNumber = false;
        Integer mainIslandNumberMayBe = 0;
        boolean isThereNewMainIslandNumber = false;
        Integer mainIslandWeightMayBe = 0;
        boolean isThereNewMainIslandWeight = false;
        Integer playerNumberMayBe = 0;
        boolean isThereNewPlayerNumber = false;

        islandNumberMayBe = this.islandNumberLine.getIntValue();
        if (islandNumberMayBe != null) {
            isThereNewIslandNumber = true;
        }
        mainIslandNumberMayBe = this.mainIslandNumberLine.getIntValue();
        if (mainIslandNumberMayBe != null) {
            isThereNewMainIslandNumber = true;
        }
        mainIslandWeightMayBe = this.mainIslandWeightLine.getIntValue();
        if (mainIslandWeightMayBe != null) {
            isThereNewMainIslandWeight = true;
        }
        playerNumberMayBe = this.playerNumberLine.getIntValue();
        if (playerNumberMayBe != null) {
            isThereNewPlayerNumber = true;
        }
        if ((islandNumberMayBe == 0 && mainIslandNumberMayBe == 0 && mainIslandWeightMayBe == 0 && playerNumberMayBe == 0)
            || startingParams.equals(new Params(islandNumberMayBe, mainIslandNumberMayBe, mainIslandWeightMayBe, playerNumberMayBe))) {
            isThereNewIslandNumber = false;
            isThereNewMainIslandNumber = false;
            isThereNewMainIslandWeight = false;
            isThereNewPlayerNumber = false;
        }
        if (isThereNewIslandNumber || isThereNewMainIslandNumber || isThereNewMainIslandWeight || isThereNewPlayerNumber) {
            this.newParams = new Params(islandNumberMayBe, mainIslandNumberMayBe, mainIslandWeightMayBe, playerNumberMayBe);
        }
        return (isThereNewIslandNumber || isThereNewMainIslandNumber || isThereNewMainIslandWeight || isThereNewPlayerNumber);
    }

    public void handleNewParams() throws ParamsValidatorException {
        logger.info("SettingsFrame.actionPerformed: confirmation button pressed.");
        if (this.isThereNewContents()) {
            ParamsValidator validator = new ParamsValidator();
            if (!validator.validate(this.newParams)) {
                logger.info("SettingsFrame.actionPerformed: validation process returned [false]. Settings new params to null.");
                newParams = null;
                throw new ParamsValidatorException(ParamsValidatorException.MESSAGE);
            } else {
             logger.info("SettingsFrame.actionPerformed: new params setted: " + newParams.toString());
            }
        } else {
            logger.info("SettingsFrame.actionPerformed: there aren't any new params. Nothing to do.");
        }
    }

    public Params getNewParams() {
        return newParams;
    }

    public boolean isBeforeRun() {
        return isBeforeRun;
    }
}
