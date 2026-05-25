package model;

/**
 * A CrushedStoneHead osztály egy zuzalék szotó fejet reprezentál a játékban.
 */
public class CrushedStoneHead extends Head {
    
    public CrushedStoneHead() {
        super();
    }
    /*
     * A zúzottkőfej tisztít egy adott sávot.
     * @param lane A sáv, amelyet a zúzottkőfej tisztítani fog.
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
