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

    public SnowPlower() {
        System.out.println("-> snowPlower.SnowPlower()");
        System.out.println("<- void");
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
        setJammedTime(3);
        System.out.println("-> snowPlower.setJammedTime(3)");
        System.out.println("<- void");
    }

    /**
     * Lépteti a hókotrót a paraméterként kapott célállomás (Point) felé.
     *
     * @param point a cél csomópont, amely felé a hókotró haladni próbál
     */
    @Override
    public void move(Point point) {
    System.out.println("-> snowPlower.move(point)");
        
        if (getCurrentPoint() != null) {
            System.out.println("-> point.removeVehicle(snowPlower)");
            System.out.println("<- void");
        }
        if (point != null) {
            System.out.println("-> point.addVehicle(snowPlower)");
            System.out.println("<- void");
        }
        setCurrentPoint(point);
        
        System.out.println("-> lane.change(snowPlower)");
        System.out.println("<- void");
        
        System.out.println("<- void");
    }
    
    /**
     * Lecseréli a hókotróra jelenleg felszerelt takarítófejet egy újra.
     *
     * @param head az új takarítófej (Head), amelyet a gépre szerelnek
     */
    public void changeHead(Head head) {
        System.out.println("-> snowPlower.changeHead(head)");
        this.currentHead = head;
        System.out.println("<- void");
    }
}
