import game.Game;

import javax.swing.*;

public class Main {
    public static void main(String[] args){
    Game game = new Game();
    JFrame frame = new JFrame("luci");
    frame.add(game);
    frame.pack();
    frame.setResizable(false);
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JInternalFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
    game.start();
    }
}
