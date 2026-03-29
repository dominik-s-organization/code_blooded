package game;

/**
 * A CrossRoads osztály egy kereszteződést reprezentál a játékban.
 */
public class CrossRoads extends Point {
    
    public CrossRoads() {
        super();
    }

    /*
<<<<<<< HEAD
     * Adott jármű ráléphet-e a kereszteződést.
=======
     * Adott jármű ráléphet-e a kereszteződésre.
>>>>>>> origin/main
     * @param vehicle, a kérdéses jármű, amely megpróbál rálépni a kereszteződésre.
     * @return true, ha a jármű ráléphet a kereszteződésre, false egyébként.
     */
    public boolean isReachable(Vehicle vehicle) {
<<<<<<< HEAD
        return true;
    }
=======
        System.out.println("-> crossRoads.isReachable(vehicle)");
        System.out.println("<- true");
        return true;
    }
    // Adott pontonál van-e dugó.
    @Override
    public void lookForJams() {
        System.out.println("-> crossRoads.lookForJams()");
    }
>>>>>>> origin/main
}
