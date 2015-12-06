

package hex;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;


public class HexBoard {
    
    private Hexagon[][] gameGrid;
    
    private List<Hexagon> hexList;
    
    private boolean whitePlays;
    
    /**
     * Constructs a new game board.
     * @param size Number of hexes to a side -- the grid is size*size
     * @param whiteFirst Does white play first?
     */
    public HexBoard(int size, boolean whiteFirst){
        whitePlays = whiteFirst;
        gameGrid = new Hexagon[size][size];
        hexList = new ArrayList<>(size*size);
    }
    
    /**
     * Constructs a game board, size*size. White plays first.
     * @param size 
     */
    public HexBoard(int size){
        this(size, true);
    }
    
    /**
     * Returns the row array at index x. Rows go from upper left to lower right.
     * @param x
     * @return 
     */
    public Hexagon[] getRow(int x){
        return gameGrid[x];
    }
    
    public List<Hexagon> getAllHexes(){
        return hexList;
    }
    
    /**
     * Returns the column at index y. Columns go from upper right to lower left.
     * @param y
     * @return 
     */
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
    
    public int getSize(){
        return gameGrid.length;
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
    
    /**
     * "marks" an area of hexagons as part of a chain connected to the given hexagon. Recurses until a win is detected or it runs out of neighbors to check.
     * Check the validity of x and y internally, to prevent IndexOutOfBoundsExcpetions
     * @param markBlack True if marking black hexagons, false if marking white hexagons.
     * @param x x index to check
     * @param y y index to check
     * @param traverseX True if checking for a win by crossing from the top right to lower left edge, false if crossing from upper left to lower right.
     * @return True when it reaches the edge of the board specified by traverseY.
     */
    private boolean markBlob(boolean markBlack, int x, int y, boolean traverseX) {
        if (isInBounds(x, y)) {
            if (((gameGrid[x][y].isBlack() && markBlack) || (gameGrid[x][y].isWhite() && !markBlack)) && !gameGrid[x][y].isPartOfBlob()) {
                gameGrid[x][y].addToBlob();
                if (traverseX) {
                    if (y == this.gameGrid.length - 1) {
                        return true;
                    }
                } else {
                    if (x == this.gameGrid.length - 1) {
                        return true;
                    }
                }
                if (markBlob(markBlack, x - 1, y - 1, traverseX)) {
                    return true;
                }
                if (markBlob(markBlack, x - 1, y, traverseX)) {
                    return true;
                }
                if (markBlob(markBlack, x, y + 1, traverseX)) {
                    return true;
                }
                if (markBlob(markBlack, x + 1, y + 1, traverseX)) {
                    return true;
                }
                if (markBlob(markBlack, x + 1, y, traverseX)) {
                    return true;
                }
                if (markBlob(markBlack, x, y - 1, traverseX)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean hasWhiteWon() {
        boolean hasWin = false;

        for (int i = 0; i < this.gameGrid.length; i++) {
            hasWin = markBlob(false, i, 0, true);
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
            hasWin = markBlob(true, 0, i, false);
            if (hasWin) {
                break;
            }
        }
        for(Hexagon h : hexList){
            h.removeFromBlob();
        }
        return hasWin;
    }

    /**
     * Changes the color at (x, y), based on whose turn it is.
     * Checks x and y for validity, does not check if the hexagon has a color.
     * @param x
     * @param y 
     */
    public void activateHex(int x, int y) {
        if (isInBounds(x, y)) {
            if (whitePlays) {
                gameGrid[x][y].setWhite();
            } else {
                gameGrid[x][y].setBlack();
            }

            whitePlays = !whitePlays;
        }
    }

    public void checkForWin() {
        boolean hasWin = false;
        for (int i = 0; i < this.gameGrid.length; i++) {
            hasWin = markBlob(true, 0, i, false);
            if (hasWin) {
                System.out.println("Black has won!");
                for (Hexagon h : hexList) {
                    h.setBlack();
                }
                break;
            }
        }
        if (!hasWin) {
            for (Hexagon h : hexList) {
                h.removeFromBlob();
            }
            for (int i = 0; i < this.gameGrid.length; i++) {
                hasWin = markBlob(false, i, 0, true);
                if (hasWin) {
                    System.out.println("White has won!");
                    for (Hexagon h : hexList) {
                        h.setWhite();
                    }
                    break;
                }
            }
        }
        for(Hexagon h : hexList){
            h.removeFromBlob();
        }
        
    }
}
