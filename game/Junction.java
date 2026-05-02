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
}
