package game;

/**
 * Absztrakt õsosztály a mozgó entitások (autók, buszok, hókotrók) számára.
 * Felelõssége az aktuális pozíció és az elakadási idõ nyilvántartása, 
 * valamint a mozgás és elakadás alapvetõ mûveleteinek definiálása.
 */
public abstract class Vehicle { 
    /**
     * A jármû aktuális helyzete (csomópontja) az úthálózatban.
     */
    private Point currentPoint;
    /**
     * A legutóbbi sáv, amelyen a jármû tartózkodott, mielõtt a jelenlegi pontra lépett.
     */
    private Lane lastLane;
    /**
     * A várakozási vagy elakadási idõ (körökben mérve). 
     * Amíg ez az érték nagyobb nullánál, a jármû nem tud mozogni.
     */
    private int jammedTime;

    /**
     * A jármû elakadását, balesetét vagy büntetési idejét kezelõ absztrakt metódus.
     * A leszármazott osztályok a saját logikájuk szerint valósítják meg.
     */
    public abstract void jam();

    /**
     * A jármûvet a megadott célállomás (pont) felé mozgatja.
     *
     * @param point a célállomás (Point), ahova a jármû lépni próbál
     */
    public abstract void move(Point point);
}
