package view;
import model.Game;
import model.Lane;
import model.Point;
import model.Vehicle;
import java.awt.*;

import javax.swing.JPanel ;

import view.AssetManager;
import controller.ControlPanel;
import controller.GameObserver;

// A GamePanel osztály felelős a játék grafikus megjelenítéséért.
public class GamePanel extends JPanel implements GameObserver {
    private Game game;
    private ControlPanel controlPanel;

    public GamePanel(Game game) {
        this.game = game;
        game.addObserver(this);
        this.controlPanel = new ControlPanel(game);
        this.addMouseListener(controlPanel);
        this.setBackground(new Color(0, 0, 0)); // Set background to black
    }

    public void setGame(Game game) {
        this.game = game;
        game.addObserver(this);
        this.controlPanel = new ControlPanel(game);
    }

    @Override
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

    private void drawLane(Graphics2D g2d, Lane lane) {
        Point start = lane.getStartPoint();
        Point end = lane.getEndPoint();

        g2d.setColor(AssetManager.getSnowColor(lane.getSnow().getSnowLevel()));
        g2d.setStroke(new java.awt.BasicStroke(3));
        g2d.drawLine(start.getX(), start.getY(), end.getX(), end.getY());
        // Itt rajzoljuk meg a sávokat a játék térképén
    }

    private void drawVehicle(Graphics2D g2d, Vehicle vehicle) {
        AssetManager assetManager = new AssetManager();
        Image img = assetManager.getIcon("Car"); // Például egy autó ikon
        if (img != null) {
            int x = vehicle.getCurrentPoint().getX() - img.getWidth(null) / 2;
            int y = vehicle.getCurrentPoint().getY() - img.getHeight(null) / 2;
            g2d.drawImage(img, x, y, null);
        }
        // Itt rajzoljuk meg a járműveket a játék térképén
    }

    private void drawPoint(Graphics2D g2d, Point point) {
        g2d.setColor(Color.darkGray);
        g2d.fillOval(point.getX() - 5, point.getY() - 5, 10, 10);
        // Itt rajzoljuk meg a pontokat a játék térképén
    }

    public void update() {
        this.repaint();
    }

    public void calculateCoordinates(Point playerPosition) {
        // Calculate the coordinates for drawing the game elements
    }
}
