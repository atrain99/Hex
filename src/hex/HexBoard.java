

package hex;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;


public class HexBoard {
    private Hexagon[][] gameGrid;
    private List<Hexagon> hexList;
    
    public HexBoard(int size){
        gameGrid = new Hexagon[size][size];
        hexList = new ArrayList<>();
    }
    
    public Hexagon[] getRow(int x){
        return gameGrid[x];
    }
    
    public List<Hexagon> getAllHexes(){
        return hexList;
    }
    public Hexagon[] getColumn(int y){
        Hexagon[] col = new Hexagon[this.gameGrid.length];
        for(int i = 0; i<col.length; i++){
            col[i] = gameGrid[i][y];
        }
        return col;
    }
    
    public Hexagon getHex(int x, int y){
        return gameGrid[x][y];
    }
    
    /**
     * Fills the gameGrid and hexArray with a fresh game instance.
     * @param head The point of the hex at [0][0]
     * @param renderRadius Side length (and circumradius) of each hexagon
     * @param apothem vertical space between each hexagon -- 0.87 * side length for best results 
     */
    public void generateBoard(Point head, int renderRadius, int apothem){
        for (int i = 0; i < gameGrid.length; i++) {
            for (int j = 0; j < gameGrid.length; j++) {
                gameGrid[i][j] = new Hexagon(head.x - ((3 * renderRadius / 2) * j), head.y + j * apothem, renderRadius, null);
                hexList.add(gameGrid[i][j]);
            }
            head.translate(3 * renderRadius / 2, apothem);
        }
        
    }
    
    public boolean isInBounds(int x, int y) {
        return x >= 0 && y >= 0 && x < this.gameGrid.length && y < this.gameGrid[0].length;
    }
    
    private boolean markBlob(Color c, int x, int y, boolean rows) {
        if (isInBounds(x, y)) {
            if (c.equals(gameGrid[x][y].getColor()) && !gameGrid[x][y].isPartOfBlob()) {
                gameGrid[x][y].addToBlob();
                //System.out.println("Added hexagon at " + x + ", " + y + " to the blob");
                if (rows) {
                    if (y == this.gameGrid.length - 1) {
                        return true;
                    }
                } else {
                    if (x == this.gameGrid.length - 1) {
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
    
    public boolean hasWhiteWon() {
        boolean hasWin = false;

        for (int i = 0; i < this.gameGrid.length; i++) {
            hasWin = markBlob(Color.WHITE, i, 0, true);
            if (hasWin) {
                break;
            }
        }
        for(Hexagon h : hexList){
            h.removeFromBlob();
        }
        return hasWin;
    }

    public boolean hasBlackWon() {
        boolean hasWin = false;
        for (int i = 0; i < this.gameGrid.length; i++) {
            hasWin = markBlob(Color.BLACK, 0, i, false);
            if (hasWin) {
                break;
            }
        }
        for(Hexagon h : hexList){
            h.removeFromBlob();
        }
        return hasWin;
    }
    
}
