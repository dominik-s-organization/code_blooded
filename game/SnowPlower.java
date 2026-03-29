package game;

/**
<<<<<<< HEAD
 * A Takar�t� (SnowCleaner) j�t�kos �ltal ir�ny�tott h�kotr� munkag�pet reprezent�l� oszt�ly.
 * Felel�ss�ge a felszerelt takar�t�fej m�k�dtet�se az adott s�von t�rt�n� mozg�s sor�n.
 */
public class SnowPlower extends Vehicle {
    /**
     * A h�kotr�ra jelenleg felszerelt �s akt�v takar�t�fej.
     */
    private Head currentHead;
    /**
     * A h�kotr�t ir�ny�t� �s birtokl� j�t�kos (takar�t�).
     */
    private SnowCleaner owner;

    /**
     * A h�kotr� elakad�s�t (pl. �tk�z�s vagy j�rhatatlan �t miatt) kezel� met�dus.
     */
    @Override
    public void jam() {}
=======
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

    public SnowPlower() {
        super();
        currentHead = new SweepingHead(); // Alapértelmezett fej, amely seprést végez
        owner = null;
    }

    public Point getNextPoint() {
        System.out.println("-> snowPlower.getNextPoint()");
        System.out.println("<- nextPoint");
        return nextPoint;
    }

    public Head getCurrentHead() {
        System.out.println("-> snowPlower.getCurrentHead()");
        System.out.println("<- currentHead");
        return currentHead;
    }

    public void setCurrentHead(Head currentHead) {
        System.out.println("-> snowPlower.setCurrentHead(currentHead)");
        this.currentHead = currentHead;
    }

    public SnowCleaner getOwner() {
        System.out.println("-> snowPlower.getOwner()");
        System.out.println("<- owner");
        return owner;
    }
     public void setOwner(SnowCleaner owner) {
        System.out.println("-> snowPlower.setOwner(owner)");
        this.owner = owner;
    }
    /**
     * A hókotró elakadását (pl. ütközés vagy járhatatlan út miatt) kezelő metódus.
     */
    @Override
    public void jam() {
        System.out.println("-> snowPlower.jam()");
        setJammedTime(1);
        System.out.println("-> snowPlower.setJammedTime(1)");
    }
>>>>>>> origin/main

    /**
     * Lépteti a hókotrót a paraméterként kapott célállomás (Point) felé.
     *
     * @param point a cél csomópont, amely felé a hókotró haladni próbál
     */
    @Override
<<<<<<< HEAD
    public void move(Point point) {}
=======
    public void move(Point point) {
        System.out.println("-> snowPlower.move(point)");
        
        if (super.getJammedTime() > 0) {
            return; // Ha a hókotró elakadt, nem mozoghat.
        }

        if (point.isReachable(this)) {
            super.getCurrentPoint().removeVehicle(this);
            super.setCurrentPoint(point);
            super.getCurrentPoint().addVehicle(this);
            super.setLastLane(point.getIncomingLanes().get(0));
            currentHead.clean(super.getLastLane(), this);
        }
    }
>>>>>>> origin/main
    
    /**
     * Lecseréli a hókotróra jelenleg felszerelt takarítófejet egy újra.
     *
     * @param head az új takarítófej (Head), amelyet a gépre szerelnek
     */
    public void changeHead(Head head) {
<<<<<<< HEAD
=======
        System.out.println("-> snowPlower.changeHead(head)");
>>>>>>> origin/main
        this.currentHead = head;
    }
}
