package model;

/**
 * A CrushedStoneHead osztály egy zúzottkő-szóró fejet reprezentál a játékban.
 * Célja, hogy a csúszós (jeges) utakra kavicsot szórjon, megakadályozva a járművek megcsúszását.
 */
public class CrushedStoneHead extends Head {

    public CrushedStoneHead() {
        super();
    }
    /**
     * A zúzottkőfej kezeli az adott sávot, nyersanyagot használva zúzottkövet szór szét.
     * @param lane A sáv, amelyet a zúzottkőfej kezel.
     * @param snowPlower A műveletet végrehajtó hókotró.
     */
    @Override
    public void clean(Lane lane, SnowPlower snowPlower) {
        if (snowPlower.getOwner().consumeMaterial("crushedStone")) {
            lane.getSnow().setCrushedStoneLevel(10);
            Logger.log("> ACTION: " + lane.getId() + " state_changed CrushedStoned");
            snowPlower.getOwner().getPaid(15);
        }
    }

    @Override
    public String getType() {
        return "CrushedStoneHead";
    }
}
