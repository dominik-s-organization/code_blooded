package view;

import controller.ControlPanel;
import model.Game;
import javax.swing.*;
import java.awt.*;

/**
 * A MainFrame osztály a játék grafikus főablakát reprezentálja.
 * Ez az osztály fogja össze a játékteret (GamePanel) és a vezérlőpultot (ControlPanel).
 */
public class MainFrame extends JFrame {
    /** A játék üzleti logikáját (Modell) reprezentáló objektum. */
    private Game game;
    
    /** A játékteret megjelenítő grafikus panel. */
    private GamePanel gamePanel;
    
    /** A felhasználói interakciókat kezelő vezérlőpanel. */
    private ControlPanel controlPanel;

    /**
     * Konstruktor, amely létrehozza az ablakot, beállítja a menüt, 
     * és elhelyezi a paneleket a képernyőn.
     * @param game A megjelenítendő Game objektum.
     */
    public MainFrame(Game game) {
        this.game = game;

        setTitle("Plow Masters");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout()); // Border elrendezés beállítása
        
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem newGameItem = new JMenuItem("New Game");
        JMenuItem loadGameItem = new JMenuItem("Load Game");
        JMenuItem saveGameItem = new JMenuItem("Save Game");
        
        fileMenu.add(newGameItem);
        fileMenu.add(loadGameItem);
        fileMenu.add(saveGameItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        setSize(1000, 600); // Kicsit szélesebb ablak, hogy elférjen a menü
        
        gamePanel = new GamePanel(game);
        controlPanel = new ControlPanel(game);

        // JAVÍTVA: Mindkét panelt hozzáadjuk az ablakhoz megfelelő helyre!
        this.add(gamePanel, BorderLayout.CENTER);
        this.add(controlPanel, BorderLayout.EAST);

        game.addObserver(gamePanel);

        setLocationRelativeTo(null); // Középre igazítja az ablakot
        setVisible(true);
    }

    /**
     * Felülírt metódus az ablak láthatóságának beállításához.
     * @param visible Igaz esetén megjeleníti, hamis esetén elrejti az ablakot.
     */
    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
    }

    /**
     * Elrejti a játékablakot és megjeleníti a főmenüt.
     */
    public void showMenu() {
        this.setVisible(false);
    }

    /**
     * Beállítja az új játék modellt a nézet számára.
     * @param game Az új Game objektum.
     */
    public void setGame(Game game) {
        this.game = game;
        gamePanel.setGame(game);
    }

}