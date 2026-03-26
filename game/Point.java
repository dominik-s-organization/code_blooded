package game;

import java.util.List;

/**
 * Absztrakt �soszt�ly az �th�l�zat topol�giai pontjainak (pl. csom�pontok, keresztez�d�sek) reprezent�l�s�ra.
 * Felel�ss�ge a becsatlakoz� �s kimen� s�vok, valamint az adott ponton tart�zkod� j�rm�vek nyilv�ntart�sa.
 */
public abstract class Point {
    /**
     * A ponton (keresztez�d�sben) �ppen tart�zkod� j�rm�vek list�ja.
     */
    private List<Vehicle> vehicles;
    /**
     * A pontba be�rkez� s�vok list�ja.
     */
    private List<Lane> incomingLanes;
    /**
     * A pontb�l kiindul� s�vok list�ja.
     */
    private List<Lane> outgoingLanes;

    public Point() {
        System.out.println("-> point.Point()");
        System.out.println("<- void");
    }
    /**
     * Hozzáad egy járművet a ponthoz (amikor a jármű rálép).
     *
     * @param vehicle a hozzáadandó jármű
     */
    public void addVehicle(Vehicle vehicle) {
        System.out.println("-> point.addVehicle(vehicle)");
        vehicles.add(vehicle);
        System.out.println("<- void");
    }

    /**
     * Eltávolít egy járművet a pontról (amikor a jármű továbbhalad).
     *
     * @param vehicle az eltávolítandó jármű
     */
    public void removeVehicle(Vehicle vehicle) {
        System.out.println("-> point.removeVehicle(vehicle)");
        vehicles.remove(vehicle);
        System.out.println("<- void");
    }

    /**
     * Beállít egy beérkező sávot a ponthoz.
     *
     * @param lane a beérkező sáv
     */
    public void addIncomingLane(Lane lane) {
        System.out.println("-> point.addIncomingLane(lane)");
        incomingLanes.add(lane);
        System.out.println("<- void");
    }

    /**
     * Beállít egy kimenő sávot a ponthoz.
     *
     * @param lane a kimenő sáv
     */
    public void addOutgoingLane(Lane lane) {
        System.out.println("-> point.addOutgoingLane(lane)");
        outgoingLanes.add(lane);
        System.out.println("<- void");
    }
    /**
     * Meghat�rozza, hogy az adott j�rm� r�l�phet-e (behajthat-e) erre a pontra.
     * A lesz�rmazott oszt�lyok (pl. Tunnel, Junction) egyedi logik�t val�s�thatnak meg.
     *
     * @param vehicle a vizsg�lt j�rm�, amely r� szeretne l�pni a pontra
     * @return true, ha a j�rm� r�l�phet a pontra, ellenkez� esetben false
     */
    public abstract boolean isReachable(Vehicle vehicle);
<<<<<<< Updated upstream
=======

    /**
     * Ellenőrzi a csomóponton lévő dugókat/baleseteket.
     */
    public abstract void lookForJams();
>>>>>>> Stashed changes
}
