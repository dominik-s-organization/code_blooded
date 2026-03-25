package game;

/**
 * A Junction osztály egy csomópontot reprezentál a játékban.
 */
public class Junction extends Point {
    /*
     * Ellenőrzi, hogy a jármű ráléphet-e a csomópontra.
     * @param vehicle
     * @return true, ha a jármű ráléphet a csomópontra, false egyébként.
     */
    public boolean isReachable(Vehicle vehicle) { return false;}
    // Adott pontonál van-e dugó.
    public void lookForJams() {}
}
