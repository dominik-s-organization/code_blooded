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

    public MainFrame() {
        this(new Game());
    }

    public MainFrame(Game game) {
        this.game = game;

        setTitle("Plow Masters");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        this.setLayout(new BorderLayout());
        gamePanel = new GamePanel(game);
        controlPanel = new ControlPanel(game);

        this.add(gamePanel, BorderLayout.CENTER);
        this.add(controlPanel, BorderLayout.EAST);

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