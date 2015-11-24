package hex;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JPanel;

/**
 *
 * 
 */
public class HexBoardPanel extends JPanel{
    
    private List<Hexagon> hexes;
    
    private HexBoard board;
    
    private boolean p1Plays = true;
    
    public HexBoardPanel(HexBoard hb){
        this.board = hb;
        this.addMouseListener(new HexagonClickListener());
        this.hexes = new ArrayList<>();
        hexes.add(new Hexagon(100, 20, 20, null));
        hexes.add(new Hexagon(70, 38, 20, null));
        hexes.add(new Hexagon(130, 38, 20, null));
        this.setBackground(Color.BLUE);
        
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
                            p1Plays = false;
                        }else{
                            p1Plays = true;
                            h.setColor(Color.WHITE);
                        }
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
