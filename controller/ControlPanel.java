package controller;

import model.Game;
import model.Player;
import model.Console;
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
    private Console console;

    // A gombok a játék különböző parancsait reprezentálják, amelyek a játékos által kiválaszthatók.
    private JButton stepButton, moveButton, buyButton, equipButton, backtomenuButton;

    // Az input és output mezők a vezérlőpanel alján helyezkednek el, és a játékos által bevitt parancsokat, valamint a játék válaszait jelenítik meg.
    private JTextField inputField, outputField;

    private int activePlayerIndex = 0;

    // Konstruktor, amely inicializálja a vezérlőpanelt, beállítja a gombokat és a mezőket, valamint feliratkozik a modellre.
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

        refreshCurrentPlayerDisplay();
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
            int totalPlayers = game.getPlayers().size();
            activePlayerIndex++;

            if(activePlayerIndex >= totalPlayers){
                activePlayerIndex = 0;
                if(console != null){
                    console.processCommand("step");
                }
                outputField.setText("Round over");
                statusPanel.setStatusText("Status: New Round started!");
            } else {
                outputField.setText("Next player turn!");
            }
            refreshCurrentPlayerDisplay();
            game.notifyObservers();
        });

        moveButton.addActionListener(e -> {
            // 1. Lekérjük a soron lévő játékost
            Object currentPlayer = game.getPlayers().get(activePlayerIndex); 
            
            // 2. CÉLPONT MEGHATÁROZÁSA (Szöveg vagy Kattintás)
            model.Point targetPoint = game.getSelectedPoint(); // Alapértelmezés: a kattintott pont
            String moveCommand = inputField.getText().trim();
            
            // Ha a játékos gépelt valamit (pl. "J2"), az felülírja a kattintást!
            if (!moveCommand.isEmpty()) {
                targetPoint = null; 
                for (model.Point p : game.getCityMap().getPoints()) {
                    if (p.getId().equals(moveCommand)) {
                        targetPoint = p;
                        break;
                    }
                }
            }

            // 3. VÉGREHAJTÁS CONSOLE-ON KERESZTÜL (Így bekerül a mentésbe!)
            if (targetPoint != null) {
                model.Vehicle vehicle = null;

                // Jármű kikeresése a játékos típusa alapján
                String playerType = currentPlayer.getClass().getSimpleName();
                if (playerType.equals("SnowCleaner")) {
                    model.SnowCleaner cleaner = (model.SnowCleaner) currentPlayer;
                    if (cleaner.getSnowPlowers() != null && !cleaner.getSnowPlowers().isEmpty()) {
                        vehicle = cleaner.getSnowPlowers().get(0);
                    }
                } else if (playerType.equals("BusDriver")) {
                    model.BusDriver driver = (model.BusDriver) currentPlayer;
                    vehicle = driver.getBus(); 
                }

                if (vehicle != null && vehicle.getCurrentPoint() != null) {
                    // Megkeressük az összekötő sávot a jármű aktuális pontja és a célpont között
                    model.Lane targetLane = null;
                    for (model.Lane lane : game.getCityMap().getLanes()) {
                        if (lane.getStartPoint().equals(vehicle.getCurrentPoint()) && lane.getEndPoint().equals(targetPoint)) {
                            targetLane = lane;
                            break;
                        }
                    }

                    // Ha megvan a közvetlen sáv, generáljuk a parancsot a Console-nak!
                    if (targetLane != null) {
                        if (console == null) {
                            System.out.println("HIBA: A console változó NULL a ControlPanelben!");
                            outputField.setText("Hiba: Belső hiba (Console null)");
                            return;
                        }

                        // Összerakjuk a parancsot
                        String command = "move " + vehicle.getId() + " " + targetLane.getId();
                        System.out.println("GUI generált Move parancs: " + command);
                        
                        // Átadjuk a Console-nak: ő elmenti a txt-be ÉS beállítja a menetirányt!
                        console.processCommand(command); 

                        // Visszajelzés a felhasználónak
                        outputField.setText("Célpont rögzítve: " + targetPoint.getId());
                        statusPanel.setStatusText("Status: Move locked to " + targetPoint.getId() + ". Press STEP!");
                        
                        // Kényelmi funkciók
                        inputField.setText(""); 
                        game.setSelectedPoint(null); 
                        game.notifyObservers();
                    } else {
                        outputField.setText("Hiba: Nincs közvetlen út a járműtől ide!");
                    }
                } else {
                    outputField.setText("Hiba: Jármű nem található vagy nincs pozíciója!");
                }
            } else {
                // Ha se nem kattintott, se nem gépelt be létező pontot
                outputField.setText("Hiba: Válassz egy pontot vagy írj be egy létező ID-t!");
                statusPanel.setStatusText("Status: Waiting for move input");
            }
        });

        equipButton.addActionListener(e -> statusPanel.setStatusText("Status: Equipping"));

       buyButton.addActionListener(e -> {
            System.out.println("--- BUY GOMB MEGNYOMVA ---");
            
            try {
                String[] availableItems = {
                    "CrushedStoneHead", "DragonHead", "IceBreakerHead", 
                    "SalterHead", "SweepingHead", "ThrowerHead",
                    "Salt", "BioKerosene", "SnowPlower"
                };
                
                System.out.println("1. Ablak megnyitása.");
                String selectedItem = (String) JOptionPane.showInputDialog(
                        this, "Válassz egy tárgyat a vásárláshoz:", "Bolt", 
                        JOptionPane.QUESTION_MESSAGE, null, availableItems, availableItems[0]
                );

                if (selectedItem != null) {
                    System.out.println("2. Kiválasztott tárgy: " + selectedItem);
                    
                    // Ellenőrizzük, van-e egyáltalán játékos
                    if (game.getPlayers() == null || game.getPlayers().isEmpty()) {
                        System.out.println("HIBA: Nincs egyetlen játékos sem a listában!");
                        return;
                    }
                    
                    model.Player currentPlayer = game.getPlayers().get(activePlayerIndex);
                    String playerName = currentPlayer.getName();
                    System.out.println("3. Vásárló játékos: " + playerName);
                    
                    // Ellenőrizzük, hogy a console nem null-e, ha véletlenül nem adódott volna át rendesen
                    if (console == null) {
                        System.out.println("HIBA: A console változó NULL! Nem lett átadva a konstruktorban!");
                        return;
                    }
                    
                    String command = "buy " + playerName + " " + selectedItem + " 1";
                    System.out.println("4. Generált parancs: " + command);
                    console.processCommand(command); 
                    
                    System.out.println("Parancs sikeresen átadva a Console-nak!");
                    outputField.setText("Kiadott parancs: " + command);
                    
                    game.notifyObservers(); 
                } else {
                    System.out.println("Vásárlás megszakítva.");
                }
            } catch (Exception ex) {
                System.out.println("HIBA TÖRTÉNT A GOMBNYOMÁSKOR!");
                ex.printStackTrace(); // Ez kiírja a pontos sort, ahol elszállt!
            }
        });
    }

    private void refreshCurrentPlayerDisplay(){
        if(game.getPlayers() != null && !game.getPlayers().isEmpty()){
            Player currentPlayer = game.getPlayers().get(activePlayerIndex);
            int playerNumber = activePlayerIndex + 1;
            statusPanel.setStatusText("Player" + playerNumber + " truns");
        }
    }

    public JButton getBackToMenuButton() { return backtomenuButton; }
}