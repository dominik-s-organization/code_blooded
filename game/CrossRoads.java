package game;

/**
 * A CrossRoads osztály egy kereszteződést reprezentál a játékban.
 */
public class CrossRoads extends Point {
    /*
     * Adott jármű ráléphet-e a kereszteződést.
     * @param vehicle, a kérdéses jármű, amely megpróbál rálépni a kereszteződésre.
     * @return true, ha a jármű ráléphet a kereszteződésre, false egyébként.
     */
    public boolean isReachable(Vehicle vehicle) {
        return true;
    }

    public CrossRoads() {
        super();
    }

    @Override
    public void lookForJams() {}
}
