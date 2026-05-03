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

    //konstruktor
    public CrossRoads() {
        super();
    }
    
    public CrossRoads(String id) {
        super(id);
    }

    protected boolean isReachableHelp(Point point, Vehicle vehicle) {
        return vehicle.getNextLane().getStartPoint().equals(vehicle.getCurrentPoint()) && !vehicle.getNextLane().isJammed() && (vehicle.getNextLane().getSnow().getLevel() < 15 || !vehicle.canSlip);
    }

    @Override
    public void stat() {
        System.out.println("CrossRoads ID: " + getId());
        System.out.println("Type: CrossRoads");
        
        System.out.println("Incoming Lanes: " + getIncomingLanes().size());
        if(getIncomingLanes().size() > 0) {
            System.out.println("Incoming Lanes:");
            for (Lane lane : getIncomingLanes()) {
                System.out.println("  - " + lane.getId());
            }
        } else {
            System.out.println("No incoming lanes.");
        }

        System.out.println("Outgoing Lanes: " + getOutgoingLanes().size());
        if(getOutgoingLanes().size() > 0) {
            System.out.println("Outgoing Lanes:");
            for (Lane lane : getOutgoingLanes()) {
                System.out.println("  - " + lane.getId());
            }
        } else {
            System.out.println("No outgoing lanes.");
        }
        
        System.out.println("Vehicles: " + getVehicles().size());
        if(getVehicles().size() > 0) {
            System.out.println("Vehicles:");
            for (Vehicle vehicle : getVehicles()) {
                System.out.println("  - " + vehicle.getId());
            }
        } else {
            System.out.println("No vehicles currently at this junction.");
        }
    }



    @Override
    public void lookForJams() {}
}
