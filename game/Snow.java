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

    public Snow() {
        System.out.println("-> snow.Snow()");
        this.level = 0;
        this.vehiclesPassed = 0;
        this.ice = false;
        this.brokenIce = false;
        this.saltLevel = 0;
        System.out.println("<- void");
    }

    public int getLevel() {
        System.out.println("-> snow.getLevel()");
        System.out.println("<- level");
        return level;
    }

    public int setLevel(int level) {
        System.out.println("-> snow.setLevel(level)");
        this.level = level;
        System.out.println("<- void");
    }

    public int getVehiclesPassed() {
        System.out.println("-> snow.getVehiclesPassed()");
        System.out.println("<- vehiclesPassed");
        return vehiclesPassed;
    }

    public void setVehiclesPassed(int vehiclesPassed) {
        System.out.println("-> snow.setVehiclesPassed(vehiclesPassed)");
        this.vehiclesPassed = vehiclesPassed;
        System.out.println("<- void");
    }

    public boolean isIce() {
        System.out.println("-> snow.isIce()");
        System.out.println("<- ice");
        return ice;
    }

    public void setIce(boolean ice) {
        System.out.println("-> snow.setIce(ice)");
        this.ice = ice;
        System.out.println("<- void");
    }

    public boolean isBrokenIce() {
        System.out.println("-> snow.isBrokenIce()");
        System.out.println("<- brokenIce");
        return brokenIce;
    }

    public void setBrokenIce(boolean brokenIce) {
        System.out.println("-> snow.setBrokenIce(brokenIce)");
        this.brokenIce = brokenIce;
        System.out.println("<- void");
    }

    public int getSaltLevel() {
        System.out.println("-> snow.getSaltLevel()");
        System.out.println("<- saltLevel");
        return saltLevel;
    }

    public void setSaltLevel(int saltLevel) {
        System.out.println("-> snow.setSaltLevel(saltLevel)");
        this.saltLevel = saltLevel;
        System.out.println("<- void");
    }

    /**
     * Megnöveli a hó vastagságát 1 egységgel, amennyiben az még nem érte el a maximumot (30)
     * és a sáv nincs sózva.
     */
    public void raise() {
        System.out.println("-> snow.raise()");    
        if (level < 30) {
            level++;
        }
        System.out.println("<- void");
    }

    /**
     * Adott mennyiséggel növeli a hó vastagságát (maximum 30-ig).
     *
     * @param amount a hó növekedésének mértéke
     */
    public void raiseBy(int amount) {
        System.out.println("-> snow.raiseBy(amount)");
        level = Math.min(level + amount, 30);
        System.out.println("<- void");
    }
    public void lower() {}

    /**
     * Csökkenti a hó vastagságát (pl. olvadás vagy takarítás következtében).
     */
    public void lower() {
        System.out.println("-> snow.lower()");
        if (level > 0){
            level--;
        }
        System.out.println("<- void");
    }

    /**
     * Megtisztítja a sávot a hótól és a jégtől a takarítófejek hatására.
     */
    public void clean() {
        System.out.println("-> snow.clean()");
        this.level = 0;
        this.ice = false;
        this.brokenIce = false;
        System.out.println("<- void")
    }

    /**
     * Egy jármű áthaladásakor hívódik meg. Növeli a letaposottság (vehiclesPassed) mértékét, 
     * ami később jégképződéshez vezethet.
     */
    public void passVehicle() {
        System.out.println("-> snow.passVehicle()");
        vehiclesPassed++;
        if (vehiclesPassed > 3) {
            setIce(true);
        }
        System.out.println("<- void");
    }
}
