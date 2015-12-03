

package hex;

import java.awt.Color;
import java.awt.Point;


public class HexBoard {
    private Hexagon[][] hexes;
    private Hexagon[] hexArray;
    
    public HexBoard(int size){
        hexes = new Hexagon[size][size];
        hexArray = new Hexagon[size*size];
    }
    
    public Hexagon[] getRow(int x){
        return hexes[x];
    }
    
    public Hexagon[] getAllHexes(){
        return hexArray;
    }
    public Hexagon[] getColumn(int y){
        Hexagon[] col = new Hexagon[this.hexes.length];
        for(int i = 0; i<col.length; i++){
            col[i] = hexes[i][y];
        }
        return col;
    }
    
    public Hexagon getHex(int x, int y){
        return hexes[x][y];
    }
    
    public void generateBoard(Point columnHead, int graphicsRadius, int apothem){
        int hexIndex = 0;
        for (int i = 0; i < hexes.length; i++) {
            for (int j = 0; j < hexes.length; j++) {
                hexes[i][j] = new Hexagon(columnHead.x - ((3 * graphicsRadius / 2) * j), columnHead.y + j * apothem, graphicsRadius, null);
                hexArray[hexIndex] = hexes[i][j];
                hexIndex++;
            }
            columnHead.translate(3 * graphicsRadius / 2, apothem);
        }
        
    }
    
    public boolean isInBounds(int x, int y) {
        return x >= 0 && y >= 0 && x < this.hexes.length && y < this.hexes[0].length;
    }
    
    private boolean markBlob(Color c, int x, int y, boolean rows) {
        if (isInBounds(x, y)) {
            if (c.equals(hexes[x][y].getColor()) && !hexes[x][y].isPartOfBlob()) {
                hexes[x][y].addToBlob();
                //System.out.println("Added hexagon at " + x + ", " + y + " to the blob");
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
    
    public boolean hasWhiteWon() {
        boolean hasWin = false;

        for (int i = 0; i < this.hexes.length; i++) {
            hasWin = markBlob(Color.WHITE, i, 0, true);
            if (hasWin) {
                break;
            }
        }
        for(Hexagon h : hexArray){
            h.removeFromBlob();
        }
        return hasWin;
    }

    public boolean hasBlackWon() {
        boolean hasWin = false;
        for (int i = 0; i < this.hexes.length; i++) {
            hasWin = markBlob(Color.BLACK, 0, i, false);
            if (hasWin) {
                break;
            }
        }
        for(Hexagon h : hexArray){
            h.removeFromBlob();
        }
        return hasWin;
    }
    
}
