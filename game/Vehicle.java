package game;

/**
 * Absztrakt ősosztály a mozgó entitások (autók, buszok, hókotrók) számára.
 * Felelőssége az aktuális pozíció és az elakadási idő nyilvántartása, 
 * valamint a mozgás és elakadás alapvető műveleteinek definiálása.
 */
public abstract class Vehicle { 
    /**
     * A jármű aktuális helyzete (csomópontja) az úthálózatban.
     */
    private Point currentPoint;
    /**
     * A legutóbbi sáv, amelyen a jármű áthaladott, mielőtt a jelenlegi pontra lépett.
     */
    private Lane lastLane;
    /**
     * A várakozási vagy elakadási idő (körökben mérve). 
     * Amíg ez az érték nagyobb nullánál, a jármű nem tud mozogni.
     */
    private int jammedTime;

    /**
     * A jármű elakadását, balesetét vagy büntetési idejét kezelő absztrakt metódus.
     * A leszármazott osztályok a saját logikájuk szerint valósítják meg.
     */
    public abstract void jam();

    /**
     * A járművet a megadott célállomás (pont) felé mozgatja.
     *
     * @param point a célállomás (Point), ahova a jármű lépni próbál
     */
    public abstract void move(Point point);
}
