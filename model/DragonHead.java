package model;

/**
 * A DragonHead osztály egy sárkányfejet reprezentál a játékban.
 * Ez a prémium fej gázturbina segítségével azonnal, fizikai érintkezés nélkül 
 * elolvasztja a havat és a jeget. Működéséhez BioKerozin szükséges.
 */
public class DragonHead extends Head {
    /**
     * Alapértelmezett konstruktor a sárkányfej inicializálásához.
     */
    public DragonHead() {
        super();
    }
    /**
     * A sárkányfej megtisztít egy adott sávot, azonnal eltüntetve a havat és a jeget.
     * A művelethez "BioKerosene" nyersanyagot fogyaszt a játékos készletéből.
     * @param lane A sáv, amelyet a sárkányfej tisztítani fog.
     * @param snowPlower A jármű, ami végrehajtja a műveletet.
     */
    @Override
    public void clean(Lane lane, SnowPlower snowPlower) {
        System.out.println("Cleaned.");
        if (snowPlower.getOwner().consumeMaterial("bioKerosene")) {
            lane.getSnow().clean();
            lane.getSnow().setIce(false);
            lane.getSnow().setBrokenIce(false);
            Logger.log("> ACTION: " + lane.getId() + " state_changed Cleaned");
            snowPlower.getOwner().getPaid(35);
        }
    }

    @Override
    public String getType() {
        return "DragonHead";
    }
}
