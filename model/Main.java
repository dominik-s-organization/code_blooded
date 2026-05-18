package model;
import javax.swing.Timer;

import javax.swing.SwingUtilities;

import view.MainFrame;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        game.initTestMap();
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame(game);
            frame.setVisible(true);
        });
        
        // Timer létrehozása, amely 1 másodpercenként meghívja a simulateStep() metódust
        Timer timer = new Timer(1000, e -> {
            game.simulateStep();
        });
        timer.start();
    }
}