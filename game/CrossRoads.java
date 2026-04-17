package game;

/**
 * A CrossRoads osztály egy kereszteződést reprezentál a játékban.
 */
public class CrossRoads extends Point {
<<<<<<< HEAD
    /*
     * Adott jármű ráléphet-e a kereszteződést.
     * @param vehicle, a kérdéses jármű, amely megpróbál rálépni a kereszteződésre.
     * @return true, ha a jármű ráléphet a kereszteződésre, false egyébként.
     */
    public boolean isReachable(Vehicle vehicle) {
        return true;
    }

    //konstruktor
    public CrossRoads() {
        super();
=======
    
    public CrossRoads() {
        super();
    }

    protected boolean isReachableHelp(Point point, Vehicle vehicle) {
        for (Lane incomingLane : point.getIncomingLanes()) {
            if (incomingLane.getStartPoint().equals(vehicle.getCurrentPoint()) && !incomingLane.isJammed() && (incomingLane.getSnow().getLevel() < 15 || vehicle instanceof SnowPlower)) {
                return true;
            }
        }
        return false;
>>>>>>> main
    }

    @Override
    public void lookForJams() {}
}
