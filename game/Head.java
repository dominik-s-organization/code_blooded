package game;

/**
 * A Head osztály egy fejet reprezentál a játékban.
 */
public abstract class Head {
    /*
    * A fej tisztít egy adott sávot.
    * @param lane A sáv, amelyet a sárkányfej tisztítani fog.
    * @param snowPlower A hóeltávolító, amelyre a fej szerelve van
    */
    public abstract void clean(Lane lane, SnowPlower snowPlower);
}
