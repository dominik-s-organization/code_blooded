package game;

import java.util.List;

/**
 * Absztrakt õsosztály az úthálózat topológiai pontjainak (pl. csomópontok, keresztezõdések) reprezentálására.
 * Felelõssége a becsatlakozó és kimenõ sávok, valamint az adott ponton tartózkodó jármûvek nyilvántartása.
 */
public abstract class Point {
    /**
     * A ponton (keresztezõdésben) éppen tartózkodó jármûvek listája.
     */
    private List<Vehicle> vehicles;
    /**
     * A pontba beérkezõ sávok listája.
     */
    private List<Lane> incomingLanes;
    /**
     * A pontból kiinduló sávok listája.
     */
    private List<Lane> outgoingLanes;

    /**
     * Meghatározza, hogy az adott jármû ráléphet-e (behajthat-e) erre a pontra.
     * A leszármazott osztályok (pl. Tunnel, Junction) egyedi logikát valósíthatnak meg.
     *
     * @param vehicle a vizsgált jármû, amely rá szeretne lépni a pontra
     * @return true, ha a jármû ráléphet a pontra, ellenkezõ esetben false
     */
    public abstract boolean isReachable(Vehicle vehicle);
}
