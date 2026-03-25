package game;

import java.util.List;

/**
 * A Game osztály a játék fő osztálya, amely kezeli a játékmenetet.
 */
public class Game {
    private CityMap city; // A város térképe, amely tartalmazza a pontokat, útvonalakat és járműveket.
    private List<Player> players; // A játékosok listája.

    // A játék elindítása, amely inicializálja a várost és a játékosokat.
    public void startGame() {}

    // A játék befejezése, amely lezárja a játékot és megjeleníti az eredményeket.
    public void endGame() {}

    // A játék mentése, amely elmenti a jelenlegi állapotot egy fájlba.
    public void saveGame() {}

    // A játék betöltése, amely visszaállítja a játék állapotát egy fájlból.
    public void loadGame() {}

    // A játék egy lépésének szimulálása, amely frissíti a járművek helyzetét és kezeli az ütközéseket.
    public void simulateStep() {}
}
