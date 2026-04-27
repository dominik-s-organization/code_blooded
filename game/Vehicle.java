package game;

/**
 * Absztrakt osztály a mozgó entitások (autók, buszok, hókotrók) számára.
 * Felelőssége az aktuális pozíció és az elakadási idő nyilvántartása,
 * valamint a mozgás és elakadás alapvető műveleteinek definiálása.
 */
public abstract class Vehicle { 
    /**
     * A jármű egyedi azonosítója a játékban.
     */
    private final String id;
    /**
     * A jármű aktuális helyzete (csomópontja) az úthálózatban.
     */
    private Point currentPoint;
    /**
     * A legutóbbi sáv, amelyen a jármű átment, mielőtt a jelenlegi pontra lépett.
     */
    private Lane lastLane;

    /** 
     * A jármű következő célállomása (sávja), amelyen a jármű át akar haladni.
     */
    protected Lane nextLane;
    /**
     * A várakozási vagy elakadási idő (körökben mérve). 
     * Amíg ez az érték nagyobb nullánál, a jármű nem tud mozogni.
     */
    private int jammedTime;
        /**
     * Meghatározza, hogy a jármű tud e csúszni a jegen.
     */
    public boolean canSlip;

    protected Vehicle() {
        this(null);
    }

    protected Vehicle(String id) {
        this.id = id;
        currentPoint = null;
        lastLane = null;
        jammedTime = 0;
    }

    public String getId() {
        return id;
    }

    public Point getCurrentPoint() {
        return currentPoint;
    }

    public void setCurrentPoint(Point currentPoint) {
        this.currentPoint = currentPoint;
    }

    public Lane getLastLane() {
        return lastLane;
    }

    public void setLastLane(Lane lastLane) {
        this.lastLane = lastLane;
    }

    /**
     * Visszaadja a jármű következő célpontját, amely felé haladni próbál.
     * @return a következő sáv (Lane), amelyen a jármű át akar haladni
     */
    public abstract Lane getNextLane();

    public void setNextLane(Lane nextLane) {
        this.nextLane = nextLane;
    }

    public int getJammedTime() {
        return jammedTime;
    }

    public void setJammedTime(int jammedTime) {
        this.jammedTime = jammedTime;
    }

    public void decreaseJammedTime() {
        if (jammedTime > 0) {
            jammedTime--;
        }
    }
    
    /**
     * A jármű elakadását, balesetét vagy büntetési idejét kezelő absztrakt metódus.
     * A leszármazott osztályok a saját logikájuk szerint megvalósítják meg.
     */
    public abstract void jam();

    /**
     * Kiírja a jármű állapotát és tulajdonságait a konzolra.
     */
    public abstract void stat();

    /**
     * A járművet a megadott célállomás (pont) felé mozgatja.
     *
     * @param point a célállomás (Point), ahova a jármű lépni próbál
     */
    public abstract void move(Point point);
}
