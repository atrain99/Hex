
package hex.players;

import hex.HexBoard;

public abstract class Player {
    
    protected HexBoard gameBoard;
    
    protected boolean isPlaying;
    
    public Player(HexBoard b){
        gameBoard = b;
    }
    
    public abstract void takeTurn();
    
}
