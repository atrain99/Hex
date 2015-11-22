package hex;

import java.awt.Color;

/**
 *
 *
 */
public class HexBoard {

    private Stone[][] board;
    /**
     * Creates a new, empty (Stones.NONE) game board.
     *
     * @param s The size of the board, both vertically and horizontally
     */
    public HexBoard(int s) {
        this.board = new Stone[s][s];
        for (int i = 0; i < s; i++) {
            for (int j = 0; j < s; j++) {
                this.board[i][j] = Stone.NONE;
            }
        }
    }

    private boolean markBlob(Stone s, int x, int y) {
        if (isInBounds(x, y) && this.board[y][x] == s) {
            mark(x, y);
            if(x == this.board[0].length -1){
                return true;
            }
            if (markBlob(s, x - 1, y)) {
                return true;
            }
            if (markBlob(s, x, y - 1)) {
                return true;
            }
            if (markBlob(s, x + 1, y - 1)) {
                return true;
            }
            if (markBlob(s, x + 1, y)) {
                return true;
            }
            if (markBlob(s, x, y + 1)) {
                return true;
            }
            if (markBlob(s, x - 1, y + 1)) {
                return true;
            }

        }
        return false;
    }
    
    public boolean hasColorWon(Stone s){
        boolean hasWin = false;
        for(int i = 0; i<this.board[0].length;i++){
            if(!hasWin){
                hasWin = markBlob(s, i, 0);
            }
        }
        for (int i = 0; i < this.board[0].length; i++) {
            for (int j = 0; j < this.board.length; j++) {
                unmark(i, j);
            }
        }
        return hasWin;
    }

    /**
     * Checks if the position (x, y) is on the board.
     *
     * @param x
     * @param y
     * @return
     */
    public boolean isInBounds(int x, int y) {
        return x < this.board[0].length && y < this.board.length;
    }

    /**
     * Checks if the stone at (x, y) is black
     *
     * @param x
     * @param y
     * @return
     */
    public boolean isBlack(int x, int y) {
        return this.board[y][x] == Stone.BLACK;
    }

    /**
     * Checks if the stone at (x, y) is white
     *
     * @param x
     * @param y
     * @return
     */
    public boolean isWhite(int x, int y) {
        return this.board[y][x] == Stone.WHITE;
    }

    /**
     * Checks for a stone at (x, y)
     *
     * @param x
     * @param y
     * @return
     */
    public boolean hasStone(int x, int y) {
        return this.board[y][x] != Stone.NONE;
    }

    /**
     * Sets the stone at position (x, y) to black, if (x, y) is on the board.
     *
     * @param x
     * @param y
     */
    public void setBlack(int x, int y) {
        if (isInBounds(x, y)) {
            this.board[y][x] = Stone.BLACK;
        }
    }

    /**
     * Sets the stone at position (x, y) to white, if (x, y) is on the board.
     *
     * @param x
     * @param y
     */
    public void setWhite(int x, int y) {
        if (isInBounds(x, y)) {
            this.board[y][x] = Stone.WHITE;
        }
    }

    public boolean isMarked(int x, int y) {
        return this.board[y][x] == Stone.MARKED_BLACK || this.board[y][x] == Stone.MARKED_WHITE;
    }

    private void mark(int x, int y) {
        switch (this.board[y][x]) {
            case BLACK:
                this.board[y][x] = Stone.MARKED_BLACK;
                break;
            case WHITE:
                this.board[y][x] = Stone.MARKED_WHITE;
                break;
            default:
                break;
        }
    }

    private void unmark(int x, int y) {
        switch (this.board[y][x]) {
            case MARKED_BLACK:
                this.board[y][x] = Stone.BLACK;
                break;
            case MARKED_WHITE:
                this.board[y][x] = Stone.WHITE;
                break;
            default:
                break;
        }
    }

    public static enum Stone {

        NONE(Color.GRAY), BLACK(Color.BLACK), WHITE(Color.WHITE), MARKED_BLACK(Color.BLACK), MARKED_WHITE(Color.WHITE);

        private Color color;

        private Stone(Color c) {
            this.color = c;
        }

        public Color getRenderColor() {
            return this.color;
        }
    }

}
