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
        if (vehicle.getLastLane() == null) {
            return vehicle.getNextLane().getStartPoint().equals(vehicle.getCurrentPoint()) && !vehicle.getNextLane().isJammed() && (vehicle.getNextLane().getSnow().getLevel() < 15 || !vehicle.canSlip);
        }
        return vehicle.getNextLane().getStartPoint().equals(vehicle.getCurrentPoint()) && !vehicle.getNextLane().isJammed() && (vehicle.getNextLane().getSnow().getLevel() < 15 || !vehicle.canSlip) && !vehicle.getLastLane().getStartPoint().equals(point);
    }

    @Override
    public void stat() {
        Logger.log("Junction ID: " + getId());
        Logger.log("Type: Junction");

        Logger.log("Incoming Lanes: " + getIncomingLanes().size());
        if(getIncomingLanes().size() > 0) {
            Logger.log("Incoming Lanes:");
            for (Lane lane : getIncomingLanes()) {
                Logger.log("  - " + lane.getId());
            }
        } else {
            Logger.log("No incoming lanes.");
        }

        Logger.log("Outgoing Lanes: " + getOutgoingLanes().size());
        if(getOutgoingLanes().size() > 0) {
            Logger.log("Outgoing Lanes:");
            for (Lane lane : getOutgoingLanes()) {
                Logger.log("  - " + lane.getId());
            }
        } else {
            Logger.log("No outgoing lanes.");
        }
        
        Logger.log("Vehicles: " + getVehicles().size());
        if(getVehicles().size() > 0) {
            Logger.log("Vehicles:");
            for (Vehicle vehicle : getVehicles()) {
                Logger.log("  - " + vehicle.getId());
            }
        } else {
            Logger.log("No vehicles currently at this junction.");
        }
    }
}
