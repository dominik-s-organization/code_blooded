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
    
    /** Gomb új játékos dinamikus hozzáadásához. */
    private JButton addPlayerButton;
    
    /** Gomb a főmenübe való visszatéréshez. */
    private JButton backtomenuButton;

    /** Szöveges beviteli mező a kézi parancsokhoz. */
    private JTextField inputField;
    
    /** Szöveges kimeneti mező a rendszerüzenetek megjelenítéséhez. */
    private JTextField outputField;

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
        this.add(addPlayerButton); // Új gomb hozzáadása a panelhez!
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
        addPlayerButton = new JButton("ADD PLAYER");

        // Egységes formázás az összes gombra
        JButton[] actionButtons = {moveButton, buyButton, equipButton, addPlayerButton, stepButton};
        for (JButton btn : actionButtons) {
            btn.setFont(new Font("Arial", Font.BOLD, 32)); // Kicsit kisebb betű, hogy kiférjen
            btn.setBackground(new Color(0, 238, 255));
            btn.setForeground(Color.BLACK);
            btn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
            btn.setFocusPainted(false);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setPreferredSize(new Dimension(240, 55)); // Kicsit laposabb gombok, hogy szép legyen
            btn.setMaximumSize(new Dimension(240, 55));
        }

        // --- ÚJ JÁTÉKOS HOZZÁADÁSA LOGIKA ---
        addPlayerButton.addActionListener(e -> {
            // 1. NÉV BEKÉRÉSE
            String newName = JOptionPane.showInputDialog(this, "Add meg az új játékos nevét:", "Új játékos", JOptionPane.QUESTION_MESSAGE);
            
            if (newName != null && !newName.trim().isEmpty()) {
                
                // 2. SZEREPKÖR VÁLASZTÁSA (Lenyitható menü)
                String[] roles = {"SnowCleaner", "BusDriver"};
                String selectedRole = (String) JOptionPane.showInputDialog(
                        this, 
                        "Válassz szerepkört a játékosnak:", 
                        "Szerepkör kiválasztása", 
                        JOptionPane.QUESTION_MESSAGE, 
                        null, 
                        roles, 
                        roles[0]
                );

                if (selectedRole != null) {
                    model.Player newPlayer;
                    model.Vehicle vehicleToSpawn = null;

                    // 3. PÉLDÁNYOSÍTÁS A SZEREP ALAPJÁN
                    if (selectedRole.equals("SnowCleaner")) {
                        model.SnowCleaner cleaner = new model.SnowCleaner(newName);
                        cleaner.setMoney(1000);
                        newPlayer = cleaner;
                        if (!cleaner.getSnowPlowers().isEmpty()) {
                            vehicleToSpawn = cleaner.getSnowPlowers().get(0);
                            vehicleToSpawn.setId("plower_" + newName);
                        }
                    } else {
                        // BusDriver logika
                        model.BusDriver driver = new model.BusDriver(newName);
                        newPlayer = driver;
                        
                        // KÖZVETLENÜL Bus típusként kérjük le a járművet az elején
                        model.Bus bus = driver.getBus();
                        vehicleToSpawn = bus; // Az ősosztályos változóba is betesszük a későbbi lerakáshoz
                        
                        if (bus != null) {
                            bus.setId("bus_" + newName);
                            
                            // --- Úticél és Kezdőpont beállítása (instanceof és kasztolás nélkül!) ---
                            java.util.List<model.Point> points = game.getCityMap().getPoints();
                            
                            if (points != null && points.size() > 1) {
                                java.util.Random rand = new java.util.Random();
                                
                                // Sorsolunk egy kezdőpontot
                                model.Point start = points.get(rand.nextInt(points.size()));
                                bus.setBeginningPoint(start);
                                
                                // Sorsolunk egy végállomást, ami NEM egyenlő a kezdőponttal
                                model.Point end;
                                do {
                                    end = points.get(rand.nextInt(points.size()));
                                } while (end.equals(start));
                                bus.setEndPoint(end);
                            }
                        }
                    }

                    // 4. VÉLETLENSZERŰ LERAKÁS (SPAWN)
                    java.util.List<model.Point> points = game.getCityMap().getPoints();
                    if (points != null && !points.isEmpty() && vehicleToSpawn != null) {
                        java.util.Random rand = new java.util.Random();
                        model.Point spawnPoint = points.get(rand.nextInt(points.size()));
                        
                        vehicleToSpawn.setCurrentPoint(spawnPoint);
                        spawnPoint.addVehicle(vehicleToSpawn);
                        game.getCityMap().addVehicle(vehicleToSpawn);
                    }

                    // 5. REGISZTRÁCIÓ ÉS FRISSÍTÉS
                    game.addPlayer(newPlayer);
                    outputField.setText("Új játékos: " + newName + " (" + selectedRole + ")");
                    
                    if (game.getPlayers().size() == 1) {
                        refreshCurrentPlayerDisplay();
                    }
                    game.notifyObservers();
                }
            }
        });

        stepButton.addActionListener(e -> {
            if (game.getPlayers().isEmpty()) {
                outputField.setText("Nincs játékos! Adj hozzá egyet.");
                return;
            }
            
            // 1. Szólunk a Modellnek, hogy váltson játékost, és megkérdezzük: Vége a körnek?
            boolean isRoundOver = game.nextPlayerTurn(); 
            
            // 2. CSAK AKKOR futtatjuk a szimulációt (csak akkor mozognak az autók), ha mindenki lépett!
            if (isRoundOver) {
                if(console != null){
                    console.processCommand("step");
                }
                outputField.setText("Kör vége! A világ szimulálva, új kör indul.");
            } else {
                outputField.setText("Lépés rögzítve! Jön a következő játékos.");
            }

            refreshCurrentPlayerDisplay();
        });

        moveButton.addActionListener(e -> {
            // 1. Lekérjük a soron lévő játékost
            model.Player currentPlayer = game.getCurrentPlayer();
            if (currentPlayer == null) return;
            
            // 2. CÉLPONT MEGHATÁROZÁSA (Szöveg vagy Kattintás)
            model.Point targetPoint = game.getSelectedPoint(); // Alapértelmezés: a kattintott pont
            String moveCommand = inputField.getText().trim();
            
            // Ha a játékos gépelt valamit (pl. "J2"), az felülírja a kattintást!
            // Ilyenkor megkeressük ezt az ID-t a térképen lévő pontok között:
            if (!moveCommand.isEmpty()) {
                targetPoint = null; 
                for (model.Point p : game.getCityMap().getPoints()) {
                    if (p.getId().equals(moveCommand)) {
                        targetPoint = p;
                        break;
                    }
                }
            }

            // 3. VÉGREHAJTÁS (Ha sikeresen találtunk egy létező célpontot)
            if (targetPoint != null) {
                // Most már a megfelelő objektumokat adjuk át a Modellnek!
                boolean success = game.move(currentPlayer, targetPoint); 
                
                if (success) {
                    // Visszajelzés a felhasználónak
                    outputField.setText("Célpont rögzítve: " + targetPoint.getId());
                    statusPanel.setStatusText("Status: Move locked to " + targetPoint.getId() + ". Press STEP!");
                    
                    // Kényelmi funkció: kiürítjük a mezőt és levesszük a sárga kijelölést
                    inputField.setText(""); 
                    game.setSelectedPoint(null); 
                    game.notifyObservers();
                } else {
                    outputField.setText("Hiba: Nincs közvetlen út a járműtől ide!");
                }
            } else {
                // Ha se nem kattintott, se nem gépelt be létező pontot
                outputField.setText("Hiba: Válassz egy pontot vagy írj be egy létező ID-t!");
                statusPanel.setStatusText("Status: Waiting for move input");
            }
        });

        equipButton.addActionListener(e -> {
            try {
                if (game.getPlayers() == null || game.getPlayers().isEmpty()) {
                    outputField.setText("HIBA: Nincs játékos!");
                    return;
                }
                
                model.Player currentPlayer = game.getCurrentPlayer();
                if (currentPlayer == null) return;
                
                if (currentPlayer.getType().equals("snow_cleaner")) {
                    model.SnowCleaner cleaner = (model.SnowCleaner) currentPlayer;
                    java.util.List<model.Head> inventory = cleaner.getInventory();
                    
                    if (inventory == null || inventory.isEmpty()) {
                        JOptionPane.showMessageDialog(this, 
                            "Nincs felszerelhető fej a raktáradban! (Vegyél egyet a Boltban)", 
                            "Üres Inventory", JOptionPane.WARNING_MESSAGE);
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
                        if (console == null) return;

                        // A fix listázás helyett lekérjük a Game-től az éppen soron lévő aktív járművet
                        model.Vehicle activeVehicle = game.getActiveVehicle();
                        if (activeVehicle == null) {
                            outputField.setText("Hiba: Nincs aktív jármű!");
                            return;
                        }
    
                        String vehicleId = activeVehicle.getId();
                        String command = "equip " + vehicleId + " " + selectedHead;
                        console.processCommand(command);
    
                        outputField.setText("Kiadott parancs: " + command);
                        game.notifyObservers();
                    }
                }
            } catch (Exception ex) {
                outputField.setText("Hiba a felszerelés során!");
                ex.printStackTrace();
            }
        });

        buyButton.addActionListener(e -> {
            try {
                String[] availableItems = {
                    "Salt (Ár: 1/egység)", "CrushedStone (Ár: 2/egység)", "BioKerosene (Ár: 3/egység)",
                    "ThrowerHead (Ár: 180)", "IceBreakerHead (Ár: 200)", "CrushedStoneHead (Ár: 300)", 
                    "SalterHead (Ár: 350)", "DragonHead (Ár: 400)", "SnowPlower (Ár: 1000)"
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
                    model.Player currentPlayer = game.getCurrentPlayer();
                    if (currentPlayer == null) return;
                    String playerName = currentPlayer.getName();
                    
                    if (console == null) return;
                    
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
            model.Player currentPlayer = game.getCurrentPlayer();
            if (currentPlayer == null) return;
            
            String vehicleInfo = "";
            if (currentPlayer instanceof model.SnowCleaner) {
                model.Vehicle activeVehicle = game.getActiveVehicle();
                if (activeVehicle != null) {
                    vehicleInfo = " (Jármű: " + activeVehicle.getId() + ")";
                }
            }
            
            statusPanel.setStatusText("Aktuális játékos: " + currentPlayer.getName() + vehicleInfo);
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