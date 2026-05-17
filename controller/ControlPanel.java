package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import java.awt.*;
import model.Game;

/**
 * A ControlPanel osztály a játék vezérlő paneljét reprezentálja.
 * Ez a Controller réteg része, amely tartalmazza a lépés, mozgás és vásárlás gombokat,
 * és ezek eseményeit továbbítja a Modell felé.
 */
public class ControlPanel extends JPanel {
   
    /** Referencia a modellre a parancsok kiadásához. */
    private Game game;
    
    /** Gomb a szimuláció léptetéséhez. */
    private JButton stepButton;
    
    /** Gomb a járművek mozgatásához. */
    private JButton moveButton;
    
    /** Gomb a tárgyak vásárlásához. */
    private JButton buyButton;
    
    /** Gomb a menübe való visszatéréshez. */
    private JButton backtomenuButton;
    
    /** Címke az aktuális állapot kiírásához. */
    private JLabel statusLabel;
    
    /** Szövegmező a bemeneti adatoknak. */
    private JTextField inputField;
    
    /** Szövegmező a kimeneti üzeneteknek. */
    private JTextField outputField;

    /**
     * A ControlPanel konstruktorában inicializáljuk a játékot és a gombokat, 
     * valamint beállítjuk a panel vizuális elrendezését.
     * @param game A szimuláció üzleti logikáját tartalmazó Game objektum.
     */
    public ControlPanel(Game game) {
        this.game = game;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Vertikális elrendezés
        this.setPreferredSize(new Dimension(200, 600)); // Fix szélesség a jobb oldalon
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Margók

        this.statusLabel = new JLabel("Status: Ready");
        this.inputField = new JTextField();
        this.outputField = new JTextField();
        this.outputField.setEditable(false);
        this.backtomenuButton = new JButton("Back to Menu");
        
        initButtons();
        
        //Az elemek hozzáadása a panelhez!
        this.add(statusLabel);
        this.add(Box.createRigidArea(new Dimension(0, 10))); 
        this.add(stepButton);
        this.add(Box.createRigidArea(new Dimension(0, 10)));
        this.add(moveButton);
        this.add(Box.createRigidArea(new Dimension(0, 10)));
        this.add(buyButton);
        this.add(Box.createRigidArea(new Dimension(0, 20)));
        this.add(new JLabel("Input:"));
        this.add(inputField);
        this.add(new JLabel("Output:"));
        this.add(outputField);
        this.add(Box.createVerticalGlue()); // Térkitöltő, hogy a menü gomb alulra kerüljön
        this.add(backtomenuButton);
    }

    /**
     * A gombok inicializálása és eseménykezelőinek (ActionListener) beállítása.
     * Itt kötjük össze a gombnyomásokat a Modell metódusaival.
     */
    public void initButtons() {
        stepButton = new JButton("Step");
        moveButton = new JButton("Move");
        buyButton = new JButton("Buy");

        stepButton.addActionListener(e -> {
            handleStep(); 
            statusLabel.setText("Status: Step taken");
        });

        moveButton.addActionListener(e -> {
            statusLabel.setText("Status: Moving");
        });

        buyButton.addActionListener(e -> {
            statusLabel.setText("Status: Buying");
            String input = JOptionPane.showInputDialog("Enter item to buy:");
            if (input != null && !input.trim().isEmpty()) {
                outputField.setText("Bought: " + input);
            } else {
                outputField.setText("No item entered");
            }
        });
    }
    
    /**
     * A szimuláció egy lépésének kezelése.
     * Meghívja a modell simulateStep metódusát.
     */
    public void handleStep() {
        game.simulateStep();
    }
}