package game;

/**
 * A Takarító (SnowCleaner) játékos által irányított hókotró munkagépet reprezentáló osztály.
 * Felelőssége a felszerelt takarítófej működtetése az adott sávon történő mozgás során.
 */
public class SnowPlower extends Vehicle {
    /**
     * A hókotróra jelenleg felszerelt és aktív takarítófej.
     */
    private Head currentHead;
    /**
     * A hókotrót irányító és birtokló játékos (takarító).
     */
    private SnowCleaner owner;

    public SnowPlower(int id) {
        super(id);
        currentHead = new SweepingHead(); // Alapértelmezett fej, amely seprést végez
        owner = null;
    }

    public Lane getNextLane() {
        return nextLane;
    }

    public Head getCurrentHead() {
        return currentHead;
    }

    public void setCurrentHead(Head currentHead) {
        this.currentHead = currentHead;
    }

    public SnowCleaner getOwner() {
        return owner;
    }
     public void setOwner(SnowCleaner owner) {
        this.owner = owner;
    }
    /**
     * A hókotró elakadását (pl. ütközés vagy járhatatlan út miatt) kezelő metódus.
     */
    @Override
    public void jam() {
        super.setJammedTime(1);
    }

    /**
     * Lépteti a hókotrót a paraméterként kapott célállomás (Point) felé.
     *
     * @param point a cél csomópont, amely felé a hókotró haladni próbál
     */
    @Override
    public void move(Point point) {
        if (super.getJammedTime() > 0) {
            return; // Ha a hókotró elakadt, nem mozoghat.
        }

        if (point.isReachable(this)) {
            super.getCurrentPoint().removeVehicle(this);
            super.setCurrentPoint(point);
            super.getCurrentPoint().addVehicle(this);
            super.setLastLane(nextLane);
            currentHead.clean(super.getLastLane(), this);
        }
    }
    
    /**
     * Lecseréli a hókotróra jelenleg felszerelt takarítófejet egy újra.
     *
     * @param head az új takarítófej (Head), amelyet a gépre szerelnek
     */
    public void changeHead(Head head) {
        this.currentHead = head;
    }
}
