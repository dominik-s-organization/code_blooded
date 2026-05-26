package model;

import java.io.Serializable;

/**
 * Absztrakt ősosztály a mozgó entitások (autók, buszok, hókotrók) számára.
 * Felelőssége az aktuális pozíció és az elakadási idő nyilvántartása,
 * valamint a mozgás és elakadás alapvető műveleteinek definiálása.
 */
public abstract class Vehicle implements Serializable {
    
    /** A jármű egyedi azonosítója a játékban. */
    private String id;
    
    /** A jármű aktuális helyzete (csomópontja) az úthálózatban. */
    private Point currentPoint;
    
    /** A legutóbbi sáv, amelyen a jármű átment, mielőtt a jelenlegi pontra lépett. */
    private Lane lastLane;

    /** A jármű következő célállomása (sávja), amelyen a jármű át akar haladni. */
    protected Lane nextLane;
    
    /** A várakozási vagy elakadási idő (körökben mérve). 
     * Amíg ez az érték nagyobb nullánál, a jármű nem tud mozogni.
     */
    private int jammedTime;
    
    /** Meghatározza, hogy a jármű tud-e csúszni a jégen (fizikai tulajdonság). */
    public boolean canSlip;

    /**
     * Alapértelmezett konstruktor, null azonosítóval inicializál.
     */
    protected Vehicle() {
        this(null);
    }

    /**
     * Konstruktor, amely beállítja a jármű azonosítóját és alaphelyzetbe állítja az attribútumokat.
     * @param id A jármű azonosítója.
     */
    protected Vehicle(String id) {
        this.id = id;
        currentPoint = null;
        lastLane = null;
        jammedTime = 0;
    }

    /**
     * Visszaadja a jármű egyedi azonosítóját.
     * @return Az azonosító string.
     */
    public String getId() {
        return id;
    }

    /**
     * Beállítja a jármű azonosítóját, de csak akkor, ha az még nem volt beállítva.
     * @param id Az új azonosító.
     */
    public void setId(String id) {
        if (this.id == null) {
            this.id = id;
        }
    }

    /**
     * Visszaadja a jármű jelenlegi tartózkodási helyét (csomópontját).
     * @return A jelenlegi Point objektum.
     */
    public Point getCurrentPoint() {
        return currentPoint;
    }

    /**
     * Beállítja a jármű jelenlegi tartózkodási helyét.
     * @param currentPoint Az új csomópont.
     */
    public void setCurrentPoint(Point currentPoint) {
        this.currentPoint = currentPoint;
    }

    /**
     * Visszaadja azt a sávot, amelyről a jármű a jelenlegi pontra érkezett.
     * @return Az előző Lane objektum.
     */
    public Lane getLastLane() {
        return lastLane;
    }

    /**
     * Beállítja azt a sávot, amelyről a jármű a jelenlegi pontra érkezett.
     * @param lastLane Az előző Lane objektum.
     */
    public void setLastLane(Lane lastLane) {
        this.lastLane = lastLane;
    }

    /**
     * Visszaadja a jármű típusának nevét (az osztály nevét).
     * @return A típus neve.
     */
    public String getTypeName() {
        return this.getClass().getSimpleName();
    }

    /**
     * Visszaadja a jármű következő célpontját jelentő sávot.
     * @return A következő sáv (Lane).
     */
    public abstract Lane getNextLane();

    /**
     * Beállítja a jármű következő haladási irányát (sávját).
     * @param nextLane A cél sáv.
     */
    public void setNextLane(Lane nextLane) {
        this.nextLane = nextLane;
    }

    /**
     * Visszaadja a jármű aktuális elakadási idejét körökben.
     * @return A hátralévő elakadási idő.
     */
    public int getJammedTime() {
        return jammedTime;
    }

    /**
     * Beállítja a jármű elakadási idejét.
     * @param jammedTime Az elakadási idő köreinek száma.
     */
    public void setJammedTime(int jammedTime) {
        this.jammedTime = jammedTime;
    }

    /**
     * Csökkenti a jármű elakadási idejét egy körrel, ha az nagyobb nullánál.
     */
    public void decreaseJammedTime() {
        if (jammedTime > 0) {
            jammedTime--;
        }
    }

    /**
     * A jármű elakadását, balesetét vagy büntetési idejét kezelő absztrakt metódus.
     */
    public abstract void jam();

    /**
     * Kiírja a jármű állapotát és tulajdonságait a konzolra.
     */
    public abstract void stat();

   
    /**
     * A járművet a megadott célállomás (pont) felé mozgatja az úthálózaton.
     * Frissíti a jármű tartózkodási helyét és az általa bejárt sávokat.
     * @param point A célállomás (Point), ahova a jármű lépni próbál.
     */
    public abstract void move(Point point);

    /**
     * A jármű interakcióba lép az útszakasszal (sávval), amelyen éppen áthalad.
     * (Például letapossa a havat, vagy a hókotró esetében letakarítja azt).
     * @param lane A sáv (Lane), amellyel a jármű interakcióba lép.
     */
    public abstract void interactWithLane(Lane lane);
}
