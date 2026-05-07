package view_controller;
import model.Game;


// A MainFrame osztály a játék fő ablakát reprezentálja, amely tartalmazza a játék panelt és a vezérlő panelt.
public class MainFrame {
    private Game game;
    private GamePanel gamePanel;
    private ControlPanel controlPanel;

    public MainFrame() {
        game = new Game();
        gamePanel = new GamePanel();
        controlPanel = new ControlPanel();
    }
}