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
    }

    public void initTestMap() {
        CityMap map = this.getCityMap(); // feltételezve, hogy a 'city' vagy 'getCityMap()' elérhető

        // 1. Csomópontok (Junctions) létrehozása ID alapján (ahogy a modell kéri)
        Junction j1 = new Junction("J1");
        j1.setX(150); j1.setY(150);

        Junction j2 = new Junction("J2");
        j2.setX(450); j2.setY(150);

        Junction j3 = new Junction("J3");
        j3.setX(650); j3.setY(300);

        Junction j4 = new Junction("J4");
        j4.setX(150); j4.setY(450);

        Junction j5 = new Junction("J5");
        j5.setX(450); j5.setY(450);

        // Csomópontok hozzáadása a várostérképhez
        map.addPoint(j1);
        map.addPoint(j2);
        map.addPoint(j3);
        map.addPoint(j4);
        map.addPoint(j5);

        // 2. Sávok (Lanes) létrehozása és topológiai összekötése setterekkel
        
        // Lane 1: J1 -> J2
        Lane l1 = new Lane("lane_1");
        l1.setStartPoint(j1);
        l1.setEndPoint(j2);
        j1.addOutgoingLane(l1);
        j2.addIncomingLane(l1);
        map.addLane(l1);

        // Lane 2: J2 -> J3
        Lane l2 = new Lane("lane_2");
        l2.setStartPoint(j2);
        l2.setEndPoint(j3);
        j2.addOutgoingLane(l2);
        j3.addIncomingLane(l2);
        map.addLane(l2);

        // Lane 3: J1 -> J4
        Lane l3 = new Lane("lane_3");
        l3.setStartPoint(j1);
        l3.setEndPoint(j4);
        j1.addOutgoingLane(l3);
        j4.addIncomingLane(l3);
        map.addLane(l3);

        // Lane 4: J4 -> J5
        Lane l4 = new Lane("lane_4");
        l4.setStartPoint(j4);
        l4.setEndPoint(j5);
        j4.addOutgoingLane(l4);
        j5.addIncomingLane(l4);
        map.addLane(l4);

        // Lane 5: J2 -> J5
        Lane l5 = new Lane("lane_5");
        l5.setStartPoint(j2);
        l5.setEndPoint(j5);
        j2.addOutgoingLane(l5);
        j5.addIncomingLane(l5);
        map.addLane(l5);

        // 3. Kezdeti hómennyiségek beállítása a teszteléshez
        l1.getSnow().setSnowLevel(0);  // Tiszta út
        l2.getSnow().setSnowLevel(4);  // Enyhe hó
        l3.getSnow().setSnowLevel(8);  // Közepes hó
        l4.getSnow().setSnowLevel(12); // Nagy hó
        l5.getSnow().setSnowLevel(2);  // Minimális hó

        // 4. JÁRMŰVEK ÉS JÁTÉKOSOK ELHELYEZÉSE
        SnowCleaner cleaner = new SnowCleaner("Jatekos1"); 
        model.SnowPlower plower = cleaner.getSnowPlowers().get(0);

        // Beállítjuk a hókotró kezdőpozícióját és összekötjük a csomóponttal
        plower.setCurrentPoint(j1); 
        j1.addVehicle(plower);
        map.addVehicle(plower);
        
        this.addPlayer(cleaner); // Ha van ilyen metódus a Game-ben

        // 5. OBSERVER ÉRTESÍTÉS: Szólunk a GUI-nak, hogy rajzolja ki a felépített pályát
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
