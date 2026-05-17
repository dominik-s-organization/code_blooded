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
    
    /** Referencia a szimulációs modellre. */    
    private Game game;
    private ControlPanel controlPanel;

    /**
     * A GamePanel konstruktora. Inicializálja a panelt, beállítja a fekete hátteret,
     * és feliratkozik a modell változásaira.
     * @param game A Game objektum, amely a város térképét tartalmazza.
     */
    public GamePanel(Game game) {
        this.game = game;
        game.addObserver(this);
        this.controlPanel = new ControlPanel(game);
        this.setBackground(new Color(0, 0, 0));
        this.setPreferredSize(new Dimension(800,600)); // Set background to black
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
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
        g2d.drawString("Simulation runs", 10, 20);

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

    /**
     * Egyetlen sáv (út) kirajzolása a megadott Graphics2D objektumra.
     * A sáv színe a rajta lévő hó mennyiségétől függ.
     * @param g2d A rajzoló objektum.
     * @param lane A kirajzolandó sáv.
     */
    private void drawLane(Graphics2D g2d, Lane lane) {
        Point start = lane.getStartPoint();
        Point end = lane.getEndPoint();

        g2d.setColor(AssetManager.getSnowColor(lane.getSnow().getSnowLevel()));
        g2d.setStroke(new java.awt.BasicStroke(3));
        g2d.drawLine(start.getX(), start.getY(), end.getX(), end.getY());
        // Itt rajzoljuk meg a sávokat a játék térképén
    }

    /**
     * Egyetlen jármű kirajzolása az aktuális pozíciójára PNG kép segítségével.
     * @param g2d A rajzoló objektum.
     * @param vehicle A kirajzolandó jármű.
     */
    private void drawVehicle(Graphics2D g2d, Vehicle vehicle) {
        Point p = vehicle.getCurrentPoint();
        if (p == null) return;

        // Dinamikusan lekérjük a jármű osztálynevét ("Car", "Bus", vagy "SnowPlower")
        String vehicleType = vehicle.getClass().getSimpleName();
        
        // Lekérjük a hozzá tartozó képet az AssetManager-ből
        Image img = AssetManager.getIcon(vehicleType);

        int x = p.getX();
        int y = p.getY();

        if (img != null) {
            // Ha megvan a kép, középre igazítva kirajzoljuk a koordinátára
            int imgX = x - (img.getWidth(null) / 2);
            int imgY = y - (img.getHeight(null) / 2);
            g2d.drawImage(img, imgX, imgY, null);
        } else {
            // TARTALÉK (Fallback): Ha nem találja a png fájlt, rajzoljon egy piros négyzetet
            g2d.setColor(Color.RED);
            g2d.fillRect(x - 8, y - 8, 16, 16);
            g2d.setColor(Color.WHITE);
            g2d.drawString("!", x - 2, y + 4);
        }
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
