package game;

/**
 * Speciális csomópontot (alagutat) reprezentáló osztály az úthálózatban.
 * Mivel fedett, a környezeti hatások (havazás) jellemzően nem érintik, 
 * de biztosítja az áthaladást a járművek számára.
 */
public class Tunnel extends Point {
    
    public Tunnel() {
         super();
    }

    public Tunnel(String id) {
        super(id);
    }

    protected boolean isReachableHelp(Point point, Vehicle vehicle) {
        if (vehicle.getLastLane() == null) {
            return vehicle.getNextLane().getStartPoint().equals(vehicle.getCurrentPoint()) && !vehicle.getNextLane().isJammed() && (vehicle.getNextLane().getSnow().getLevel() < 15 || !vehicle.canSlip);
        }
        return vehicle.getNextLane().getStartPoint().equals(vehicle.getCurrentPoint()) && !vehicle.getNextLane().isJammed() && (vehicle.getNextLane().getSnow().getLevel() < 15 || !vehicle.canSlip) && vehicle.getNextLane().isUnderground() == vehicle.getLastLane().isUnderground();
    }

    @Override
    public void stat() {
        Logger.log("Tunnel ID: " + getId());
        Logger.log("Type: Tunnel");
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
