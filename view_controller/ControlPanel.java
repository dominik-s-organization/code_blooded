package view_controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import model.Game;
import model.Player;

// A ControlPanel osztály a játék vezérlő paneljét reprezentálja, amely tartalmazza a lépés, mozgás és vásárlás gombokat.
public class ControlPanel extends MouseAdapter {
    private Game game;
    private JButton stepButton;
    private JButton moveButton;
    private JButton buyButton;
    private JLabel statusLabel;
    private JTextField inputField;
    private JTextField outputField;

    // A ControlPanel konstruktorában inicializáljuk a játékot és a gombokat, valamint beállítjuk a gombok eseménykezelőit.
    public ControlPanel(Game game, JTextField inputField, JTextField outputField) {
        this.game = game;
        this.inputField = inputField;
        this.outputField = outputField;
        this.statusLabel = new JLabel("Status: Ready");
        initialize();
    }

    // A gombok inicializálása és eseménykezelőinek beállítása.
    public void initialize() {
        stepButton = new JButton("Step");
        moveButton = new JButton("Move");
        buyButton = new JButton("Buy");

        stepButton.addActionListener(e -> {
            game.simulateStep(); // lefuttatja a játék egy lépését
            statusLabel.setText("Status: Step taken");
        });

        moveButton.addActionListener(e -> {
            statusLabel.setText("Status: Moving");
        });

        buyButton.addActionListener(e -> {
            statusLabel.setText("Status: Buying");
            String input = JOptionPane.showInputDialog("Enter item to buy:"); // megkérdezi a felhasználót, hogy mit szeretne vásárolni
            if (input != null && !input.trim().isEmpty()) {
                // Itt lehetne hozzáadni a vásárlási logikát, például ellenőrizni a bolt kínálatát és a játékos pénzét.
                outputField.setText("Bought: " + input); // kiírja, hogy mit vásárolt a játékos
            } else {
                outputField.setText("No item entered"); // ha nem adott meg semmit, kiírja, hogy nem adott meg semmit
            }
        });
    } 
    
    public void handleStep() {
        game.simulateStep();
    }
}
