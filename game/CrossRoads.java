package game;

/**
 * A CrossRoads osztály egy kereszteződést reprezentál a játékban.
 */
public class CrossRoads extends Point {
    
    public CrossRoads() {
        super();
    }

    /*
     * Adott jármű ráléphet-e a kereszteződésre.
     * @param vehicle, a kérdéses jármű, amely megpróbál rálépni a kereszteződésre.
     * @return true, ha a jármű ráléphet a kereszteződésre, false egyébként.
     */
    public boolean isReachable(Vehicle vehicle) {
        System.out.println("-> crossRoads.isReachable(vehicle)");
        System.out.println("<- true");
        return true;
    }
    // Adott pontonál van-e dugó.
    @Override
    public void lookForJams() {
        System.out.println("-> crossRoads.lookForJams()");
    }
}
