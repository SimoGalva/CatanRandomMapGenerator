package frontEnd.frames;

import engine.pojo.Params;
import frontEnd.buttons.settingsFrameButtons.ConfirmButton;
import frontEnd.inputLines.NumberLine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static utils.ConstantsFrontEnd.BACKGROUND_COLOR;
import static utils.ConstantsFrontEnd.ConstantsTextLines.*;

public class SettingsFrame extends JFrame implements ActionListener {
    private final JPanel paramsPanel;
    private final JPanel buttonPanel;
    private final JButton confirmButton;
    private final NumberLine islandNumberLine;
    private final NumberLine mainIslandNumberLine;
    private final NumberLine mainIslandWeightLine;
    private final NumberLine playerNumberLine;

    private Params startingParams;
    private Params newParams;

    public SettingsFrame(Params params, ActionListener listenerFeRunner) {
        super();
        this.startingParams = params;

        paramsPanel = new JPanel();
        buttonPanel = new JPanel();
        confirmButton = new ConfirmButton(this, listenerFeRunner);
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
                        paramsPanel.add(islandNumberLine, constraints);
                    } else if (j == 1) {
                        paramsPanel.add(mainIslandNumberLine, constraints);
                    } else if (j == 2) {
                        paramsPanel.add(mainIslandWeightLine, constraints);
                    } else {
                        paramsPanel.add(playerNumberLine, constraints);
                    }
                } else {
                    if (j == 0) {
                        paramsPanel.add(new JLabel("Number of Island"), constraints);
                        constraints.gridx += 2;
                        paramsPanel.add(new JLabel(""), constraints);
                        constraints.gridx += 1;
                        paramsPanel.add(new JLabel("(max 5)"), constraints);
                    } else if (j == 1) {
                        paramsPanel.add(new JLabel("Main Island Number"), constraints);
                        constraints.gridx += 2;
                        paramsPanel.add(new JLabel(""), constraints);
                        constraints.gridx += 1;
                        paramsPanel.add(new JLabel("(max Number of Island)"), constraints);
                    } else if (j == 2) {
                        paramsPanel.add(new JLabel("Main Island Weight"), constraints);
                        constraints.gridx += 2;
                        paramsPanel.add(new JLabel(""), constraints);
                        constraints.gridx += 1;
                        paramsPanel.add(new JLabel("(0, 10)"), constraints);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        //todo: gestire il popolamento del newParams
    }

    public Params getNewParams() {
        return newParams;
    }
}
