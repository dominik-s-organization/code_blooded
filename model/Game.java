package model;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import controller.GameObserver;

/**
 * A Game osztály a játék fő üzleti logikáját összefogó osztály, 
 * amely kezeli a játékmenetet, a köröket, az entitásokat és a térképet.
 */
public class Game implements IdGenerator {
    
    /** A város térképe, amely tartalmazza a pontokat, útvonalakat és járműveket. */
    private CityMap city; 
    
    /** A játékosok listája. */
    private List<Player> players; 
    
    /** A játékban elérhető tárgyak boltja. */
    private Store store; 
    
    /** Az egyedi azonosító számlálók tárolása. */
    private Map<String, Integer> idCounters; 
    
    /** A játék megfigyelői (GUI), akik értesülnek a játék állapotváltozásairól. */
    private List<GameObserver> observers; 
    
    /** A grafikus felületen éppen kiválasztott (kattintott) pont. */
    private Point selectedPoint; 
    
    /** Az aktuális játékos, aki éppen soron van. */
    private Player currentPlayer; 
    
    /** A játék aktuális köre. */
    private int round; 
    
    /** A játékban található összes sáv (útvonal) listája. */
    public List<Lane> lanes; 
    
    /** A játékban található összes csomópont listája. */
    public List<Point> points; 
    
    /** A játékban található összes jármű listája. */
    public List<Vehicle> vehicles; 

    /**
     * A Game osztály konstruktora, inicializálja az üres listákat és számlálókat.
     */
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

    /**
     * Létrehoz egy egyszerű, statikus teszttérképet (A MapGenerator mellett ritkábban használt).
     */
    public void initTestMap() {
        // ... Ezt a részt a korábbi kódod alapján bent hagyhatod ...
    }

     /**
      * Hozzáad egy megfigyelőt (pl. a GamePanel-t) a játékhoz.
      * @param observer Az értesítendő objektum.
      */
     public void addObserver(GameObserver observer) {
        observers.add(observer);
    }

    /**
     * Értesíti az összes regisztrált megfigyelőt, hogy a Modell állapota megváltozott.
     */
    public void notifyObservers() {
        for (GameObserver observer : observers) {
            observer.update();
        }
    }

    /** @return Az éppen soron lévő játékos. */
    public Player getCurrentPlayer() { return currentPlayer; }

    /** @return A jelenlegi körszám. */
    public int getCurrentRound() { return round; }

    /** @return A város térképét tároló objektum. */
    public CityMap getCityMap() { return city; }

    /** @param city Az új várostérkép. */
    public void setCity(CityMap city) { this.city = city; }

    /** @return A játékosok listája. */
    public List<Player> getPlayers() { return players; }

    /** @return A járművek listája. */
    public List<Vehicle> getVehicles() { return vehicles; }

    /** @return A játék boltja. */
    public Store getStore() { return store; }

    /** @param store Az új bolt objektum. */
    public void setStore(Store store) { this.store = store; }

    /** @param point A felhasználó által kijelölt célpont. */
    public void setSelectedPoint(Point point) { this.selectedPoint = point; }

    /** @return A felhasználó által kijelölt célpont. */
    public Point getSelectedPoint() { return this.selectedPoint; }

    @Override
    public String generateId(String prefix) {
        int next = idCounters.getOrDefault(prefix, 0) + 1;
        idCounters.put(prefix, next);
        return prefix + "_" + next;
    }

    @Override
    public void reset() {
        idCounters.clear();
        this.players.clear();
        this.city = new CityMap();
        this.initTestMap();
    }

    /**
     * ID alapján megkeres és visszaad egy járművet a térképről.
     * @param id A jármű azonosítója.
     * @return A jármű, vagy null ha nem található.
     */
    public Vehicle getVehicleById(String id) {
        if (city == null || id == null) return null;
        for (Vehicle vehicle : city.getVehicles()) {
            if (id.equals(vehicle.getId())) return vehicle;
        }
        return null;
    }

    /**
     * ID alapján megkeres és visszaad egy sávot a térképről.
     * @param id A sáv azonosítója.
     * @return A sáv, vagy null ha nem található.
     */
    public Lane getLaneById(String id) {
        if (city == null || id == null) return null;
        for (Lane lane : city.getLanes()) {
            if (id.equals(lane.getId())) return lane;
        }
        return null;
    }

    /**
     * ID alapján megkeres és visszaad egy csomópontot a térképről.
     * @param id A csomópont azonosítója.
     * @return A csomópont, vagy null.
     */
    public Point getPointById(String id) {
        if (city == null || id == null) return null;
        for (Point point : city.getPoints()) {
            if (id.equals(point.getId())) return point;
        }
        return null;
    }

    /**
     * Hozzáad egy új játékost a szimulációhoz.
     * @param player Az új játékos.
     */
    public void addPlayer(Player player) {
        this.players.add(player);
    }

    /**
     * Név alapján megkeres egy játékost.
     * @param name A játékos neve.
     * @return A játékos objektum, vagy null.
     */
    public Player getPlayerByName(String name) {
        if (name == null) return null;
        for (Player player : players) {
            if (name.equals(player.getName())) return player;
        }
        return null;
    }

    /** A játék elindítása. */
    public void startGame() { Logger.log("-> game.startGame()"); }

    /** A játék leállítása. */
    public void endGame() { Logger.log("-> game.endGame()"); }

    /** A játék mentése fájlba. @param filename Fájlnév. */
    public void saveGame(String filename) { Logger.log("-> game.saveGame(" + filename + ")"); }

    /** A játék betöltése fájlból. @param filename Fájlnév. */
    public void loadGame(String filename) { Logger.log("-> game.loadGame(" + filename + ")"); }

    // A játék egy lépésének szimulálása, amely frissíti a járművek helyzetét és kezeli az ütközéseket.
    public void simulateStep() {
        round++; // A körök számának növelése

        // Havazás
        for (Lane lane : city.getLanes()) {
            lane.raiseSnow();
        }
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

        notifyObservers(); // A nézet frissítése a játék új állapotára
    }

    /**
     * A játékos mozgási szándékának feldolgozása és előkészítése a Controller felől.
     * @param currentPlayer Az éppen soron lévő játékos.
     * @param targetPoint A térképen kijelölt célpont.
     * @return true, ha sikeres volt az útvonal rögzítése, false ha nincs közvetlen összeköttetés.
     */
    public boolean move(Object currentPlayer, model.Point targetPoint) {
        if (currentPlayer == null || targetPoint == null) return false;

        model.Vehicle vehicle = null;

        // 1. Jármű kikeresése a játékos típusa alapján
        String playerType = currentPlayer.getClass().getSimpleName();
        if (playerType.equals("SnowCleaner")) {
            model.SnowCleaner cleaner = (model.SnowCleaner) currentPlayer;
            if (cleaner.getSnowPlowers() != null && !cleaner.getSnowPlowers().isEmpty()) {
                vehicle = cleaner.getSnowPlowers().get(0);
            }
        } else if (playerType.equals("BusDriver")) {
            model.BusDriver driver = (model.BusDriver) currentPlayer;
            vehicle = driver.getBus(); 
        }

        if (vehicle == null || vehicle.getCurrentPoint() == null) return false;

        // 2. Megkeressük a megfelelő sávot, KIZÁRÓLAG a jelenlegi pontból kiindulók között (JAVÍTÁS!)
        model.Lane nextLane = null;
        for (model.Lane lane : vehicle.getCurrentPoint().getOutgoingLanes()) {
            if (lane.getEndPoint().equals(targetPoint)) {
                nextLane = lane;
                break; // Megtaláltuk a megfelelő irányú utat!
            }
        }

        // 3. Ha megvan a sáv, rögzítjük a járműnek a következő STEP-hez
        if (nextLane != null) {
            vehicle.setNextLane(nextLane);
            return true;
        }

        return false; // Nincs közvetlen sáv a két pont között a megfelelő irányba
    }
    
    /**
     * Kompatibilitási metódus a Console számára. Név alapján keresi a játékost.
     * @param id A játékos azonosítója (neve).
     * @return A játékos objektum, vagy null.
     */
    public Player getPlayerById(String id) {
        return getPlayerByName(id);
    }
}