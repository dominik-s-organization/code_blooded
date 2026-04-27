package game;

/**
 * A Junction osztály egy csomópontot reprezentál a játékban.
 */
public class Junction extends Point {
    
    public Junction() {
        super();
    }

    public Junction(String id) {
        super(id);
    }

    protected boolean isReachableHelp(Point point, Vehicle vehicle) {
        for (Lane incomingLane : point.getIncomingLanes()) {
            if (incomingLane.getStartPoint().equals(vehicle.getCurrentPoint()) && !incomingLane.isJammed() && (incomingLane.getSnow().getLevel() < 15 || vehicle instanceof SnowPlower) && !vehicle.getLastLane().getStartPoint().equals(point)) {
                return true;
            }
        }
        return false;
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
