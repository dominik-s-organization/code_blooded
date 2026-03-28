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
        System.out.println("-> game.setCity(cm)");
        this.city = city;
    }

    public void addPlayer(Player player) {
        System.out.println("-> game.addPlayer(player)");
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
        System.out.println("-> game.saveGame()");
    }

    // A játék betöltése, amely visszaállítja a játék állapotát egy fájlból.
    public void loadGame() {
        System.out.println("-> game.loadGame()");
    }

    // A játék egy lépésének szimulálása, amely frissíti a járművek helyzetét és kezeli az ütközéseket.
    public void simulateStep() {
         System.out.println("-> game.simulateStep()");
    }
}
