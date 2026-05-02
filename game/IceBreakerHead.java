package game;

/**
 * A IceBreakerHead osztály egy jégtörőfejet reprezentál a játékban.
 */
public class IceBreakerHead extends Head {
    //Konstruktor
     public IceBreakerHead() {
        super();
     }

    /*
    * A jégtörőfej tisztít egy adott sávot.
    * @param lane A sáv, amelyet a jégtörőfej tisztítani fog.
    */
    @Override
    public void clean(Lane lane, SnowPlower snowPlower) {
        if (lane.getSnow().isIce()) {
            lane.getSnow().setIce(false);
            lane.getSnow().setBrokenIce(true);

            snowPlower.getOwner().getPaid(25);
        }
    }
}
