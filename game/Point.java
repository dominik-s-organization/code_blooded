package game;

import java.util.ArrayList;
import java.util.List;

/**
 * Absztrakt ősosztály az őthálózat topológiai pontjainak (pl. csomópontok, kereszteződésekre) reprezentálására.
 * Felelőssége a becsatlakozó és kimenő sávok, valamint az adott ponton tartózkodó járművek nyilvántartása.
 */
public abstract class Point {
    /**
     * A pont egyedi azonosítója.
     */
    private final String id;
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
    protected Point() {
        this(null);
    }

    protected Point(String id) {
        this.id = id;
        vehicles = new ArrayList<>();
        incomingLanes = new ArrayList<>();
        outgoingLanes = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    // Getterek
    public List<Lane> getIncomingLanes() {
        return incomingLanes;
    }

    public List<Lane> getOutgoingLanes() {
        return outgoingLanes;
    }

     public List<Vehicle> getVehicles() {
        return vehicles;
     }

    /**
     * Hozzáad egy járművet a ponthoz (amikor a jármű rálép).
     *
     * @param vehicle a hozzáadandó jármű
     */
    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    /**
     * Eltávolít egy járművet a pontról (amikor a jármű továbbhalad).
     *
     * @param vehicle az eltávolítandó jármű
     */
    public void removeVehicle(Vehicle vehicle) {
        if (vehicles.contains(vehicle)) {
            vehicles.remove(vehicle);
        }
    }

    /**
     * Beállít egy beérkező sávot a ponthoz.
     *
     * @param lane a beérkező sáv
     */
    public void addIncomingLane(Lane lane) {
        incomingLanes.add(lane);
    }

    /**
     * Beállít egy kimenő sávot a ponthoz.
     *
     * @param lane a kimenő sáv
     */
    public void addOutgoingLane(Lane lane) {
        outgoingLanes.add(lane);
    }

    /*
     * Ellenőrzi, hogy a jármű ráléphet-e a csomópontra.
     * @param vehicle, a kérdéses jármű, amely megpróbál rálépni a csomópontra.
     * @return true, ha a jármű ráléphet a csomópontra, false egyébként.
     */
    public boolean isReachable(Vehicle vehicle) {
        return isReachableHelp(this, vehicle);
    }

    /*
     * Segédfüggvény az isReachable metódushoz, amelyet a konkrét csomópont típusok implementálnak.
     * @param point, a kérdéses csomópont, amelyre a jármű megpróbál rálépni.
     * @param vehicle, a kérdéses jármű, amely megpróbál rálépni a csomópontra.
     * @return true, ha a jármű ráléphet a csomópontra, false egyébként.
     */
    protected abstract boolean isReachableHelp(Point point, Vehicle vehicle);

    /**
     * Ellenőrzi a csomóponton lévő dugókat/baleseteket.
     */
    public void lookForJams() {
        for (Vehicle vehicle1 : getVehicles()) {
            if (vehicle1.getLastLane() == null) {
                continue;
            }
            for (Vehicle vehicle2 : getVehicles()) {
                if (vehicle2.getLastLane() == null) {
                    continue;
                }
                if (!vehicle1.equals(vehicle2) && vehicle1.getLastLane().equals(vehicle2.getLastLane())) {
                    vehicle1.jam();
                    vehicle2.jam();
                    vehicle1.getLastLane().setJammed(true);
                }
            }
        }
    }

    /**
     * Kiírja a pont állapotát és tulajdonságait a konzolra.
     */
    public abstract void stat();
}
