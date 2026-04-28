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

    public CrossRoads(String id) {
        super(id);
    }

    protected boolean isReachableHelp(Point point, Vehicle vehicle) {
        return vehicle.getNextLane().getStartPoint().equals(vehicle.getCurrentPoint()) && !vehicle.getNextLane().isJammed() && (vehicle.getNextLane().getSnow().getLevel() < 15 || vehicle.canSlip);
    }

    @Override
    public void stat() {
        System.out.println("CrossRoads ID: " + getId());
        System.out.println("Type: CrossRoads");
        System.out.println("Vehicles: " + getVehicles().size());
        System.out.println("Incoming Lanes: " + getIncomingLanes().size());
        System.out.println("Outgoing Lanes: " + getOutgoingLanes().size());
    }

    @Override
    public void lookForJams() {}
}
