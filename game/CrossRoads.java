package game;

/**
 * A CrossRoads osztály egy kereszteződést reprezentál a játékban.
 */
public class CrossRoads extends Point {

    //konstruktor
    public CrossRoads() {
        super();
    }
    
    public CrossRoads(String id) {
        super(id);
    }

    @Override
    public void stat() {
        Logger.log("CrossRoads ID: " + getId());
        Logger.log("Type: CrossRoads");
        
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
        Logger.log("");
    }



    @Override
    public void lookForJams() {}
}
