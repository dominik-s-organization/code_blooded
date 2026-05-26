package view;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import model.Game;
import model.Lane;
import model.Vehicle;
import model.Point;
import model.Snow;
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
        g2d.setColor(Color.RED);
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.drawString("CityMap", 10, 20);

        // Biztonsági ellenőrzés: ha nincs modell, nem rajzolunk mást
        if (game == null || game.getCityMap() == null) return;

        // 1. Sávok (Lanes) kirajzolása
        for (Lane lane : game.getCityMap().getLanes()) {
            Color laneColor = determineLaneColor(lane);
            drawLane(g2d, lane.getStartPoint(), lane.getEndPoint(), laneColor);
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
     * @param startNode kezdőpont
     * @param endNode végpont
     * @param lane A kirajzolandó sáv.
     */
    private void drawLane(Graphics2D g2d, Point startNode, Point endNode, Color roadColor) {
        int x1 = startNode.getX();
        int y1 = startNode.getY();
        int x2 = endNode.getX();
        int y2 = endNode.getY();

        // 1. Kiszámoljuk az irányvektort
        double dx = x2 - x1;
        double dy = y2 - y1;
        double length = Math.sqrt(dx * dx + dy * dy);

        // 2. Kiszámoljuk a normálvektort (merőleges irány) a "jobbra tarts" elv alapján
        // (A -dy, dx a balra, a dy, -dx a jobbra tolás a képernyő koordinátarendszerében)
        double nx = -dy / length;
        double ny = dx / length;

        // 3. Eltolás mértéke pixelben (pl. 10 pixel)
        int offset = 10;

        // 4. Új, eltolt koordináták
        int startX = (int) (x1 + nx * offset);
        int startY = (int) (y1 + ny * offset);
        int endX = (int) (x2 + nx * offset);
        int endY = (int) (y2 + ny * offset);

        // 5. Vonal (sáv) kirajzolása
        g2d.setColor(roadColor);
        g2d.setStroke(new BasicStroke(15)); // Sáv vastagsága
        g2d.drawLine(startX, startY, endX, endY);
    }

    /**
     * Meghatározza egy sáv színét a rajta lévő hó és jég állapota alapján.
     * @param lane A vizsgált sáv
     * @return A sáv megjelenítési színe
     */
    private Color determineLaneColor(Lane lane) {
        if (lane == null || lane.getSnow() == null) {
            return Color.DARK_GRAY; // Biztonsági alapértelmezett szín
        }

        Snow snow = lane.getSnow();

        // 1. Prioritás: Zúzottkő (kavics)
        if (snow.getCrushedStoneLevel() > 0) {
            return new Color(139, 115, 85); // Barna
        }
        // 2. Prioritás: Jégpáncél
        else if (snow.isIce()) {
            return Color.CYAN; // Világoskék
        } 
        // 3. Prioritás: Jégtörmelék
        else if (snow.isBrokenIce()) {
            return new Color(192, 192, 192); // Klasszikus szürke (Silver)
        } 
        // 4. Prioritás: HÓ SZÍNÁTMENET
        else if (snow.getSnowLevel() > 0) {
            int level = snow.getSnowLevel();
            
            if (level >= 10) {
                return Color.WHITE; // Vastag hó: Vakítóan fehér
            } 
            else if (level >= 5) {
                return new Color(220, 220, 220); // Közepes hó: Törtfehér
            } 
            else {
                return new Color(160, 160, 160); // Vékony hó: Szürkésfehér (átüt az aszfalt)
            }
        } 
        // 5. Prioritás: Tiszta (0-s szint)
        else {
            return Color.DARK_GRAY; // Tiszta aszfalt
        }
    }

    /**
     * Egyetlen csomópont kirajzolása.
     * @param g2d A rajzoló objektum.
     * @param point A kirajzolandó csomópont.
     */
    private void drawPoint(Graphics2D g2d, Point point) {
        g2d.setColor(Color.white);
        // Egy 20x20-as kör rajzolása a pont középpontja köré
        g2d.fillOval(point.getX() - 20, point.getY() - 20, 50, 50);
        
        // Azonosító kiírása a pont fölé
        g2d.setColor(Color.darkGray);
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
        g2d.drawString(point.getId(), point.getX() - 10, point.getY() - 15);
    }

    /**
     * Egyetlen jármű kirajzolása.
     * @param g2d A rajzoló objektum.
     * @param vehicle A kirajzolandó jármű.
     */
    private void drawVehicle(Graphics2D g2d, Vehicle vehicle) {
        model.Point p = vehicle.getCurrentPoint();
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

        // AZONOSÍTÓ KIÍRÁSA A JÁRMŰ ALÁ
        String id = vehicle.getId();
        if (id != null) {
            Font originalFont = g2d.getFont();
            g2d.setFont(new Font("Arial", Font.BOLD, 11)); // Kicsi, de jól olvasható betű
            
            // A szöveg Y koordinátája: a kép alja (y+16) alá tesszük egy kicsivel
            int textY = y + 28; 
            
            // Hogy kb. középen legyen a szöveg (egy 5-6 karakteres ID esetén ez pont jó)
            int textX = x - 18; 
            
            // 1. Fehér kontúr rajzolása, hogy az utakon is tökéletesen olvasható legyen
            g2d.setColor(Color.WHITE);
            g2d.drawString(id, textX + 1, textY + 1);
            g2d.drawString(id, textX - 1, textY - 1);
            g2d.drawString(id, textX + 1, textY - 1);
            g2d.drawString(id, textX - 1, textY + 1);
            
            // 2. A fekete azonosító szöveg
            g2d.setColor(Color.RED);
            g2d.drawString(id, textX, textY);
            
            // Visszaállítjuk az eredeti betűtípust
            g2d.setFont(originalFont);
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