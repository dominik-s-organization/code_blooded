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
        return vehicle.getNextLane().getStartPoint().equals(vehicle.getCurrentPoint()) && !vehicle.getNextLane().isJammed() && (vehicle.getNextLane().getSnow().getLevel() < 15 || vehicle.canSlip) && vehicle.getNextLane().isUnderground() == vehicle.getLastLane().isUnderground();
    }

    @Override
    public void stat() {
        System.out.println("Tunnel ID: " + getId());
        System.out.println("Type: Tunnel");
        System.out.println("Vehicles: " + getVehicles().size());
        System.out.println("Incoming Lanes: " + getIncomingLanes().size());
        System.out.println("Outgoing Lanes: " + getOutgoingLanes().size());
    }
}
