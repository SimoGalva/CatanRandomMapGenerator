package frontEnd.frames;

import frontEnd.buttons.commonButtons.ConfirmButton;
import utils.Constants;
import utils.logging.LoggingClassesEnum;
import utils.logging.SyncedLogger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import static utils.Constants.BACKGROUND_COLOR;

public class GenericErrorFrame extends JFrame {
    private static final Logger logger = SyncedLogger.getLogger(LoggingClassesEnum.SETTINGS_FRAME);

    private final JPanel textPanel;
    private final JPanel buttonPanel;
    private final JButton confirmButton;

    public GenericErrorFrame(ActionListener listenerFeRunner,String errorStr) {
        super();
        textPanel = new JPanel();
        textPanel.add(new Label(errorStr));
        buttonPanel = new JPanel();
        confirmButton = new ConfirmButton(Constants.ConstantsButtons.CONFIRM_BUTTON_ERROR, listenerFeRunner);


        this.setLayout(new BorderLayout());
        this.buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER,10, 0));
        this.buttonPanel.add(confirmButton);

        LayoutManager layout = new BorderLayout(-110,0);
        this.setLayout(layout);

        this.add(textPanel, BorderLayout.CENTER);
        this.add(buttonPanel,BorderLayout.SOUTH);

        textPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setBackground(BACKGROUND_COLOR);
        this.getContentPane().setBackground(BACKGROUND_COLOR);
        //this.setPreferredSize(new Dimension(200,100));
        this.setResizable(false);
        this.setTitle("Error");
    }
}
