package view;

import controller.ControlPanel;
import model.Game;
import javax.swing.*;
import java.awt.*;

// A MainFrame osztály a játék fő ablakát reprezentálja, amely tartalmazza a játék panelt és a vezérlő panelt.
public class MainFrame extends JFrame {
    private Game game;
    private GamePanel gamePanel;
    private ControlPanel controlPanel;

    public MainFrame(Game game) {
        this.game = game;

        setTitle("Plow Masters");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
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

        setSize(800, 600);
        this.setLayout(new BorderLayout());
        gamePanel = new GamePanel(game);
        controlPanel = new ControlPanel(game);

        this.add(gamePanel, BorderLayout.CENTER);

        game.addObserver(gamePanel);

        pack();
        setLocationRelativeTo(null); // Center the window on the screen
        setVisible(true);
    }

    public void setVisible(boolean visible) {
        super.setVisible(visible);
    }

    public void setGame(Game game) {
        this.game = game;
        gamePanel.setGame(game);
    }

}