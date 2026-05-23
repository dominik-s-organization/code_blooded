package model;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import controller.GameObserver;

/**
 * A Game osztály a játék fő osztálya, amely kezeli a játékmenetet.
 */
public class Game implements IdGenerator {
    private CityMap city; // A város térképe, amely tartalmazza a pontokat, útvonalakat és járműveket.
    private List<Player> players; // A játékosok listája.
    private Store store; // A játékban elérhető tárgyak boltja.
    private Map<String, Integer> idCounters; // Az egyedi azonosító számlálók tárolása.
    private List<GameObserver> observers; // A játék megfigyelői, akik értesülnek a játék állapotváltozásairól.
    private Point selectedPoint; // A játékban éppen kiválasztott pont, amelyre a játékos interakciót hajt végre.
    private Player currentPlayer; // Az aktuális játékos, aki éppen soron van.
    private int round; // A játék aktuális köre, amely a játék előrehaladását jelzi.
    public List<Lane> lanes; // A játékban található sávok listája, amelyek az útvonalakat reprezentálják.
    public List<Point> points; // A játékban található pontok listája, amelyek a sávok végpontjait reprezentálják.
    public List<Vehicle> vehicles; // A játékban található járművek listája, amelyek a forgalmat reprezentálják.

    public Game() {
        city = new CityMap();
        players = new ArrayList<>();
        store = new Store();
        idCounters = new HashMap<>();
        observers = new ArrayList<>();
        lanes = new ArrayList<>();
        points = new ArrayList<>();
        vehicles = new ArrayList<>();
        selectedPoint = null;
        currentPlayer = null;
        round = 0;
    }

    public void initTestMap() {
        CityMap map = this.getCityMap();

        // ==========================================
        // 1. CSOMÓPONTOK LÉTREHOZÁSA (5 csomópontos hálózat)
        // ==========================================
        
        CrossRoads center = new CrossRoads("crossroads_center"); center.setX(400); center.setY(350);
        Junction home = new Junction("junction_home"); home.setX(150); home.setY(150); // Bal Felső
        Junction school = new Junction("junction_school"); school.setX(650); school.setY(150); // Jobb Felső
        Junction industry = new Junction("junction_industry"); industry.setX(150); industry.setY(550); // Bal Alsó
        Tunnel tunnel = new Tunnel("tunnel_east"); tunnel.setX(650); tunnel.setY(550); // Jobb Alsó

        map.addPoint(center); map.addPoint(home); map.addPoint(school); 
        map.addPoint(industry); map.addPoint(tunnel);

        // ==========================================
        // 2. SÁVOK BEKÖTÉSE (Normál 2 sávos utak)
        // ==========================================

        // Kertváros <-> Központ
        createTwoWayRoad(map, home, center, "home_to_center");
        // Iskola <-> Központ
        createTwoWayRoad(map, school, center, "school_to_center");
        // Alagút <-> Központ
        createTwoWayRoad(map, tunnel, center, "tunnel_to_center");

        // Elkerülő utak (hogy körbe is lehessen menni)
        createTwoWayRoad(map, home, industry, "home_to_industry");
        createTwoWayRoad(map, school, tunnel, "school_to_tunnel");

        // ==========================================
        // 3. A 4 SÁVOS AUTÓPÁLYA (Ipari Park <-> Központ)
        // ==========================================
        
        // 1. sáv ODA
        Lane ind_cen_1 = new Lane("lane_ind_to_cen_1");
        ind_cen_1.setStartPoint(industry); ind_cen_1.setEndPoint(center);
        industry.addOutgoingLane(ind_cen_1); center.addIncomingLane(ind_cen_1);
        map.addLane(ind_cen_1);

        // 2. sáv ODA (Párhuzamos az elsővel!)
        Lane ind_cen_2 = new Lane("lane_ind_to_cen_2");
        ind_cen_2.setStartPoint(industry); ind_cen_2.setEndPoint(center);
        industry.addOutgoingLane(ind_cen_2); center.addIncomingLane(ind_cen_2);
        map.addLane(ind_cen_2);

        // 1. sáv VISSZA
        Lane cen_ind_1 = new Lane("lane_cen_to_ind_1");
        cen_ind_1.setStartPoint(center); cen_ind_1.setEndPoint(industry);
        center.addOutgoingLane(cen_ind_1); industry.addIncomingLane(cen_ind_1);
        map.addLane(cen_ind_1);

        // 2. sáv VISSZA
        Lane cen_ind_2 = new Lane("lane_cen_to_ind_2");
        cen_ind_2.setStartPoint(center); cen_ind_2.setEndPoint(industry);
        center.addOutgoingLane(cen_ind_2); industry.addIncomingLane(cen_ind_2);
        map.addLane(cen_ind_2);

        // Hó beállítása a 4 sávos úton (teszteléshez)
        ind_cen_1.getSnow().setSnowLevel(10);
        ind_cen_2.getSnow().setSnowLevel(10);

        // ==========================================
        // 4. JÁTÉKOSOK ÉS JÁRMŰVEK BEÁLLÍTÁSA
        // ==========================================
        
        SnowCleaner cleaner = new SnowCleaner("Hokotro_Jani"); 
        cleaner.setMoney(2000);
        if (!cleaner.getSnowPlowers().isEmpty()) {
            model.SnowPlower plower = cleaner.getSnowPlowers().get(0);
            plower.setId("snow_plower_1");
            plower.setCurrentPoint(center); 
            center.addVehicle(plower);
            map.addVehicle(plower);
        }
        this.addPlayer(cleaner);

        BusDriver busDriver = new BusDriver("Buszos_Bela");
        if (busDriver.getBus() != null) { 
            model.Bus bus = busDriver.getBus();
            bus.setId("bus_1");
            bus.setCurrentPoint(home); 
            home.addVehicle(bus);
            map.addVehicle(bus);
        }
        this.addPlayer(busDriver);

        Car car = new Car(); 
        car.setId("car_1");
        car.setHome(home); car.setWork(industry);
        car.setCurrentPoint(home); 
        home.addVehicle(car);
        map.addVehicle(car);

        this.notifyObservers();
    }

    /**
     * Segédmetódus a normál 2 sávos (oda-vissza) utak gyorsabb bekötéséhez.
     * Ezt is másold be a CityMap/Game osztályba az initTestMap mellé!
     */
    private void createTwoWayRoad(CityMap map, model.Point p1, model.Point p2, String baseName) {
        Lane oda = new Lane("lane_" + baseName + "_oda");
        oda.setStartPoint(p1); oda.setEndPoint(p2);
        p1.addOutgoingLane(oda); p2.addIncomingLane(oda);
        map.addLane(oda);

        Lane vissza = new Lane("lane_" + baseName + "_vissza");
        vissza.setStartPoint(p2); vissza.setEndPoint(p1);
        p2.addOutgoingLane(vissza); p1.addIncomingLane(vissza);
        map.addLane(vissza);
    }

     // A megfigyelő hozzáadása a játékhoz, hogy értesülhessen a játék állapotváltozásairól.
     public void addObserver(GameObserver observer) {
        observers.add(observer);
    }

    // A megfigyelők értesítése a játék állapotváltozásairól.
    // simulateStep() után hívjuk meg, hogy a nézet frissüljön a játék új állapotára.
    public void notifyObservers() {
        for (GameObserver observer : observers) {
            observer.update();
        }
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public int getCurrentRound() {
        return round;
    }

    public CityMap getCityMap(){
        return city;
    }

    public void setCity(CityMap city) {
        this.city = city;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public void setSelectedPoint(Point point) {
        this.selectedPoint = point;
    }

    public Point getSelectedPoint() {
        return this.selectedPoint;
    }

    @Override
    public String generateId(String prefix) {
        int next = idCounters.getOrDefault(prefix, 0) + 1;
        idCounters.put(prefix, next);
        return prefix + "_" + next;
    }

    @Override
    public void reset() {
        idCounters.clear();
    }

    public Vehicle getVehicleById(String id) {
        if (city == null || id == null) {
            return null;
        }
        for (Vehicle vehicle : city.getVehicles()) {
            if (id.equals(vehicle.getId())) {
                return vehicle;
            }
        }
        return null;
    }

    public Lane getLaneById(String id) {
        if (city == null || id == null) {
            return null;
        }
        for (Lane lane : city.getLanes()) {
            if (id.equals(lane.getId())) {
                return lane;
            }
        }
        return null;
    }

    public Point getPointById(String id) {
        if (city == null || id == null) {
            return null;
        }
        for (Point point : city.getPoints()) {
            if (id.equals(point.getId())) {
                return point;
            }
        }
        return null;
    }

    public Player getPlayerById(String name) {
        if (name == null) {
            return null;
        }
        for (Player player : players) {
            if (name.equals(player.getName())) {
                return player;
            }
        }
        return null;
    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }

    public Player getPlayerByName(String name) {
        if (name == null) {
            return null;
        }
        for (Player player : players) {
            if (name.equals(player.getName())) {
                return player;
            }
        }
        return null;
    }

    // A játék elindítása, amely inicializálja a várost és a játékosokat.
    public void startGame() {
        Logger.log("-> game.startGame()");
    }

    // A játék befejezése, amely lezárja a játékot és megjeleníti az eredményeket.
    public void endGame() {
        Logger.log("-> game.endGame()");
    }

    // A játék mentése, amely elmenti a jelenlegi állapotot egy fájlba.
    public void saveGame(String filename) {
        Logger.log("-> game.saveGame(" + filename + ")");
    }

    // A játék betöltése, amely visszaállítja a játék állapotát egy fájlból.
    public void loadGame(String filename) {
        Logger.log("-> game.loadGame(" + filename + ")");
    }

    // A játék egy lépésének szimulálása, amely frissíti a járművek helyzetét és kezeli az ütközéseket.
    public void simulateStep() {
        round++; // A körök számának növelése

         // Járművek mozgatása
         for (Vehicle vehicle : city.getVehicles()) {
            // Elakadt járművek kezelése
            vehicle.decreaseJammedTime();

            // mozgatás
            Lane nextLane = vehicle.getNextLane();
            if (nextLane == null) { continue; }
            Point nextPoint = nextLane.getEndPoint();
            if (nextPoint != null) {
                vehicle.move(nextPoint);
            }

             // csúszás, ha jeges volt az előző út, nincs rajta zúzottkő és tud csúszni a jármű 
            if (vehicle.canSlip && vehicle.getLastLane().getSnow().isIce() && vehicle.getLastLane().getSnow().getCrushedStoneLevel() == 0) {
                Point newPoint = null;
                for (Lane lane : vehicle.getCurrentPoint().getOutgoingLanes()) {
                    // ez azért kell, hogy hátrafele ne csússzon, kereszteződésnél jobbra balra is csúszhat
                    if (lane.getEndPoint().isReachable(vehicle) && !vehicle.getLastLane().getStartPoint().equals(lane.getEndPoint())) {
                        newPoint = lane.getEndPoint();
                        break;
                    }
                }
                if (newPoint != null) {
                    vehicle.move(newPoint);
                }
            }
        }

         // Karambolok keresése
         if(city.getVehicles().size() > 1) {
            for (Point point : city.getPoints()) {
                point.lookForJams();
            }
        }

         // Havazás
        for (Lane lane : city.getLanes()) {
            lane.raiseSnow();
        }
        notifyObservers(); // A nézet frissítése a játék új állapotára
    }

    /**
     * A játékos mozgási szándékának feldolgozása és előkészítése.
     * @param currentPlayer Az éppen soron lévő játékos
     * @param targetPoint A térképen kattintással kijelölt célpont
     * @return true, ha sikeres volt az útvonaltervezés, false ha hiba történt.
     */
    public boolean move(Object currentPlayer, model.Point targetPoint) {
        if (currentPlayer == null || targetPoint == null) return false;

        model.Vehicle vehicle = null;

        // 1. Jármű kikeresése a játékos típusa alapján
        String playerType = currentPlayer.getClass().getSimpleName();
        if (playerType.equals("SnowCleaner")) {
            model.SnowCleaner cleaner = (model.SnowCleaner) currentPlayer;
            // Ha esetleg neki is csak egy van, akkor ide is `cleaner.getSnowPlower()` jönne.
            // Ha neki listája van, akkor marad ez:
            if (cleaner.getSnowPlowers() != null && !cleaner.getSnowPlowers().isEmpty()) {
                vehicle = cleaner.getSnowPlowers().get(0);
            }
        } else if (playerType.equals("BusDriver")) {
            model.BusDriver driver = (model.BusDriver) currentPlayer;
            // Mivel pontosan 1 busza van, közvetlenül elkérjük az objektumot:
            vehicle = driver.getBus(); 
        }

        if (vehicle == null || vehicle.getCurrentPoint() == null) return false;

        // 2. Megkeressük a sávot a jármű alól a célpontba
        model.Lane nextLane = null;
        for (model.Lane lane : city.getLanes()) {
            if (lane.getStartPoint().equals(vehicle.getCurrentPoint()) && lane.getEndPoint().equals(targetPoint)) {
                nextLane = lane;
                break;
            }
        }

        // 3. Ha megvan a sáv, rögzítjük a járműnek
        if (nextLane != null) {
            vehicle.setNextLane(nextLane);
            return true;
        }

        return false; // Nincs közvetlen sáv a két pont között
    }
}