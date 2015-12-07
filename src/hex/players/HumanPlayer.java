package hex.players;

import hex.HexBoard;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * 
 */
public class HumanPlayer extends Player implements MouseListener{
    
    public HumanPlayer(HexBoard b){
        super(b);
    }
    
    @Override
    public void takeTurn(){
        
    }
    
    @Override
    public void mouseClicked(MouseEvent clickEvent) {
        if (isPlaying) {
            for (int i = 0; i < gameBoard.getSize(); i++) {
                for (int j = 0; j < gameBoard.getSize(); j++) {
                    if (gameBoard.getHex(i, j).contains(clickEvent.getPoint()) && !gameBoard.getHex(i, j).hasColor()) {
                        gameBoard.activateHex(i, j);
                        gameBoard.checkForWin();
                        return;
                    }
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}
