package game;

import java.util.List;

/**
 * Absztrakt ősosztály azthlzat topolgiai pontjainak (pl. csompontok, kereszteződések) reprezentálására.
 * Felelőssge a becsatlakoző és kimenő sávok, valamint az adott ponton tartózkodó járművek nyilvntartása.
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

    /**
     * Meghatározza, hogy az adott jármű ráléphet-e (behajthat-e) erre a pontra.
     * A leszármazott osztályok (pl. Tunnel, Junction) egyedi logikát valósíthatnak meg.
     *
     * @param vehicle a vizsgált jármű, amely rálépni szeretne a pontra
     * @return true, ha a jármű ráléphet a pontra, ellenkező esetben false
     */
    public abstract boolean isReachable(Vehicle vehicle);

    public abstract void lookForJams();
}
