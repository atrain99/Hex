/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hex;

import java.awt.Container;
import javax.swing.JFrame;

/**
 *
 * @author atrain99
 */
public class Hex {

    private HexBoard gameBoard;

    private JFrame gameWindow;
    
    private int gameSize;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Hex(5).run();
    }
    
    public Hex(int size){
        this.gameSize = size;
    }

    private void run() {
        gameBoard = new HexBoard(this.gameSize);
        
        gameWindow = new JFrame("Hex");
        gameWindow.setBounds(300, 300, 300, 200);
        gameWindow.setResizable(false);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = gameWindow.getContentPane();
        c.add(new HexBoardPanel(gameBoard));
        gameWindow.setVisible(true);
    }

}
