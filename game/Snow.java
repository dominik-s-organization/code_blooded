package game;

/**
 * Egy sávon (Lane) lévő hó- és jégviszonyokat reprezentáló osztály.
 * Felelős a hó vastagságának, letaposottságának, a jégképződésnek és a kiszórt só 
 * mennyiségének nyilvántartásáért és logikájának kezeléséért.
 */
public class Snow {
    /**
     * A hó vastagsága (szintje).
     * Értéke 0 és 30 között mozoghat. 0-14-ig a sáv még átkelhető, 15-30 között járhatatlan.
     */
    private int level; 
    /**
     * A havon áthaladó járművek számát tárolja. Ha eléri a küszöböt, a hó jéggé alakul.
     */
    private int vehiclesPassed;
    /**
     * Jelzi, hogy a sávon lévő letaposott hó szilárd jégpáncéllá alakult-e.
     */
    private boolean ice;
    /**
     * Jelzi, hogy az egybefüggő jégpáncélt egy jégtörő fej (IceBreakerHead) már feltörte-e törmelékké.
     */
    private boolean brokenIce;
    /**
     * A sávra kiszórt aktív só mennyiségét jelzi(0-30). 
     * Minden lépés/kör után csökken. Ha 0, akkor a sáv nincs sózva.
     */
    private int saltLevel; 

    /**
     * Megnöveli a hó vastagságát 1 egységgel, amennyiben az még nem érte el a maximumot (30)
     * és a sáv nincs sózva.
     */
    public void raise() {
        if (level < 30) {
            level++;
        }
    }

    /**
     * Adott mennyiséggel növeli a hó vastagságát (maximum 30-ig).
     *
     * @param amount a hó növekedésének mértéke
     */
    public void raiseBy(int amount) {
        level = Math.min(level + amount, 30);
    }
    
    /**
     * Csökkenti a hó vastagságát (pl. olvadás vagy takarítás következtében).
     */
    public void lower() {}

    /**
     * Megtisztítja a sávot a hótól és a jégtől a takarítófejek hatására.
     */
    public void clean() {}

    /**
     * Egy jármű áthaladásakor hívódik meg. Növeli a letaposottság (vehiclesPassed) mértékét, 
     * ami később jégképződéshez vezethet.
     */
    public void passVehicle() {}
}
