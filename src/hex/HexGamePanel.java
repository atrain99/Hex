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
public class HexGamePanel extends JPanel {

    private HexBoard gameBoard;

    private Hexagon[] border;

    private boolean blackPlays = false;

    public HexGamePanel(int size, int graphicsRadius, Point p) {
        gameBoard = new HexBoard(size);
        border = new Hexagon[2 * (size + 1) + 2 * size];
        this.addMouseListener(new HexagonClickListener());
        this.setBackground(Color.DARK_GRAY);
        int apothem = (int) Math.ceil(0.87 * graphicsRadius);
        System.out.println("Filling Border");

        fillBorder(p, graphicsRadius, apothem, size);

        p.move(this.border[0].getXCenter(), this.border[0].getYCenter() + 2 * apothem);

        gameBoard.generateBoard(p, graphicsRadius, apothem);
    }

    public HexGamePanel(int size) {
        this(size, 20, new Point(250, 50));
    }

    private void fillBorder(Point p, int graphicsRadius, int apothem, int size) {
        this.border[0] = new Hexagon(p.x, p.y, graphicsRadius, Color.GRAY);
        for (int i = 1; i < size + 1; i++) {
            p.translate(-(3 * graphicsRadius / 2), apothem);
            this.border[i] = new Hexagon(p.x, p.y, graphicsRadius, Color.BLACK);
        }
        p.translate(0, 2 * apothem);
        for (int i = size + 1; i < 2 * size + 1; i++) {
            this.border[i] = new Hexagon(p.x, p.y, graphicsRadius, Color.WHITE);
            p.translate((3 * graphicsRadius / 2), apothem);
        }
        this.border[2 * size + 1] = new Hexagon(p.x, p.y, graphicsRadius, Color.GRAY);
        for (int i = 2 * size + 2; i < 3 * size + 2; i++) {
            p.translate((3 * graphicsRadius / 2), -apothem);
            this.border[i] = new Hexagon(p.x, p.y, graphicsRadius, Color.BLACK);
        }

        p.translate(0, -2 * apothem);

        for (int i = 3 * size + 2; i < 4 * size + 2; i++) {
            this.border[i] = new Hexagon(p.x, p.y, graphicsRadius, Color.WHITE);
            p.translate(-(3 * graphicsRadius / 2), -apothem);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D canvas = (Graphics2D) g;
        for (Hexagon h : border) {
            canvas.setColor(h.getColor());
            canvas.fill(h);

        }
        for (Hexagon h : gameBoard.getAllHexes()) {
            if (h.hasColor()) {
                canvas.setColor(h.getColor());
                canvas.fill(h);
            }
            canvas.setColor(Color.BLACK);
            canvas.draw(h);
        }

    }

    private class HexagonClickListener implements MouseListener {

        boolean isListening = true;

        @Override
        public void mouseClicked(MouseEvent clickEvent) {
            if (!isListening) {
                return;
            }
            for (Hexagon h : gameBoard.getAllHexes()) {
                if (h.contains(clickEvent.getPoint())) {
                    if (blackPlays) {
                        h.setColor(Color.BLACK);
                        if (gameBoard.hasBlackWon()) {
                            this.isListening = false;
                            System.out.println("Black has won the game!");
                        }
                        blackPlays = false;
                    } else {
                        h.setColor(Color.WHITE);
                        if (gameBoard.hasWhiteWon()) {
                            System.out.println("White has won the game!");
                        }
                        blackPlays = true;
                    }
                    repaint();
                    return;
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
