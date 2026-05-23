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
        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e){
                int mouseX = e.getX();
                int mouseY = e.getY();

                for(Point p : game.getCityMap().getPoints()){
                    if(Math.hypot(p.getX() - mouseX, p.getY() - mouseY) < 25){
                        game.setSelectedPoint(p);
                        game.notifyObservers();
                        return;
                    }
                }
            }

        });
    }
    /**
     * A JPanel felülírt kirajzoló metódusa. Ez felel a grafikai elemek képernyőre festéséért.
     * @param g A Graphics objektum, amire rajzolhatunk.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // Háttér törlése
        Graphics2D g2d = (Graphics2D) g;
        
        // Élvonal-simítás (Antialiasing) bekapcsolása a szebb vonalakért
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Információs szöveg kirajzolása a bal felső sarokba (FEHÉRREL)
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.drawString("Várostérkép", 10, 20);

        // Biztonsági ellenőrzés: ha nincs modell, nem rajzolunk mást
        if (game == null || game.getCityMap() == null) return;

        // 1. Sávok (Lanes) kirajzolása
        for (Lane lane : game.getCityMap().getLanes()) {
            drawLane(g2d, lane);
        }

        // 2. Csomópontok (Points) kirajzolása
        for (Point p : game.getCityMap().getPoints()) {
            drawPoint(g2d, p);
        }

        // 3. Járművek (Vehicles) kirajzolása
        if (game.getCityMap().getVehicles() != null) {
            for (Vehicle v : game.getCityMap().getVehicles()) {
                drawVehicle(g2d, v);
            }
        }
    }

    /**
     * Egyetlen sáv (út) kirajzolása a megadott Graphics2D objektumra.
     * @param g2d A rajzoló objektum.
     * @param lane A kirajzolandó sáv.
     */
    private void drawLane(Graphics2D g2d, Lane lane) {
        Point start = lane.getStartPoint();
        Point end = lane.getEndPoint();
        
        if (start == null || end == null) return;

        // 5 pixel vastag vonal
        g2d.setStroke(new BasicStroke(5)); 
        g2d.drawLine(start.getX(), start.getY(), end.getX(), end.getY());
    }

    /**
     * Egyetlen csomópont kirajzolása.
     * @param g2d A rajzoló objektum.
     * @param point A kirajzolandó csomópont.
     */
    private void drawPoint(Graphics2D g2d, Point point) {
        g2d.setColor(Color.DARK_GRAY);
        // Egy 20x20-as kör rajzolása a pont középpontja köré
        g2d.fillOval(point.getX() - 10, point.getY() - 10, 20, 20);
        
        // Azonosító kiírása a pont fölé
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
        g2d.drawString(point.getId(), point.getX() - 10, point.getY() - 15);
    }

    private void drawVehicle(Graphics2D g2d, Vehicle vehicle) {
        Point p = vehicle.getCurrentPoint();
        if (p == null) return;

        int x = p.getX();
        int y = p.getY();

        String vehicleType = vehicle.getClass().getSimpleName();
        Image img = AssetManager.getIcon(vehicleType);

        if (img != null) {
            // Méret Pixelben 
            int imgWidth = 32; 
            int imgHeight = 32;

            // Kiszámoljuk a középpontot az új méretek alapján
            int imgX = x - (imgWidth / 2);
            int imgY = y - (imgHeight / 2);
            
            // Kirajzolás az extra szélesség és magasság paraméterekkel
            g2d.drawImage(img, imgX, imgY, imgWidth, imgHeight, null);
        } else {
            // Ha nem találja a png fájlt, rajzol egy piros négyzetet
            g2d.setColor(Color.RED);
            g2d.fillRect(x - 8, y - 8, 16, 16);
            g2d.setColor(Color.WHITE);
            g2d.drawString("!", x - 2, y + 4);
        }
    }

    /**
     * Az Observer minta update metódusa. Újrarajzolja a panelt.
     */
    @Override
    public void update() {
        this.repaint();
    }
}