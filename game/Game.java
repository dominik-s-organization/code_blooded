package game;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

/**
 * A Game osztály a játék fő osztálya, amely kezeli a játékmenetet.
 */
public class Game {
    private CityMap city; // A város térképe, amely tartalmazza a pontokat, útvonalakat és járműveket.
    private List<Player> players; // A játékosok listája.

    public Game() {
        city = null;
        players = new ArrayList<>();
    }

    public void setCity(CityMap city) {
        this.city = city;
    }

    public void addPlayer(Player player) {
        this.players.add(player);
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
    public void saveGame() {
        
        // TO DO

    }

    // A játék betöltése, amely visszaállítja a játék állapotát egy fájlból.
    public void loadGame() {
        
        // TO DO

    }

    // A játék egy lépésének szimulálása, amely frissíti a járművek helyzetét és kezeli az ütközéseket.
    public void simulateStep() {
         
         // Járművek mozgatása
         for (Vehicle vehicle : city.getVehicles()) {
            // Elakadt járművek kezelése
            vehicle.decreaseJammedTime();

            // mozgatás
            Point nextPoint = vehicle.getNextLane().getEndPoint();
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
        for (Point point : city.getPoints()) {
            point.lookForJams();
        }

         // Végül havazás
        for (Lane lane : city.getLanes()) {
            lane.raiseSnow();
        }
    } // teszt hogy mukodik e a pr
}
