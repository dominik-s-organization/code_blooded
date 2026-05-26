package view;

import controller.ControlPanel;
import model.Game;
import model.Console;
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

    private StatusPanel statusPanel; // A státusz panel, amely a játék állását mutatja (pénz, körök)

    /**
     * Konstruktor, amely létrehozza az ablakot és beállítja a menü-játék váltást.
     * @param game A megjelenítendő Game objektum.
     */
    public MainFrame(Game game) {
        this.game = game;

        setTitle("Plow Masters");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        JMenuItem backToMenuItem = new JMenuItem("Back to Menu");
        JMenuItem newGameItem = new JMenuItem("New Game");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);
        fileMenu.add(backToMenuItem);
        fileMenu.add(newGameItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);


        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout);

        Console mainConsole = new Console(game);

        menuPanel = new MenuPanel(mainConsole,this);

        JPanel gameContainer = new JPanel(new BorderLayout());
        JPanel rightSideBar = new JPanel();
        rightSideBar.setLayout(new BoxLayout(rightSideBar, BoxLayout.Y_AXIS));

        gamePanel = new GamePanel(game);
        statusPanel = new StatusPanel(game); 
        controlPanel = new ControlPanel(game, statusPanel, mainConsole);

        rightSideBar.add(statusPanel);
        rightSideBar.add(controlPanel);
        gameContainer.add(gamePanel, BorderLayout.CENTER);
        gameContainer.add(rightSideBar, BorderLayout.EAST);

        
        game.addObserver(gamePanel);

       
        mainContainer.add(menuPanel, "MENU");
        mainContainer.add(gameContainer, "GAME");

        this.add(mainContainer);

       
        menuPanel.getNewGameButton().addActionListener(e -> {
            cardLayout.show(mainContainer, "GAME");
        });

       
        controlPanel.getBackToMenuButton().addActionListener(e -> {
            showMenu(); 
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
     * Átvált a főmenüből a játék képernyőre.
     */
    public void showGamePanel() {
        if (cardLayout != null && mainContainer != null) {
            cardLayout.show(mainContainer, "GAME"); 
        }
    }

    /**
     * Beállítja az új játék modellt a nézet számára.
     * @param game Az új Game objektum.
     */
    public void setGame(Game game) {
        this.game = game;
    }
}