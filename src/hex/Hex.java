/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hex;

import java.awt.Container;
import java.awt.Point;
import javax.swing.JFrame;

public class Hex {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame gameWindow = new JFrame("Hex");
        gameWindow.setBounds(300, 300, 800, 600);
        gameWindow.setResizable(false);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container contentPane = gameWindow.getContentPane();
        HexGamePanel gamePanel = new HexGamePanel(11, 20, new Point(400, 50));
        
        contentPane.add(gamePanel);
        gameWindow.setVisible(true);
    }

}
