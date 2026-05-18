package view;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import model.Game;
import model.Lane;
import model.Vehicle;
import model.Point;
import controller.GameObserver;

// A GamePanel osztály felelős a játék grafikus megjelenítéséért.
public class GamePanel extends JPanel implements GameObserver {
    /** Referencia a szimulációs modellre. */
    private Game game;

    /**
     * A GamePanel konstruktora. Inicializálja a panelt, beállítja a fekete hátteret,
     * és feliratkozik a modell változásaira.
     * @param game A Game objektum, amely a város térképét tartalmazza.
     */
    public GamePanel(Game game) {
        this.game = game;
        game.addObserver(this);
        this.setBackground(Color.BLACK); // Háttér beállítása feketére
        this.setPreferredSize(new Dimension(800, 600)); 
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                for(Point point : game.getCityMap().getPoints()) {
                    if (Math.hypot(point.getX() - x, point.getY() - y) < 15) {
                        game.setSelectedPoint(point);
                        game.notifyObservers();
                        return;
                    }
                }
            }
        });
    }

    public void setGame(Game game) {
        this.game = game;
        game.addObserver(this);
        this.repaint(); // Ha új játékot kapunk, azonnal rajzoljuk újra
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        // A super.paintComponent letakarítja a panelt a beállított háttérszínnel (fekete)
        super.paintComponent(g); 
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Biztonsági ellenőrzés: ha még nincs térkép, ne csináljunk semmit
        if (game == null || game.getCityMap() == null) return;

        // Sávok (utak) kirajzolása
        if (game.getCityMap().getLanes() != null) {
            for (Lane lane : game.getCityMap().getLanes()) {
                drawLane(g2d, lane);
            }
        }

        // Csomópontok kirajzolása
        if (game.getCityMap().getPoints() != null) {
            for (Point p : game.getCityMap().getPoints()) {
                drawPoint(g2d, p);
            }
        }

        // Járművek kirajzolása
        if (game.getVehicles() != null) {
            for (Vehicle v : game.getVehicles()) {
                drawVehicle(g2d, v);
            }
        }
    }

    /**
     * Egyetlen sáv (út) kirajzolása a megadott Graphics2D objektumra.
     * A sáv színe a rajta lévő hó mennyiségétől függ.
     */
    private void drawLane(Graphics2D g2d, Lane lane) {
        Point start = lane.getStartPoint();
        Point end = lane.getEndPoint();

        // ALTERNATÍVA (Ha a Lane csak ID-kat ad vissza, akkor használd ezt a két sort helyette):
        // Point start = game.findPointById(lane.getStartPointId());
        // Point end = game.findPointById(lane.getEndPointId());

        if (start != null && end != null) {
            // A hó szintjétől függő szín az AssetManagerből
            g2d.setColor(AssetManager.getSnowColor(lane.getSnow().getSnowLevel()));
            g2d.setStroke(new java.awt.BasicStroke(3));
            g2d.drawLine(start.getX(), start.getY(), end.getX(), end.getY());
        }
    }

    /**
     * Egyetlen jármű kirajzolása az aktuális pozíciójára PNG kép segítségével.
     */
    private void drawVehicle(Graphics2D g2d, Vehicle vehicle) {
        Point p = vehicle.getCurrentPoint();
        if (p == null) return;

        String vehicleType = vehicle.getClass().getSimpleName();
        Image img = AssetManager.getIcon(vehicleType);

        int x = p.getX();
        int y = p.getY();

        if (img != null) {
            int imgX = x - (img.getWidth(null) / 2);
            int imgY = y - (img.getHeight(null) / 2);
            g2d.drawImage(img, imgX, imgY, null);
        } else {
            // Tartalék (Fallback), ha nincs kép: piros négyzet felkiáltójellel
            g2d.setColor(Color.RED);
            g2d.fillRect(x - 8, y - 8, 16, 16);
            g2d.setColor(Color.WHITE);
            g2d.drawString("!", x - 2, y + 4);
        }
    }

    //csomópontok kirajzolása szürke körökkel
    private void drawPoint(Graphics2D g2d, Point point) {
        if (point == null) return;
        g2d.setColor(Color.darkGray);
        g2d.fillOval(point.getX() - 5, point.getY() - 5, 10, 10);
        
        if(point.equals(game.getSelectedPoint())) {
             g2d.setColor(Color.YELLOW);
             g2d.setStroke(new java.awt.BasicStroke(3));
             g2d.drawOval(point.getX() - 10, point.getY() - 10, 20, 20);
        }
    }

    @Override
    public void update() {
        this.repaint();
    }
}