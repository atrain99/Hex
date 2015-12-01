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
public class HexBoardPanel extends JPanel{
    
    private Hexagon[] hexes;
    
    private HexBoard board;
    
    private boolean p1Plays = true;
    
    public HexBoardPanel(HexBoard hb){
        board = hb;
        hexes = new Hexagon[hb.getNumHexes()];
        this.addMouseListener(new HexagonClickListener());
        int apothem = (int) Math.ceil(0.87 * 20);
        int radius = 20;
        
        Point p = new Point(250, 50);
        int hexNumber = 0;
        for(int i = 0; i<board.getSize(); i++){
            for (int j = 0; j<board.getSize(); j++){
                hexes[hexNumber] = new Hexagon(p.x - ((3 * radius /2)*j), p.y + j*apothem, radius, null);
                hexes[hexNumber].setBoardPosition(i, j);
                hexNumber++;
            }
            p.translate(3 * radius / 2, apothem);
        }
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D canvas = (Graphics2D) g;
        for(Hexagon h : hexes){
            if(h.hasColor()){
                canvas.setColor(h.getColor());
                canvas.fill(h);
            }else{
                canvas.setColor(Color.BLACK);
                canvas.draw(h);
            }
        }    
    }
    
    private class HexagonClickListener implements MouseListener{
        
        @Override
        public void mouseClicked(MouseEvent me) {
            for(Hexagon h : hexes){
                if(h.contains(me.getPoint())){
                    if(!h.hasColor()){
                        if(p1Plays){
                            h.setColor(Color.BLACK);
                            board.setBlack(h.getBoardX(), h.getBoardY());
                            p1Plays = false;
                        }else{
                            p1Plays = true;
                            board.setWhite(h.getBoardX(), h.getBoardY());
                            h.setColor(Color.WHITE);
                        }
                        if(board.hasColorWon(HexBoard.Stone.BLACK)){
                            System.out.println("Black won");
                        }
                        break;
                    }
                }
            }
            repaint();
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
