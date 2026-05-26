package model;

/**
 * Speciális csomópontot (alagutat) reprezentáló osztály az úthálózatban.
 * Mivel fedett, a környezeti hatások (havazás) jellemzően nem érintik, 
 * de biztosítja az áthaladást a járművek számára.
 */
public class Tunnel extends Point {
    
    public Tunnel() {
         super();
    }
    /**
     * Konstruktor, amely egyedi azonosítóval hoz létre egy alagutat.
     * @param id Az alagút egyedi szöveges azonosítója.
     */
    public Tunnel(String id) {
        super(id);
    }
    /**
     * Részletes statisztikát ír ki az alagút aktuális állapotáról a konzolra,
     * listázva a bejövő és kimenő sávokat, valamint a benne tartózkodó járműveket.
     */
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
        Logger.log("");
    }
}
