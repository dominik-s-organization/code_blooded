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

    private int activePlayerIndex = 0; // Az aktuális játékos indexe a players listában
    
    /** A játék aktuális köre. */
    private int currentRound; 
    
    /** A játékban található összes sáv (útvonal) listája. */
    public List<Lane> lanes; 
    
    /** A játékban található összes csomópont listája. */
    public List<Point> points; 
    
    /** A játékban található összes jármű listája. */
    public List<Vehicle> vehicles; 

    // Új változó a többi változó (pl. activePlayerIndex) alá
    private int activeVehicleIndex = 0;

    public int getActiveVehicleIndex() {
        return activeVehicleIndex;
    }

    /**
     * Visszaadja az éppen soron lévő játékos aktuálisan lépő járművét.
     */
    public Vehicle getActiveVehicle() {
        Player currentPlayer = getCurrentPlayer();
        if (currentPlayer == null) return null;
        
        if (currentPlayer.getType().equals("snow_cleaner")) {
            SnowCleaner cleaner = (SnowCleaner) currentPlayer;
            if (cleaner.getSnowPlowers() != null && !cleaner.getSnowPlowers().isEmpty()) {
                // Biztonsági ellenőrzés, ha pl. eladna egyet a jövőben
                if (activeVehicleIndex >= cleaner.getSnowPlowers().size()) {
                    activeVehicleIndex = 0;
                }
                return cleaner.getSnowPlowers().get(activeVehicleIndex);
            }
        } else if (currentPlayer.getType().equals("bus_driver")) {
            return ((BusDriver) currentPlayer).getBus();
        }
        return null;
    }

    /**
     * A Game osztály konstruktora, inicializálja az üres listákat és számlálókat.
     */
    public Game() {
        city = new CityMap();
        players = new ArrayList<>();
        store = new Store();
        store.setCityMap(city);
        idCounters = new HashMap<>();
        observers = new ArrayList<>();
        lanes = new ArrayList<>();
        points = new ArrayList<>();
        vehicles = new ArrayList<>();
        selectedPoint = null;
        currentRound = 0;
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
    public Player getCurrentPlayer() {
        // Ha a lista üres, vagy null, logikusan nincs aktív játékos
        if (this.players == null || this.players.isEmpty()) {
            return null; 
        }
        
        // Biztonsági védelem: ha az index valamiért túlcsordulna, visszatekerjük 0-ra
        if (this.activePlayerIndex >= this.players.size()) {
            this.activePlayerIndex = 0;
        }
        
        // Visszaadjuk a soron lévő játékost
        return this.players.get(this.activePlayerIndex);
    }

     /**
      * Előlépteti a játékot a következő játékosra, és ha mindenki lépett, új kört indít.
      */
    public boolean nextPlayerTurn() {
        if (players == null || players.isEmpty()) return false;
        
        Player currentPlayer = getCurrentPlayer();
        
        // 1. Megnézzük, van-e még járműve az aktuális játékosnak, amivel nem lépett
        if (currentPlayer.getType().equals("snow_cleaner")) {
            SnowCleaner cleaner = (SnowCleaner) currentPlayer;
            if (cleaner.getSnowPlowers() != null && activeVehicleIndex + 1 < cleaner.getSnowPlowers().size()) {
                activeVehicleIndex++; // Ugrás a következő hókotróra
                notifyObservers();
                return false; // A játékos köre még nem ért véget
            }
        }
        
        // 2. Ha nincs több jármű, jöhet a következő játékos
        activeVehicleIndex = 0; // Visszaállítjuk a jármű indexet
        activePlayerIndex++; 
        
        // 3. Ha mindenki lépett, új kör indul
        if (activePlayerIndex >= players.size()) {
            activePlayerIndex = 0;
            notifyObservers();
            return true; 
        }
        
        notifyObservers();
        return false;
    }


    /** @return A jelenlegi körszám. */
    public int getCurrentRound() { return currentRound; }

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
        if(this.players == null){
            this.players = new ArrayList<>();
        }
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

    /** A játék mentése bináris fájlba. */
    public void saveGame(String filename) {
        try (java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(new java.io.FileOutputStream(filename))) {
            // Lementjük a játék főbb állapotait
            oos.writeObject(this.city);
            oos.writeObject(this.players);
            oos.writeObject(this.store);
            oos.writeObject(this.idCounters);
            Logger.log("> Game successfully saved to " + filename);
        } catch (Exception e) {
            Logger.log("> ERROR: Could not save the game. " + e.getMessage());
            e.printStackTrace();
        }
    }

    /** A játék betöltése bináris fájlból. */
    @SuppressWarnings("unchecked")
    public void loadGame(String filename) {
        java.io.File file = new java.io.File(filename);
        if (!file.exists()) {
            Logger.log("> ERROR: File not found: " + filename);
            return;
        }

        try (java.io.ObjectInputStream ois = new java.io.ObjectInputStream(new java.io.FileInputStream(filename))) {
            // Visszaolvassuk és felülírjuk a jelenlegi állapotot
            this.city = (CityMap) ois.readObject();
            this.players = (List<model.Player>) ois.readObject();
            this.store = (Store) ois.readObject();
            this.idCounters = (Map<String, Integer>) ois.readObject();
            // Újrarajzolás
            this.notifyObservers();
            
            Logger.log("> Game successfully loaded from " + filename);
        } catch (Exception e) {
            Logger.log("> ERROR: Could not load the game. " + e.getMessage());
            e.printStackTrace();
        }
    }

    // A játék egy lépésének szimulálása, amely frissíti a járművek helyzetét és kezeli az ütközéseket.
    public void simulateStep() {
        currentRound++; // A körök számának növelése

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

        // A régi if-else típusvizsgálat és a get(0) helyett egyszerűen lekérjük az aktív járművet:
        model.Vehicle vehicle = getActiveVehicle(); 

        if (vehicle == null || vehicle.getCurrentPoint() == null) return false;

        // Megkeressük a megfelelő sávot
        model.Lane nextLane = null;
        for (model.Lane lane : vehicle.getCurrentPoint().getOutgoingLanes()) {
            if (lane.getEndPoint().equals(targetPoint)) {
                nextLane = lane;
                break; 
            }
        }

        if (nextLane != null) {
            vehicle.setNextLane(nextLane);
            return true;
        }

        return false; 
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