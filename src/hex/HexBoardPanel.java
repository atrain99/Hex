package hex;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;

/**
 *
 *
 */
public class HexBoardPanel extends JPanel {

    private Hexagon[][] hexes;
    private Hexagon[] border;

    private boolean blackPlays = false;

    public HexBoardPanel(int size, int graphicsRadius, Point p) {
        hexes = new Hexagon[size][size];
        border = new Hexagon[2 * (size + 2) + 2 * size];
        this.addMouseListener(new HexagonClickListener());
        this.setBackground(Color.DARK_GRAY);
        int apothem = (int) Math.ceil(0.87 * graphicsRadius);
        this.border[0] = new Hexagon(p.x, p.y, graphicsRadius, Color.GRAY);
        for (int i = 1; i < size + 1; i++) {
            this.border[i] = new Hexagon(p.x - ((3 * graphicsRadius / 2) * i), p.y + i * apothem, graphicsRadius, Color.BLACK);
        }
        p.move(this.border[0].getXCenter(), this.border[0].getYCenter() + 2 * apothem);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                hexes[i][j] = new Hexagon(p.x - ((3 * graphicsRadius / 2) * j), p.y + j * apothem, graphicsRadius, null);
            }
            p.translate(3 * graphicsRadius / 2, apothem);
        }
    }

    public HexBoardPanel(int size) {
        this(size, 20, new Point(250, 50));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D canvas = (Graphics2D) g;
        for (Hexagon h : border) {
            if (h != null) {
                canvas.setColor(h.getColor());
                canvas.fill(h);
            }
        }

        for (int i = 0; i < hexes.length; i++) {
            for (Hexagon h : hexes[i]) {
                if (h.hasColor()) {
                    canvas.setColor(h.getColor());
                    canvas.fill(h);
                }
                canvas.setColor(Color.BLACK);
                canvas.draw(h);
            }
        }
    }

    private boolean hasWhiteWon() {
        boolean hasWin = false;

        for (int i = 0; i < this.hexes.length; i++) {
            hasWin = markBlob(Color.WHITE, i, 0, true);
            if (hasWin) {
                break;
            }
        }
        for (int i = 0; i < hexes.length; i++) {
            for (Hexagon h : hexes[i]) {
                h.removeFromBlob();
            }
        }
        return hasWin;
    }

    private boolean hasBlackWon() {
        boolean hasWin = false;
        for (int i = 0; i < this.hexes.length; i++) {
            hasWin = markBlob(Color.BLACK, 0, i, false);
            if (hasWin) {
                break;
            }
        }
        for (int i = 0; i < hexes.length; i++) {
            for (Hexagon h : hexes[i]) {
                h.removeFromBlob();
            }
        }
        return hasWin;
    }

    private boolean isInBounds(int x, int y) {
        return x >= 0 && y >= 0 && x < this.hexes.length && y < this.hexes[0].length;
    }

    private boolean markBlob(Color c, int x, int y, boolean rows) {
        if (isInBounds(x, y)) {
            if (c.equals(hexes[x][y].getColor()) && !hexes[x][y].isPartOfBlob()) {
                hexes[x][y].addToBlob();
                System.out.println("Added hexagon at " + x + ", " + y + " to the blob");
                if (rows) {
                    if (y == this.hexes.length - 1) {
                        return true;
                    }
                } else {
                    if (x == this.hexes.length - 1) {
                        return true;
                    }
                }
                if (markBlob(c, x - 1, y - 1, rows)) {
                    return true;
                }
                if (markBlob(c, x - 1, y, rows)) {
                    return true;
                }
                if (markBlob(c, x, y + 1, rows)) {
                    return true;
                }
                if (markBlob(c, x + 1, y + 1, rows)) {
                    return true;
                }
                if (markBlob(c, x + 1, y, rows)) {
                    return true;
                }
                if (markBlob(c, x, y - 1, rows)) {
                    return true;
                }
            }
        }
        return false;
    }

    private class HexagonClickListener implements MouseListener {

        boolean isListening = true;

        @Override
        public void mouseClicked(MouseEvent me) {
            if (!isListening) {
                return;
            }
            for (int i = 0; i < hexes.length; i++) {
                for (Hexagon h : hexes[i]) {
                    if (h.contains(me.getPoint()) && !h.hasColor()) {
                        if (blackPlays) {
                            h.setColor(Color.BLACK);
                            this.isListening = !hasBlackWon();
                            blackPlays = false;
                        } else {
                            h.setColor(Color.WHITE);
                            this.isListening = !hasWhiteWon();
                            blackPlays = true;
                        }
                        repaint();
                        return;
                    }
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent me) {

        }

        @Override
        public void mouseReleased(MouseEvent me) {

        }

        @Override
        public void mouseEntered(MouseEvent me) {

        }

        @Override
        public void mouseExited(MouseEvent me) {

        }

    }
}
