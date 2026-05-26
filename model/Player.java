package model;

import java.io.Serializable;

/**
 * Absztrakt ősosztály a felhasználókat (játékosokat) reprezentáló entitások számára.
 * Közös felületet biztosít a játékosok döntéshozatalához a körök során, 
 * illetve az általuk irányított járművek eléréséhez.
 */
public abstract class Player implements Serializable {
    
    /** A játékos azonosítója vagy megjelenítendő neve. */
    private String name;

    /**
     * Konstruktor, amely beállítja a játékos nevét.
     * @param name A játékos neve.
     */
    public Player(String name) {
        this.name = name;
    }

    /**
     * Visszaadja a játékos nevét.
     * @return A név string formátumban.
     */
    public String getName() {
         return name;
     }
 
    /**
     * Beállítja a játékos nevét.
     * @param name Az új név.
     */
     public void setName(String name) {
         this.name = name;
     }

    /**
     * Visszaadja a játékos által irányított fő járművet.
     * Alapértelmezés szerint null-t ad vissza, a leszármazott osztályok (pl. BusDriver) felülírják.
     * @return A vezérelt Vehicle objektum, vagy null.
     */
     public Vehicle getControlledVehicle() {
         return null; 
     }

    /**
     * Visszaadja a játékos típusát azonosító stringet.
     * @return A játékos típusa (pl. "snow_cleaner", "bus_driver").
     */
    public abstract String getType();
    
    /**
     * A játékos ezen a metóduson keresztül választja ki a következő célállomást
     * (csomópontot) az általa irányított jármű számára.
     * @param vehicle A jármű, amelynek a célállomása kiválasztandó.
     * @param nextLane A kiválasztott célállomás (Lane), amin a jármű át akar haladni.
     */
    public void selectDestination(Vehicle vehicle, Lane nextLane) {
        vehicle.setNextLane(nextLane);
    }

    // Visszaadja a játékos fő információját string formátumban, amely megjeleníthető a GUI-n.
    public String getMainInfo(){
        return this.getName();
    }

    // Visszaadja a játékos további információit string formátumban, amely megjeleníthető a GUI-n.
    public String getSubStatusInfo(){
        return "Status: Ready";
    }

    /**
     * Kiírja a játékos állapotát és tulajdonságait a konzolra.
     */
    public abstract void stat();
}