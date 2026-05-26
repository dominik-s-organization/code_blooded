package model;

/**
 * A CrossRoads osztály egy összetett kereszteződést reprezentál a játékban.
 * A normál csomópontokhoz (Junction) képest bonyolultabb forgalmi helyzeteket
 * is képes kezelni az úthálózatban.
 */
public class CrossRoads extends Point {
    /**
     * Alapértelmezett konstruktor egy névtelen kereszteződés létrehozásához.
     */
    public CrossRoads() {
        super();
    }
    /**
     * Konstruktor, amely egyedi azonosítóval hoz létre egy kereszteződést.
     * @param id A kereszteződés egyedi szöveges azonosítója (pl. "CR1").
     */
    public CrossRoads(String id) {
        super(id);
    }
    /**
     * Részletes statisztikát és állapotjelentést ír ki a kereszteződésről a konzolra,
     * listázva a bejövő és kimenő sávokat, valamint a rajta tartózkodó járműveket.
     */
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


    /**
     * Megvizsgálja, hogy kialakult-e közlekedési dugó (túl sok jármű) a kereszteződésben.
     */
    @Override
    public void lookForJams() {
        // A leszármazott osztály specifikus ütközés-keresési logikája
    }
}
