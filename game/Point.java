package game;

import java.util.ArrayList;
import java.util.List;

/**
 * Absztrakt ősosztaly az úthálzat topolgiai pontjainak (pl. csompontok, keresztezdsek) reprezentálásra.
 * Felelőssége a becsatlakozás kimenő sávok, valamint az adott ponton tartózkodó járművek nyilvántartsa.
 */
public abstract class Point {
    /**
     * A ponton (kereszteződésben) éppen tartózkodó járművek listája.
     */
    private List<Vehicle> vehicles;
    /**
     * A pontba beérkező sávok listája.
     */
    private List<Lane> incomingLanes;
    /**
     * A pontból kiinduló sávok listája.
     */
    private List<Lane> outgoingLanes;

    //Konstruktor
    public Point() {
        vehicles = new ArrayList<>();
        incomingLanes = new ArrayList<>();
        outgoingLanes = new ArrayList<>();
    }

    // Getterek
    public List<Lane> getIncomingLanes() {
        System.out.println("-> point.getIncomingLanes()");
        System.out.println("<- incomingLanes");
        return incomingLanes;
    }

    public List<Lane> getOutgoingLanes() {
        System.out.println("-> point.getOutgoingLanes()");
        System.out.println("<- outgoingLanes");
        return outgoingLanes;
    }

     public List<Vehicle> getVehicles() {
        System.out.println("-> point.getVehicles()");
        System.out.println("<- vehicles");
        return vehicles;
     }

    /**
     * Hozzáad egy járművet a ponthoz (amikor a jármű rálép).
     *
     * @param vehicle a hozzáadandó jármű
     */
    public void addVehicle(Vehicle vehicle) {
        System.out.println("-> point.addVehicle(vehicle)");
        vehicles.add(vehicle);
    }

    /**
     * Eltávolít egy járművet a pontról (amikor a jármű továbbhalad).
     *
     * @param vehicle az eltávolítandó jármű
     */
    public void removeVehicle(Vehicle vehicle) {
        System.out.println("-> point.removeVehicle(vehicle)");
        vehicles.remove(vehicle);
    }

    /**
     * Beállít egy beérkező sávot a ponthoz.
     *
     * @param lane a beérkező sáv
     */
    public void addIncomingLane(Lane lane) {
        System.out.println("-> point.addIncomingLane(lane)");
        incomingLanes.add(lane);
    }

    /**
     * Beállít egy kimenő sávot a ponthoz.
     *
     * @param lane a kimenő sáv
     */
    public void addOutgoingLane(Lane lane) {
        System.out.println("-> point.addOutgoingLane(lane)");
        outgoingLanes.add(lane);
    }
    /**
     * Meghatározza, hogy az adott jármű ráléphet-e (behajthat-e) erre a pontra.
     * A leszármazott osztályok (pl. Tunnel, Junction) egyedi logikát valósíthatnak meg.
     *
     * @param vehicle a vizsgált jármű, amely rálépni szeretne a pontra
     * @return true, ha a jármű ráléphet a pontra, ellenkező esetben false
     */
    public abstract boolean isReachable(Vehicle vehicle);

    /**
     * Ellenőrzi a csomóponton lévő dugókat/baleseteket.
     */
    public abstract void lookForJams();

}
