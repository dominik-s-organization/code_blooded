package game;

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
            
            snowPlower.getOwner().getPaid(15);
        }
    }
}
