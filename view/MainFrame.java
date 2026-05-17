package view;

import controller.ControlPanel;
import model.Game;
import javax.swing.*;
import java.awt.*;

/**
 * A MainFrame osztály a játék grafikus főablakát reprezentálja.
 * Ez az osztály kezeli a nézetek (Főmenü és Játéktér) közötti váltást CardLayout segítségével.
 */
public class MainFrame extends JFrame {
    
    /** A játék üzleti logikáját (Modell) reprezentáló objektum. */
    private Game game;
    
    /** A játékteret megjelenítő grafikus panel. */
    private GamePanel gamePanel;
    
    /** A felhasználói interakciókat kezelő vezérlőpanel. */
    private ControlPanel controlPanel;
    
    /** A játék főmenüjét tartalmazó panel. */
    private MenuPanel menuPanel;
    
    /** A panelek közötti váltást kezelő CardLayout elrendezés. */
    private CardLayout cardLayout;
    
    /** A kártyákat (paneleket) összefogó fő konténer. */
    private JPanel mainContainer;

    /**
     * Konstruktor, amely létrehozza az ablakot és beállítja a menü-játék váltást.
     * @param game A megjelenítendő Game objektum.
     */
    public MainFrame(Game game) {
        this.game = game;

        setTitle("Plow Masters");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null); // Középre igazítja az ablakot
        
        // Felső menüsor beállítása
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0)); // Kilépés logikája
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        // CardLayout beállítása a váltáshoz
        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout);

        // 1. Kártya: Főmenü (MenuPanel)
        menuPanel = new MenuPanel();
        
        // 2. Kártya: Játék nézet (GamePanel + ControlPanel összefogva egy BorderLayoutban)
        JPanel gameContainer = new JPanel(new BorderLayout());
        gamePanel = new GamePanel(game);
        controlPanel = new ControlPanel(game);
        gameContainer.add(gamePanel, BorderLayout.CENTER);
        gameContainer.add(controlPanel, BorderLayout.EAST);
        
        // A Modell feliratkoztatása a GamePanelre
        game.addObserver(gamePanel);

        // Kártyák hozzáadása a fő konténerhez
        mainContainer.add(menuPanel, "MENU");
        mainContainer.add(gameContainer, "GAME");

        this.add(mainContainer);

        // ESEMÉNYKEZELÉS: Ha a menüben rákattintanak a "New Game" gombra, váltsunk a GAME kártyára!
        menuPanel.getNewGameButton().addActionListener(e -> {
            cardLayout.show(mainContainer, "GAME");
        });

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
     * Visszaváltja a nézetet a főmenüre.
     */
    public void showMenu() {
        cardLayout.show(mainContainer, "MENU");
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