package game;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

/**
 * A Game osztály a játék fő osztálya, amely kezeli a játékmenetet.
 */
public class Game implements IdGenerator {
    private CityMap city; // A város térképe, amely tartalmazza a pontokat, útvonalakat és járműveket.
    private List<Player> players; // A játékosok listája.
    private Store store; // A játékban elérhető tárgyak boltja.
    private Map<String, Integer> idCounters; // Az egyedi azonosító számlálók tárolása.

    public Game() {
        city = new CityMap();
        players = new ArrayList<>();
        store = new Store();
        idCounters = new HashMap<>();
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
        System.out.println("-> game.startGame()");
    }

    // A játék befejezése, amely lezárja a játékot és megjeleníti az eredményeket.
    public void endGame() {
        System.out.println("-> game.endGame()");
    }

    // A játék mentése, amely elmenti a jelenlegi állapotot egy fájlba.
    public void saveGame(String filename) {
        System.out.println("-> game.saveGame(" + filename + ")");
    }

    // A játék betöltése, amely visszaállítja a játék állapotát egy fájlból.
    public void loadGame(String filename) {
        System.out.println("-> game.loadGame(" + filename + ")");
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

         // Végül havazás
        for (Lane lane : city.getLanes()) {
            lane.raiseSnow();
        }
    }
}
