package game;

/**
 * A Junction osztály egy csomópontot reprezentál a játékban.
 */
public class Junction extends Point {
    
    public Junction() {
    }
    /*
     * Ellenőrzi, hogy a jármű ráléphet-e a csomópontra.
     * @param vehicle, a kérdéses jármű, amely megpróbál rálépni a csomópontra.
     * @return true, ha a jármű ráléphet a csomópontra, false egyébként.
     */
    public boolean isReachable(Vehicle vehicle) {
        System.out.println("-> junction.isReachable(vehicle)");
        System.out.println("<- true");
        return true;
    }
    // Adott pontonál van-e dugó.
    @Override
    public void lookForJams() {
        System.out.println("-> junction.lookForJams()");
    }
}
