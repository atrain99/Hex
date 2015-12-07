package hex;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 *
 */
public class HexGamePanel extends JPanel {

    private HexBoard gameBoard;

    private Hexagon[] border;


    public HexGamePanel(int size, int graphicsRadius, Point p) {
        gameBoard = new HexBoard(size);
        border = new Hexagon[2 * (size + 1) + 2 * size];
        Timer t = new Timer(100, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                repaint();
            }
        });
        this.setBackground(Color.DARK_GRAY);
        int apothem = (int) Math.ceil(0.87 * graphicsRadius);

        fillBorder(p, graphicsRadius, apothem, size);

        p.move(this.border[0].getXCenter(), this.border[0].getYCenter() + 2 * apothem);

        gameBoard.generateBoard(p, graphicsRadius, apothem);
        t.start();
    }

    public HexGamePanel(int size) {
        this(size, 20, new Point(250, 50));
    }
    
    public HexBoard getGameBoard(){
        return gameBoard;
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
        for (int i = 0; i < gameBoard.getSize(); i++) {
            for (int j = 0; j < gameBoard.getSize(); j++) {
                if (gameBoard.getHex(i, j).hasColor()) {
                    canvas.setColor(gameBoard.getHex(i, j).getColor());
                    canvas.fill(gameBoard.getHex(i, j));
                }
                canvas.setColor(Color.BLACK);
                canvas.draw(gameBoard.getHex(i, j));
            }
        }
    }

}
