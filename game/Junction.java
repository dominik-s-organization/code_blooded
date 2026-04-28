package game;

/**
 * A Junction osztály egy csomópontot reprezentál a játékban.
 */
public class Junction extends Point {
    //Konstruktor
    public Junction() {
        super();
    }

    public Junction(String id) {
        super(id);
    }

    protected boolean isReachableHelp(Point point, Vehicle vehicle) {
        return vehicle.getNextLane().getStartPoint().equals(vehicle.getCurrentPoint()) && !vehicle.getNextLane().isJammed() && (vehicle.getNextLane().getSnow().getLevel() < 15 || vehicle.canSlip) && !vehicle.getLastLane().getStartPoint().equals(point);
    }

    @Override
    public void stat() {
        System.out.println("Junction ID: " + getId());
        System.out.println("Type: Junction");
        System.out.println("Vehicles: " + getVehicles().size());
        System.out.println("Incoming Lanes: " + getIncomingLanes().size());
        System.out.println("Outgoing Lanes: " + getOutgoingLanes().size());
    }
}
