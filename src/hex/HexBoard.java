package hex;

/**
 *
 *
 */
public class HexBoard {

    private Stone[][] board;
    private int size;

    /**
     * Creates a new, empty (Stones.NONE) game board.
     *
     * @param s The size of the board, both vertically and horizontally
     */
    public HexBoard(int s) {
        this.size = s;
        this.board = new Stone[s][s];
        for (int i = 0; i < s; i++) {
            for (int j = 0; j < s; j++) {
                this.board[i][j] = Stone.NONE;
            }
        }
    }

    private boolean markBlob(Stone s, int x, int y) {
        if (isInBounds(x, y) && this.board[x][y] == s) {
            mark(x, y);
            if (x == this.board.length - 1) {
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

    public boolean hasColorWon(Stone s) {
        boolean hasWin = false;
        for (int i = 0; i < this.board.length; i++) {
            if (!hasWin) {
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

    public boolean isInBounds(int x, int y) {
        return y < this.board[0].length && x < this.board.length && x < 0 && y < 0;
    }

    public boolean isBlack(int x, int y) {
        return this.board[x][y] == Stone.BLACK;
    }

    public int getSize() {
        return this.size;
    }

    public int getNumHexes() {
        return size * size;
    }

    public boolean isWhite(int x, int y) {
        return this.board[x][y] == Stone.WHITE;
    }

    public boolean hasStone(int x, int y) {
        return this.board[x][y] != Stone.NONE;
    }

    public void setBlack(int x, int y) {
        if (isInBounds(x, y)) {
            System.out.println("Setting stone color at:" + x + ", " + y);
            board[x][y] = Stone.BLACK;
        }
    }

    public void setWhite(int x, int y) {
        
        if (isInBounds(x, y)) {
            System.out.println("Setting stone color at:" + x + ", " + y);
            board[x][y] = Stone.WHITE;
        }
    }

    public boolean isMarked(int x, int y) {
        return board[x][y] == Stone.MARKED_BLACK || this.board[x][y] == Stone.MARKED_WHITE;
    }

    private void mark(int x, int y) {
        System.out.println("Marking " + x + "," + y);
        switch (this.board[x][y]) {
            case BLACK:
                this.board[x][y] = Stone.MARKED_BLACK;
                break;
            case WHITE:
                this.board[x][y] = Stone.MARKED_WHITE;
                break;
            default:
                break;
        }
    }

    private void unmark(int x, int y) {
        switch (this.board[x][y]) {
            case MARKED_BLACK:
                this.board[x][y] = Stone.BLACK;
                break;
            case MARKED_WHITE:
                this.board[x][y] = Stone.WHITE;
                break;
            default:
                break;
        }
    }
    

    public static enum Stone {

        NONE, BLACK, WHITE, MARKED_BLACK, MARKED_WHITE;

        
    }

}
