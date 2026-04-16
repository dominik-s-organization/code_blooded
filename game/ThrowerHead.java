package game;

/**
 * A hó messzire történő elszórásra szolgáló hóyny fejet reprezentáló osztály.
 * Felelőssége a hó eltávolítása az útból a szomszédos sávok érintése nélkül (kiveti a rendszerből).
 * Jég ellen nem hatékony.
 */
public class ThrowerHead extends Head {

    public ThrowerHead() {
        super();
    }
    /**
     * Megtisztítja az adott sávot, a havat messzire hajtva, csökkentve a hőrteget.
     *
     * @param lane a sáv, amelyet a hóyny fej letakarít
     */    
    @Override
    public void clean(Lane lane, SnowPlower snowPlower) {
        System.out.println("-> throwerHead.clean(lane, snowPlower)");
    
        lane.getSnow().clean();
        lane.getSnow().setCrushedStoneLevel(0);

        snowPlower.getOwner().getPaid(20);
    }
}
