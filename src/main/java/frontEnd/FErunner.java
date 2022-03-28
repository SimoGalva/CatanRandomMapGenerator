package frontEnd;

import javax.swing.*;

public class FErunner implements Runnable{
    @Override
    public void run() {
        JFrame f = new JFrame();
        MapPanel p = new MapPanel();
        JButton b = new JButton();

        f.setContentPane(p);
        f.add(b);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}
