package game;

/**
 * Absztrakt osztály a mozgó entitások (autók, buszok, hókotrók) számára.
 * Felelőssége az aktuális pozíció és az elakadási idő nyilvántartása,
 * valamint a mozgás és elakadás alapvető műveleteinek definiálása.
 */
public abstract class Vehicle { 
    /**
     * A jármű aktuális helyzete (csomópontja) az úthálózatban.
     */
    private Point currentPoint;
    /**
     * A legutóbbi sáv, amelyen a jármű átment, mielőtt a jelenlegi pontra lépett.
     */
    private Lane lastLane;

    /** 
     * A jármű következő célállomása (pontja), amely felé a jármű haladni próbál.
     */
    protected Point nextPoint;
    /**
     * A várakozási vagy elakadási idő (körökben mérve). 
     * Amíg ez az érték nagyobb nullánál, a jármű nem tud mozogni.
     */
    private int jammedTime;

    public Vehicle() {
        currentPoint = null;
        lastLane = null;
        jammedTime = 0;
    }

    public Point getCurrentPoint() {
        System.out.println("-> vehicle.getCurrentPoint()");
        System.out.println("<- currentPoint");
        return currentPoint;
    }

    public void setCurrentPoint(Point currentPoint) {
        System.out.println("-> vehicle.setCurrentPoint(currentPoint)");
        this.currentPoint = currentPoint;
    }

    public Lane getLastLane() {
        System.out.println("-> vehicle.getLastLane()");
        System.out.println("<- lastLane");
        return lastLane;
    }

    public void setLastLane(Lane lastLane) {
        System.out.println("-> vehicle.setLastLane(lastLane)");
        this.lastLane = lastLane;
    }

    /**
     * Visszaadja a jármű következő célpontját, amely felé haladni próbál.
     * @return a következő pont (Point), amely felé a jármű haladni próbál
     */
    public abstract Point getNextPoint();

    public void setNextPoint(Point nextPoint) {
        System.out.println("-> vehicle.setNextPoint(nextPoint)");
        this.nextPoint = nextPoint;
    }

    public int getJammedTime() {
        System.out.println("-> vehicle.getJammedTime()");
        System.out.println("<- jammedTime");
        return jammedTime;
    }

    public void setJammedTime(int jammedTime) {
        System.out.println("-> vehicle.setJammedTime(jammedTime)");
        this.jammedTime = jammedTime;
    }

    public void decreaseJammedTime() {
        if (jammedTime > 0) {
            jammedTime--;
            System.out.println("-> vehicle.decreaseJammedTime()");
        }
    }
    /**
     * A jármű elakadását, balesetét vagy büntetési idejét kezelő absztrakt metódus.
     * A leszármazott osztályok a saját logikájuk szerint megvalósítják meg.
     */
    public abstract void jam();

    /**
     * A járművet a megadott célállomás (pont) felé mozgatja.
     *
     * @param point a célállomás (Point), ahova a jármű lépni próbál
     */
    public abstract void move(Point point);
}
