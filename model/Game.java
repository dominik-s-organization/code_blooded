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
        // 1. CSOMÓPONTOK LÉTREHOZÁSA
        // ID-k kisbetűs típusnevekkel
        // ==========================================
        
        // J1: Kertváros (Itt laknak az autók)
        Junction j1 = new Junction("junction_home"); j1.setX(100); j1.setY(300);
        // C1: Fő kereszteződés (Középen)
        CrossRoads c1 = new CrossRoads("crossroads_center"); c1.setX(400); c1.setY(300);
        // J2: Belváros / Munkahely
        Junction j2 = new Junction("junction_work"); j2.setX(700); j2.setY(300);
        // T1: Alagút bejárata (Lefelé)
        Tunnel t1 = new Tunnel("tunnel_entrance"); t1.setX(400); t1.setY(500);

        map.addPoint(j1);
        map.addPoint(c1);
        map.addPoint(j2);
        map.addPoint(t1);

        // ==========================================
        // 2. KÉTIRÁNYÚ SÁVOK (LANES) LÉTREHOZÁSA
        // ==========================================

        // --- Kertváros (J1) <-> Központ (C1) ---
        Lane l_j1_c1 = new Lane("lane_home_to_center");
        l_j1_c1.setStartPoint(j1); l_j1_c1.setEndPoint(c1);
        j1.addOutgoingLane(l_j1_c1); c1.addIncomingLane(l_j1_c1);
        map.addLane(l_j1_c1);

        Lane l_c1_j1 = new Lane("lane_center_to_home");
        l_c1_j1.setStartPoint(c1); l_c1_j1.setEndPoint(j1);
        c1.addOutgoingLane(l_c1_j1); j1.addIncomingLane(l_c1_j1);
        map.addLane(l_c1_j1);

        // --- Központ (C1) <-> Belváros (J2) ---
        Lane l_c1_j2 = new Lane("lane_center_to_work");
        l_c1_j2.setStartPoint(c1); l_c1_j2.setEndPoint(j2);
        c1.addOutgoingLane(l_c1_j2); j2.addIncomingLane(l_c1_j2);
        map.addLane(l_c1_j2);

        Lane l_j2_c1 = new Lane("lane_work_to_center");
        l_j2_c1.setStartPoint(j2); l_j2_c1.setEndPoint(c1);
        j2.addOutgoingLane(l_j2_c1); c1.addIncomingLane(l_j2_c1);
        map.addLane(l_j2_c1);

        // --- Központ (C1) <-> Alagút (T1) ---
        Lane l_c1_t1 = new Lane("lane_center_to_tunnel");
        l_c1_t1.setStartPoint(c1); l_c1_t1.setEndPoint(t1);
        c1.addOutgoingLane(l_c1_t1); t1.addIncomingLane(l_c1_t1);
        map.addLane(l_c1_t1);

        Lane l_t1_c1 = new Lane("lane_tunnel_to_center");
        l_t1_c1.setStartPoint(t1); l_t1_c1.setEndPoint(c1);
        t1.addOutgoingLane(l_t1_c1); c1.addIncomingLane(l_t1_c1);
        map.addLane(l_t1_c1);

        // ==========================================
        // 3. IDŐJÁRÁS ÉS ÚTVISZONYOK BEÁLLÍTÁSA
        // ==========================================
        l_j1_c1.getSnow().setSnowLevel(0);  // Tiszta út kifelé
        l_c1_j2.getSnow().setSnowLevel(12); // Erősen havas út a munkahely felé
        l_c1_t1.getSnow().setSnowLevel(2);  // Kicsi hó az alagút felé

        // ==========================================
        // 4. JÁTÉKOSOK ÉS JÁRMŰVEIK (A Modell alapján)
        // ==========================================
        
        // --- 1. Játékos: Hókotró ---
        SnowCleaner cleaner = new SnowCleaner("Hokotro_Jani"); 
        if (!cleaner.getSnowPlowers().isEmpty()) {
            model.SnowPlower plower = cleaner.getSnowPlowers().get(0);
            plower.setId("snow_plower_1");
            plower.setCurrentPoint(c1); // A központból indul
            c1.addVehicle(plower);
            map.addVehicle(plower);
        }
        this.addPlayer(cleaner); // Hozzáadjuk a körmenedzserhez

        // --- 2. Játékos: Buszsofőr ---
        BusDriver busDriver = new BusDriver("Buszos_Bela");
        if (busDriver.getBus() != null) { // Vagy getBuses().get(0)
            Bus bus = busDriver.getBus();
            bus.setId("bus_1");
            bus.setCurrentPoint(j1); // A kertvárosból indul
            j1.addVehicle(bus);
            map.addVehicle(bus);
        }
        this.addPlayer(busDriver);

        // ==========================================
        // 5. AUTÓ
        // ==========================================
        
        // Az autók nem játékosok, csak járművek a térképen
        Car car = new Car(); // Vagy ahogy a konstruktorotok kéri
        car.setId("car_1");
        car.setHome(j1); 
        car.setWork(j2);
        car.setCurrentPoint(j1); // Otthonról indul
        j1.addVehicle(car);
        map.addVehicle(car);

        // ==========================================
        // 6. ÉRTESÍTÉS A GUI-NAK
        // ==========================================
        this.notifyObservers();
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