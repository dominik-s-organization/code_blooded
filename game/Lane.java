package game;

/**
 * A Lane osztály egy sávot reprezentál a játékban.
 */
public class Lane {
    private Point startpoint; // sáv kezdőpontja, ami lehet egy Junction vagy egy CrossRoads
    private Point endpoint; // sáv végpontja, ami lehet egy Junction vagy egy CrossRoads
    private Lane leftLane; // balra eső sáv
    private Lane rightLane; // jobbra eső sáv
    private boolean isJammed; // jelzi, hogy a sávon van-e dugó
    private boolean isUnderground; // jelzi, hogy a sáv föld alatt van-e (aluljáró)
    private int saltLevel; // min = ( nem sózott ) , minden lépés után csökken
    private Snow snow; // a sávon lévő hó mennyisége, amely befolyásolja a járművek sebességét

    //Konstruktor
    public Lane(Point s, Point e, Lane l, Lane r){
        startpoint = s;
        endpoint = e;
        leftLane = l;
        rightLane = r;
        isJammed = false;
    }

    /*
     * Változtat a sáv állapotán.
     * @param vehicle
     * A jármű, amely a sávon mozog, vagy null, ha csak a hó változik.
     */
    public void change(Vehicle vehicle){}   
}
