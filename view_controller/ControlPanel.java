package view_controller;

import javax.swing.JButton;
import javax.swing.JTextField;

import model.Game;

// A ControlPanel osztály a játék vezérlő paneljét reprezentálja, amely tartalmazza a lépés, mozgás és vásárlás gombokat.
public class ControlPanel {
    private Game game;
    private JButton stepButton;
    private JButton moveButton;
    private JButton buyButton;
    private JTextField inputField;
    private JTextField outputField;

    // A ControlPanel konstruktorában inicializáljuk a játékot és a gombokat, valamint beállítjuk a gombok eseménykezelőit.
    public ControlPanel() {
        game = new Game();
        initialize();
    }

    // A gombok inicializálása és eseménykezelőinek beállítása.
    public void initialize() {
        stepButton = new JButton("Step");
        moveButton = new JButton("Move");
        buyButton = new JButton("Buy");

        stepButton.addActionListener(e -> {
            game.simulateStep();
            // Update the game panel after stepping
        });

        moveButton.addActionListener(e -> {
            // Handle move action
        });

        buyButton.addActionListener(e -> {
            // Handle buy action
        });
    } 
    
    public void handleStep() {
        game.simulateStep();
        // Update the game panel after stepping
    }
}
