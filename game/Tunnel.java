package game;

/**
<<<<<<< HEAD
 * Speciális csomópontot (alagutat) reprezentáló osztály az úthálzatban.
=======
 * Speciális csomópontot (alagutat) reprezentáló osztály az úthálózatban.
>>>>>>> main
 * Mivel fedett, a környezeti hatások (havazás) jellemzően nem érintik, 
 * de biztosítja az áthaladást a járművek számára.
 */
public class Tunnel extends Point {
    
    public Tunnel() {
         super();
    }
<<<<<<< HEAD
    /**
     * Meghatározza, hogy az adott jármű ráléphet-e (behajthat-e) az alagútba.
     *
     * @param vehicle a vizsgált jármű, amely be szeretne hajtani az alagútba
     * @return true, ha a jármű ráléphet az alagútra, ellenkező esetben false
     */    
     public boolean isReachable(Vehicle vehicle) {
        System.out.println("-> tunnel.isReachable(vehicle)");
        System.out.println("<- true");
        return true;
     }
=======
>>>>>>> main

    protected boolean isReachableHelp(Point point, Vehicle vehicle) {
        for (Lane incomingLane : point.getIncomingLanes()) {
            if (incomingLane.getStartPoint().equals(vehicle.getCurrentPoint()) && !incomingLane.isJammed() && (incomingLane.getSnow().getLevel() < 15 || vehicle instanceof SnowPlower) && incomingLane.isUnderground() == vehicle.getLastLane().isUnderground()) {
                return true;
            }
        }
        return false;
    }
}
