package frontEnd.panels;

import globalMap.GlobalMapHandler;
import hexagon.HexagonFE;
import hexagon.HexagonPoint;
import hexagon.HexagonalBase;
import hexagon.material.Materials;
import utils.logging.LoggingClassesEnum;
import utils.logging.SyncedLogger;
import utils.pojo.DiagSettingsHolder;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Logger;

public class MapPanel extends JPanel {
        private static final Logger logger = SyncedLogger.getLogger(LoggingClassesEnum.MAP_PANEL);
        private static final long serialVersionUID = 1L;

        private int numberOfPlayer;
        private int WIDTH = 1120;
        private int HEIGHT = 860;
        private final int SIZE = 9; // valore iviolabile, definisce il giusto numero di righe e la forma della mappa

        private final static HashMap<String, HexagonalBase> globalMap = GlobalMapHandler.getGlobalMap();

        private Font font = new Font("Arial", Font.BOLD, 18);
        FontMetrics metrics;
        public MapPanel() {
            this.numberOfPlayer = GlobalMapHandler.calculateNumberOfPlayerForFront();
            setPreferredSize(new Dimension(WIDTH, HEIGHT));
        }

        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            Point origin = new Point(WIDTH / 2, HEIGHT / 2);

            g2d.setStroke(new BasicStroke(4.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));
            g2d.setFont(font);
            metrics = g.getFontMetrics();
            int radious = GlobalMapHandler.calculateRadiuos();
            drawCircle(g2d, radious, true, true, 0x4488FF, 0);
            drawHexGridLoop(g2d, origin, 50, 8);
        }

        private void drawHexGridLoop(Graphics g, Point origin, int radius, int padding) {
            double ang30 = Math.toRadians(30);
            double xOff = Math.cos(ang30) * (radius + padding);
            double yOff = Math.sin(ang30) * (radius + padding);
            int half = SIZE / 2;
            DiagSettingsHolder diagSettings = GlobalMapHandler.calculateDiagSettings();

            for (int row = 1; row < SIZE -1; row++) {
                int colsLimit = SIZE - java.lang.Math.abs(row - half);

                for (int col = diagSettings.getDiagStart(); col < colsLimit + diagSettings.getDiagColsAdding(); col++) {
                    int xLbl = row < half ? col - row : col - half;
                    int yLbl = row - half;
                    int x = (int) (origin.x + xOff * (col * 2 + 1 - colsLimit));
                    int y = (int) (origin.y + yOff * (row - half) * 3);

                    drawHex(g, xLbl, yLbl, x, y, radius);
                }
            }
        }

        private void drawHex(Graphics g, int posX, int posY, int x, int y, int r) {
            Graphics2D g2d = (Graphics2D) g;

            HexagonalBase currentHexagon = globalMap.get( new HexagonPoint(posX, posY).toString());
            HexagonFE hex = currentHexagon.defineHexagonFE(x, y, r);

            hex.draw(g2d, x, y, 0, true);
            hex.draw(g2d, x, y, 1, new Color(0xFFFFFF), false);

            if (!Arrays.asList(Materials.DESERT, Materials.WATER).contains(currentHexagon.getMaterial())) {
                g.setColor(new Color(0xFFFFFF));
                g.setFont(new Font("TimesRoman", Font.BOLD, 28));
                int w = metrics.stringWidth(currentHexagon.getNumber().toIntStr());
                int h = metrics.getHeight();
                g.drawString(currentHexagon.getNumber().toIntStr(), x - w / 2, y + h / 2);
            }
        }

        private String coord(int value) {
            return (value > 0 ? "+" : "") + Integer.toString(value);
        }

        public void drawCircle(Graphics2D g, int radius,
                               boolean centered, boolean filled, int colorValue, int lineThickness) {
            // Store before changing.
            Stroke tmpS = g.getStroke();
            Color tmpC = g.getColor();

            Point origin = setCircleOrigin();

            g.setColor(new Color(colorValue));
            g.setStroke(new BasicStroke(lineThickness, BasicStroke.CAP_ROUND,
                    BasicStroke.JOIN_ROUND));

            int diameterX = radius * 2 + getHorizontalExpansion();
            int diameterY = 800 + getVerticalExpansion(); //non ho cercato una forma di calcolo esplicito, l'ho settato in modo che pagasse l'occhio
            int x2 = centered ? origin.x - radius : origin.x;
            int y2 = centered ? origin.y - radius + 50 : origin.y; // anche a traslazione di +50 è per motivi estetici, la deformazione del cerchio crea casini

            if (filled)
                g.fillOval(x2, y2, diameterX, diameterY);
            else
                g.drawOval(x2, y2, diameterX, diameterY);

            // Set values to previous when done.
            g.setColor(tmpC);
            g.setStroke(tmpS);
        }

    private int getHorizontalExpansion() {
        switch (this.numberOfPlayer){
            case 3: //4 Player
                return -103;
            case 4: //4 Player
                return -3;
            case 5: //4 Player
                return 98;
            case 6: //6 Player
                return 1;
            default:
                return 0;
        }
    }

    private int getVerticalExpansion() {
        switch (this.numberOfPlayer){
            case 3: //4 Player
                return -55;
            case 4: //4 Player
                return -23;
            case 5: //4 Player
                return 0;
            case 6: //6 Player
                return 40;
            default:
                return 0;
        }
    }

    private Point setCircleOrigin() {
            switch (this.numberOfPlayer){
                case 3:
                    return new Point((WIDTH + 200) / 2, (HEIGHT + 54) / 2);
                case 4: //4 Player
                    return new Point(WIDTH / 2, (HEIGHT + 21) / 2);
                case 5:
                    return new Point(WIDTH / 2, HEIGHT / 2);
                case 6:
                    return new Point((WIDTH - 4) / 2, (HEIGHT + 158) / 2);
                default:
                    return null;
            }
        }
    }

