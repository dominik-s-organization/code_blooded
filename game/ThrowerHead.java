package game;

/**
 * A hó messzire történő elszórására szolgáló hóhány fejet reprezentáló osztály.
 * Felelőssége a hó eltávolítása az útról a szomszédos sávok érintése nélkül (kiveti a rendszerből).
 * Jég ellen nem hatékony.
 */
public class ThrowerHead extends Head {

    public ThrowerHead() {
        super();
    }
    /**
     * Megtiszt�tja az adott s�vot, a havat messzire haj�tva, cs�kkentve a h�r�teget.
     *
     * @param lane a s�v, amelyet a h�h�ny� fej letakar�t
     */    
    @Override
    public void clean(Lane lane, SnowPlower snowPlower) {
        System.out.println("-> throwerHead.clean(lane, snowPlower)");
    
        lane.getSnow().clean();
        lane.getSnow().setCrushedStoneLevel(0);

        snowPlower.getOwner().getPaid(20);
    }
}
