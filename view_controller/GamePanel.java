package view_controller;
import model.Game;
import java.awt.Graphics;
import javax.swing.JPanel ;

// A GamePanel osztály felelős a játék grafikus megjelenítéséért.
public class GamePanel extends JPanel implements GameObserver {
    private Game game;
    private double scale;

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    public void update() {
        repaint();
    }

    public void calculateCoordinates() {
        // Calculate the coordinates for drawing the game elements
    }
}
