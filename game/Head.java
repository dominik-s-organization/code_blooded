package game;

/**
 * A Head osztály egy fejet reprezentál a játékban.
 */
public abstract class Head {
    /*
    * A fej tisztít egy adott sávot.
    * @param lane A sáv, amelyet a sárkányfej tisztítani fog.
    */
    public abstract void clean(Lane lane);
}
