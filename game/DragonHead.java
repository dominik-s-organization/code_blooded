package game;

/**
 * A DragonHead osztály egy sárkányfejet reprezentál a játékban.
 */
public class DragonHead extends Head {
    //Konstruktor
    public DragonHead() {
        super();
    }

    /*
    * A sárkányfej tisztít egy adott sávot.
    * @param lane A sáv, amelyet a sárkányfej tisztítani fog.
    */
    @Override
    public void clean(Lane lane, SnowPlower snowPlower) {
        if (snowPlower.getOwner().consumeMaterial("bioKerosene")) {
            lane.getSnow().clean();
            lane.getSnow().setIce(false);
            lane.getSnow().setBrokenIce(false);
            
            snowPlower.getOwner().getPaid(35);
        }
    }
}
