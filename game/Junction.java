package game;

/**
 * A Junction osztály egy csomópontot reprezentál a játékban.
 */
public class Junction extends Point {
    
    public Junction() {
        super();
    }
    /*
     * Ellenőrzi, hogy a jármű ráléphet-e a csomópontra.
     * @param vehicle, a kérdéses jármű, amely megpróbál rálépni a csomópontra.
     * @return true, ha a jármű ráléphet a csomópontra, false egyébként.
     */
    public boolean isReachable(Vehicle vehicle) {
        System.out.println("-> junction.isReachable(vehicle)");
        System.out.println("<- true");
        return true;
    }
    
    // Adott pontonál van-e dugó.
    @Override
    public void lookForJams() {
        System.out.println("-> junction.lookForJams()");

        for (Vehicle vehicle1 : getVehicles()) {
            for (Vehicle vehicle2 : getVehicles()) {
                if (!vehicle1.equals(vehicle2) && vehicle1.getLastLane().equals(vehicle2.getLastLane())) {
                    vehicle1.jam();
                    vehicle2.jam();
                    vehicle1.getLastLane().setJammed(true);
                }
            }
        }
    }
}
