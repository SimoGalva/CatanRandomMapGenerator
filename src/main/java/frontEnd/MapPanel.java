package frontEnd;

import globalMap.GlobalMapHandler;
import hexagon.HexagonFE;
import hexagon.HexagonPoint;
import hexagon.HexagonalBase;
import hexagon.material.Materials;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;

public class MapPanel extends JPanel {
        private static final long serialVersionUID = 1L;
        private final int WIDTH = 920;
        private final int HEIGHT = 820;

        private final static HashMap<String, HexagonalBase> globalMap = GlobalMapHandler.getGlobalMap();

        private Font font = new Font("Arial", Font.BOLD, 18);
        FontMetrics metrics;

        public MapPanel() {
            setPreferredSize(new Dimension(WIDTH, HEIGHT));
        }

        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            Point origin = new Point(WIDTH / 2, HEIGHT / 2);

            g2d.setStroke(new BasicStroke(4.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));
            g2d.setFont(font);
            metrics = g.getFontMetrics();

            drawCircle(g2d, origin, 450, true, true, 0x4488FF, 0);
            drawHexGridLoop(g2d, origin, 9, 50, 8);
        }

        private void drawHexGridLoop(Graphics g, Point origin, int size, int radius, int padding) {
            double ang30 = Math.toRadians(30);
            double xOff = Math.cos(ang30) * (radius + padding);
            double yOff = Math.sin(ang30) * (radius + padding);
            int half = size / 2;

            for (int row = 1; row < size-1; row++) {
                int cols = size - java.lang.Math.abs(row - half);

                for (int col = 0; col < cols; col++) {
                    int xLbl = row < half ? col - row : col - half;
                    int yLbl = row - half;
                    int x = (int) (origin.x + xOff * (col * 2 + 1 - cols));
                    int y = (int) (origin.y + yOff * (row - half) * 3);

                    drawHex(g, xLbl, yLbl, x, y, radius);
                }
            }
        }

        private void drawHex(Graphics g, int posX, int posY, int x, int y, int r) {
            Graphics2D g2d = (Graphics2D) g;

            //TODO: qui non devo fargliene creare uno, devo capire come posso ripescare hexagonalBase e istanziarlo da lì, ci sarà un metodo che crea HEXAGONFE a dovere
            // HexagonFE hex = new HexagonFE(x, y, r);
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

        public void drawCircle(Graphics2D g, Point origin, int radius,
                               boolean centered, boolean filled, int colorValue, int lineThickness) {
            // Store before changing.
            Stroke tmpS = g.getStroke();
            Color tmpC = g.getColor();

            g.setColor(new Color(colorValue));
            g.setStroke(new BasicStroke(lineThickness, BasicStroke.CAP_ROUND,
                    BasicStroke.JOIN_ROUND));

            int diameterX = radius * 2;
            int diameterY = 800; //non ho cercato una forma di calcolo esplicito, l'ho settato in modo che pagasse l'occhio
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
    }

