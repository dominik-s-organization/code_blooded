package model;

import view.MainFrame;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        game.initTestMap(); // Minden adat (J1-J5, utak, hókotró) itt töltődik be a háttérben!
        
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame(game);
            frame.setVisible(true);
        });
    }
}