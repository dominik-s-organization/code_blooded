package view_controller;
import model.Game;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Graphics2D;
import model.Lane;
import model.Vehicle;

import javax.swing.JPanel ;

// A GamePanel osztály felelős a játék grafikus megjelenítéséért.
public class GamePanel extends JPanel implements GameObserver {
    private Game game;
    private double scale;
    private ControlPanel controlPanel;

    public GamePanel() {
        this.controlPanel = new ControlPanel();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, g2d);

        for (Lane lane : game.getCityMap().getLanes()) {
            drawLane(g2d, lane);
        }

        for(Point p : game.getCityMap().getPoints()) {
            drawPoint(g2d, p);
        }

        for(Vehicle v : game.getVehicles()) {
            drawVehicle(g2d, v);
        }
    }

    public void update() {
        this.repaint();
    }

    public void calculateCoordinates(Point playerPosition) {
        // Calculate the coordinates for drawing the game elements
    }
}
