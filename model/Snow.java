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
     * A sávra kiszórt aktív zúzott kő mennyiségét jelzi (0-5). 
     * Minden lépés/kör után csökken, ha hó hullott rá. Ha 0, akkor nincs rászórva zúzott kő.
     */
    private int crushedStoneLevel; 

    //Konstruktor
    public Snow() {
        this.level = 0;
        this.vehiclesPassed = 0;
        this.ice = false;
        this.brokenIce = false;
        this.saltLevel = 0;
    }

    // Getterek, setterek
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getVehiclesPassed() {
        return vehiclesPassed;
    }

    public void setVehiclesPassed(int vehiclesPassed) {
        this.vehiclesPassed = vehiclesPassed;
    }

    public boolean isIce() {
        return ice;
    }

    public void setIce(boolean ice) {
        this.ice = ice;
    }

    public boolean isBrokenIce() {
        return brokenIce;
    }

    public void setBrokenIce(boolean brokenIce) {
        this.brokenIce = brokenIce;
    }

    public int getSaltLevel() {
        return saltLevel;
    }

    public void setSaltLevel(int saltLevel) {
        this.saltLevel = saltLevel;
    }

    public int getCrushedStoneLevel() {
        return crushedStoneLevel;
    }

    public void setCrushedStoneLevel(int crushedStoneLevel) {
        this.crushedStoneLevel = crushedStoneLevel;
    }

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

    public void lower() {
        if (level > 0){
            level--;
        }
    }

    /**
     * Megtisztítja a sávot a hótól és a jégtől a takarítófejek hatására.
     */
    public void clean() {
        this.level = 0;
    }

    /**
     * Egy jármű áthaladásakor hívódik meg. Növeli a letaposottság (vehiclesPassed) mértékét, 
     * ami később jégképződéshez vezethet.
     */
    public void passVehicle() {
        vehiclesPassed++;
        if (vehiclesPassed > 4 && level < 15) {
            setIce(true);
        }
    }
    
}
