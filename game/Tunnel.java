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

    protected boolean isReachableHelp(Point point, Vehicle vehicle) {
        for (Lane incomingLane : point.getIncomingLanes()) {
            if (incomingLane.getStartPoint().equals(vehicle.getCurrentPoint()) && !incomingLane.isJammed() && (incomingLane.getSnow().getLevel() < 15 || vehicle instanceof SnowPlower) && incomingLane.isUnderground() == vehicle.getLastLane().isUnderground()) {
                return true;
            }
        }
        return false;
    }
}
