package model;

/**
 * Az IceBreakerHead osztály egy jégtörő fejet reprezentál a játékban.
 * Képes a lefagyott jégpáncél feltörésére, hogy a sáv ismét járható legyen,
 * de a havat önmagában nem takarítja el.
 */
public class IceBreakerHead extends Head {
    /**
     * Alapértelmezett konstruktor a jégtörő fej inicializálásához.
     */
     public IceBreakerHead() {
        super();
     }

    /**
     * A jégtörő fej feltöri az egybefüggő jégpáncélt az adott sávon.
     * A jég státuszát hamisra, a feltört jég státuszát igazra állítja.
     * @param lane A sáv, amelyet a jégtörő fej kezelni fog.
     * @param snowPlower A műveletet végző hókotró jármű.
     */
    @Override
    public void clean(Lane lane, SnowPlower snowPlower) {
        if (lane.getSnow().isIce()) {
            lane.getSnow().setIce(false);
            lane.getSnow().setBrokenIce(true);
            Logger.log("> ACTION: " + lane.getId() + " state_changed IceBroken");
            snowPlower.getOwner().getPaid(25);
        }
    }

    @Override
    public String getType() {
        return "IceBreakerHead";
    }
}
