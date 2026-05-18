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
    }

    public void initTestMap() {
        // 1. Csomópontok (Point) létrehozása konkrét X, Y koordinátákkal
        Point p1 = new Point(100, 100); // Bal felső
        Point p2 = new Point(500, 100); // Jobb felső
        Point p3 = new Point(500, 400); // Jobb alsó
        Point p4 = new Point(100, 400); // Bal alsó

        // Hozzáadás a térképhez
        city.addPoint(p1);
        city.addPoint(p2);
        city.addPoint(p3);
        city.addPoint(p4);

        // 2. Sávok/Utak (Lane) összekötése a pontok között
        // Feltételezve, hogy a Lane konstruktora: Lane(Point start, Point end)
        city.addLane(new Lane(p1, p2)); // Felső út
        city.addLane(new Lane(p2, p3)); // Jobb oldali út
        city.addLane(new Lane(p3, p4)); // Alsó út
        city.addLane(new Lane(p4, p1)); // Bal oldali út

        // 3. Kezdő járművek elhelyezése
        // Feltételezve, hogy a Vehicle kap egy kezdőpontot
        Vehicle car = new Car(); 
        Vehicle plow = new SnowPlower();

        city.addVehicle(car);
        city.addVehicle(plow);

        // Értesítjük a nézetet, hogy új pálya lett betöltve, rajzolja újra magát
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
}
