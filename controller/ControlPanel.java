package controller;

import model.Game;
import view.StatusPanel;
import javax.swing.*;
import java.awt.*;

/**
 * A ControlPanel a tiszta CONTROLLER réteg.
 * Feladata a felhasználói interakciók (gombnyomások) továbbítása a Modell felé.
 */
public class ControlPanel extends JPanel {
    private Game game;
    private StatusPanel statusPanel; // Referencia a view-ra, hogy a státuszt átírhassa

    // A gombok a játék különböző parancsait reprezentálják, amelyek a játékos által kiválaszthatók.
    private JButton stepButton, moveButton, buyButton, equipButton, backtomenuButton;

    // Az input és output mezők a vezérlőpanel alján helyezkednek el, és a játékos által bevitt parancsokat, valamint a játék válaszait jelenítik meg.
    private JTextField inputField, outputField;

    // Konstruktor, amely inicializálja a vezérlőpanelt, beállítja a gombokat és a mezőket, valamint feliratkozik a modellre.
    public ControlPanel(Game game, StatusPanel statusPanel) {
        this.game = game;
        this.statusPanel = statusPanel;

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Color.WHITE);
        this.setBorder(BorderFactory.createEmptyBorder(5, 15, 15, 15));

        this.inputField = new JTextField();
        this.inputField.setMaximumSize(new Dimension(250, 30));
        this.outputField = new JTextField();
        this.outputField.setMaximumSize(new Dimension(250, 30));
        this.outputField.setEditable(false);
        
        this.backtomenuButton = new JButton("Back to Menu");
        this.backtomenuButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        initButtons();
        
        // Gombok és mezők hozzáadása
        this.add(moveButton);
        this.add(Box.createRigidArea(new Dimension(0, 15)));
        this.add(buyButton);
        this.add(Box.createRigidArea(new Dimension(0, 15)));
        this.add(equipButton);
        this.add(Box.createRigidArea(new Dimension(0, 15)));
        this.add(stepButton);
        
        this.add(Box.createRigidArea(new Dimension(0, 20)));
        this.add(new JLabel("Input:"));
        this.add(inputField);
        this.add(new JLabel("Output:"));
        this.add(outputField);
        this.add(Box.createVerticalGlue());
        this.add(backtomenuButton);
    }

    private void initButtons() {
        stepButton = new JButton("STEP");
        moveButton = new JButton("MOVE");
        buyButton = new JButton("BUY");
        equipButton = new JButton("EQUIP");

        JButton[] actionButtons = {moveButton, buyButton, equipButton, stepButton};
        for (JButton btn : actionButtons) {
            btn.setFont(new Font("Arial", Font.BOLD, 36));
            btn.setBackground(new Color(0, 238, 255));
            btn.setForeground(Color.BLACK);
            btn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
            btn.setFocusPainted(false);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setPreferredSize(new Dimension(240, 65));
            btn.setMaximumSize(new Dimension(240, 65));
        }

        stepButton.addActionListener(e -> {
            game.simulateStep(); // Parancs a modellnek
            statusPanel.setStatusText("Status: Step taken");
        });

        moveButton.addActionListener(e -> statusPanel.setStatusText("Status: Moving"));
        equipButton.addActionListener(e -> statusPanel.setStatusText("Status: Equipping"));

        buyButton.addActionListener(e -> {
            statusPanel.setStatusText("Status: Buying");
            
            String[] availableItems = {
                "CrushedStoneHead", "DragonHead", "IceBreakerHead", 
                "SalterHead", "SweepingHead", "ThrowerHead",
                "Salt", "BioKerosene", "SnowPlower"
            };
            
            String selectedItem = (String) JOptionPane.showInputDialog(
                    this,                                 
                    "Válassz egy tárgyat a vásárláshoz:", 
                    "Bolt",                               
                    JOptionPane.QUESTION_MESSAGE,         
                    null,                                 
                    availableItems,                       
                    availableItems[0]                     
            );

            if (selectedItem != null) {
                //game.buyItem(selectedItem);
                outputField.setText("Bought: " + selectedItem);
            } else {
                outputField.setText("Vásárlás megszakítva");
            }
        });
    }

    public JButton getBackToMenuButton() { return backtomenuButton; }
}