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

    /**
     * Meghat�rozza, hogy az adott j�rm� r�l�phet-e (behajthat-e) erre a pontra.
     * A lesz�rmazott oszt�lyok (pl. Tunnel, Junction) egyedi logik�t val�s�thatnak meg.
     *
     * @param vehicle a vizsg�lt j�rm�, amely r� szeretne l�pni a pontra
     * @return true, ha a j�rm� r�l�phet a pontra, ellenkez� esetben false
     */
    public abstract boolean isReachable(Vehicle vehicle);

    public abstract void lookForJams();
}
