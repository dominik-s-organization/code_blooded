package controller;

import model.Game;
import model.Player;
import model.Console;
import view.StatusPanel;
import javax.swing.*;
import java.awt.*;

/**
 * A ControlPanel a tiszta CONTROLLER réteg.
 * Feladata a felhasználói interakciók (gombnyomások) továbbítása a Modell felé,
 * és a Console parancsok generálása.
 */
public class ControlPanel extends JPanel {
    
    /** A játék üzleti logikáját összefogó Game objektum. */
    private Game game;
    
    /** Referencia a nézet státusz paneljére, hogy a kiírást frissíteni lehessen. */
    private StatusPanel statusPanel; 
    
    /** A parancsokat feldolgozó konzol objektum. */
    private Console console;

    /** Gomb a játék léptetéséhez (kör vége). */
    private JButton stepButton;
    
    /** Gomb a járművek mozgatásához. */
    private JButton moveButton;
    
    /** Gomb a boltban való vásárláshoz. */
    private JButton buyButton;
    
    /** Gomb a takarítófejek felszereléséhez. */
    private JButton equipButton;
    
    /** Gomb a főmenübe való visszatéréshez. */
    private JButton backtomenuButton;

    /** Szöveges beviteli mező a kézi parancsokhoz. */
    private JTextField inputField;
    
    /** Szöveges kimeneti mező a rendszerüzenetek megjelenítéséhez. */
    private JTextField outputField;

    /** Az éppen soron lévő (aktív) játékos indexe a játékosok listájában. */
    private int activePlayerIndex = 0;

    /**
     * Konstruktor, amely inicializálja a vezérlőpanelt, beállítja a gombokat és a mezőket, 
     * valamint összeköti a modellt a nézettel.
     * @param game A játék modellje.
     * @param statusPanel A státuszt megjelenítő panel.
     * @param console A parancsokat feldolgozó konzol.
     */
    public ControlPanel(Game game, StatusPanel statusPanel, Console console) {
        this.game = game;
        this.statusPanel = statusPanel;
        this.console = console;

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

        refreshCurrentPlayerDisplay();
    }

    /**
     * Inicializálja a felületen lévő gombokat és beállítja az eseménykezelőiket (ActionListener).
     */
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
            int totalPlayers = game.getPlayers().size();
            activePlayerIndex++;

            if(activePlayerIndex >= totalPlayers){
                activePlayerIndex = 0;
                game.simulateStep();
                outputField.setText("Round over");
            } else {
                outputField.setText("Next player turn!");
            }
            // CSAK a játékos nevét frissítjük!
            refreshCurrentPlayerDisplay();
            game.notifyObservers();
        });

        moveButton.addActionListener(e -> {
            Object currentPlayer = game.getPlayers().get(activePlayerIndex); 
            model.Point targetPoint = game.getSelectedPoint(); 
            String moveCommand = inputField.getText().trim();
            
            if (!moveCommand.isEmpty()) {
                targetPoint = null; 
                for (model.Point p : game.getCityMap().getPoints()) {
                    if (p.getId().equals(moveCommand)) {
                        targetPoint = p;
                        break;
                    }
                }
            }

            if (targetPoint != null) {
                boolean success = game.move(currentPlayer, targetPoint); 
                
                if (success) {
                    outputField.setText("Célpont rögzítve: " + targetPoint.getId());
                    inputField.setText(""); 
                    game.setSelectedPoint(null); 
                    game.notifyObservers();
                } else {
                    outputField.setText("Hiba: Nincs közvetlen út a járműtől ide!");
                }
            } else {
                outputField.setText("Hiba: Válassz egy pontot vagy írj be ID-t!");
            }
        });

        equipButton.addActionListener(e -> {
            try {
                if (game.getPlayers() == null || game.getPlayers().isEmpty()) {
                    outputField.setText("HIBA: Nincs játékos!");
                    return;
                }
                
                model.Player currentPlayer = game.getPlayers().get(activePlayerIndex);
                
                if (currentPlayer instanceof model.SnowCleaner) {
                    model.SnowCleaner cleaner = (model.SnowCleaner) currentPlayer;
                    java.util.List<model.Head> inventory = cleaner.getInventory();
                    
                    if (inventory == null || inventory.isEmpty()) {
                        JOptionPane.showMessageDialog(this, 
                            "Nincs felszerelhető fej a raktáradban! (Vegyél egyet a Boltban)", 
                            "Üres Inventory", 
                            JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    
                    String[] availableHeads = new String[inventory.size()];
                    for (int i = 0; i < inventory.size(); i++) {
                        availableHeads[i] = inventory.get(i).getClass().getSimpleName(); 
                    }
                    
                    String selectedHead = (String) JOptionPane.showInputDialog(
                            this, "Válassz egy fejet a felszereléshez:", "Inventory (Raktár)", 
                            JOptionPane.QUESTION_MESSAGE, null, availableHeads, availableHeads[0]
                    );
                    
                    if (selectedHead != null) {
                        if (console == null) {
                            outputField.setText("HIBA: Console nincs inicializálva!");
                            return;
                        }
                        
                        String playerName = currentPlayer.getName();
                        String command = "equip " + playerName + " " + selectedHead;
                        
                        console.processCommand(command);
                        outputField.setText("Kiadott parancs: " + command);
                        game.notifyObservers();
                    } else {
                        outputField.setText("Felszerelés megszakítva.");
                    }
                } else {
                    outputField.setText("HIBA: A jelenlegi játékos nem SnowCleaner!");
                }
            } catch (Exception ex) {
                outputField.setText("Hiba a felszerelés során!");
                ex.printStackTrace();
            }
        });

        buyButton.addActionListener(e -> {
            try {
                String[] availableItems = {
                    "CrushedStoneHead (Ár: 60)", "DragonHead (Ár: 200)", "IceBreakerHead (Ár: 150)", 
                    "SalterHead (Ár: 75)", "SweepingHead (Ár: 50)", "ThrowerHead (Ár: 120)",
                    "Salt (Ár: 5/egység)", "BioKerosene (Ár: 10/egység)", "SnowPlower (Ár: 500)", "CrushedStone (Ár: 2/egység)"
                };
                
                String selectedItemFull = (String) JOptionPane.showInputDialog(
                        this, "Válassz egy tárgyat a vásárláshoz:", "Bolt", 
                        JOptionPane.QUESTION_MESSAGE, null, availableItems, availableItems[0]
                );

                if (selectedItemFull != null) {
                    if (game.getPlayers() == null || game.getPlayers().isEmpty()) {
                        outputField.setText("HIBA: Nincs játékos!");
                        return;
                    }
                    
                    String selectedItem = selectedItemFull.split(" ")[0];
                    
                    model.Player currentPlayer = game.getPlayers().get(activePlayerIndex);
                    String playerName = currentPlayer.getName();
                    
                    if (console == null) {
                        outputField.setText("HIBA: Console nincs inicializálva!");
                        return;
                    }
                    
                    String command;
                    if (selectedItem.equals("Salt") || selectedItem.equals("BioKerosene") || selectedItem.equals("CrushedStone")) {
                        String qtyStr = JOptionPane.showInputDialog(this, "Mennyit szeretnél vásárolni ebből: " + selectedItemFull + "?");
                        if (qtyStr != null && !qtyStr.trim().isEmpty()) {
                            try {
                                int qty = Integer.parseInt(qtyStr);
                                if (qty > 0) {
                                    command = "buy " + playerName + " " + selectedItem + " " + qty;
                                } else {
                                    outputField.setText("Érvénytelen mennyiség!");
                                    return;
                                }
                            } catch (NumberFormatException ex) {
                                outputField.setText("Kérlek számot adj meg!");
                                return;
                            }
                        } else {
                            outputField.setText("Vásárlás megszakítva");
                            return;
                        }
                    } else {
                        command = "buy " + playerName + " " + selectedItem + " 1";
                    }
                    
                    console.processCommand(command); 
                    outputField.setText("Kiadott parancs: " + command);
                    game.notifyObservers(); 
                } else {
                    outputField.setText("Vásárlás megszakítva.");
                }
            } catch (Exception ex) {
                outputField.setText("Hiba a vásárlás során!");
                ex.printStackTrace(); 
            }
        });
    }

    /**
     * Frissíti a felületen az aktuálisan soron lévő játékos kijelzését a StatusPanel-en.
     */
    private void refreshCurrentPlayerDisplay(){
        if(game.getPlayers() != null && !game.getPlayers().isEmpty()){
            Player currentPlayer = game.getPlayers().get(activePlayerIndex);
            statusPanel.setStatusText("Aktuális játékos: " + currentPlayer.getName());
        }
    }

    /**
     * Visszaadja a menübe visszatérő gombot a MainFrame számára.
     * @return A Back to Menu gomb.
     */
    public JButton getBackToMenuButton() { 
        return backtomenuButton; 
    }
}