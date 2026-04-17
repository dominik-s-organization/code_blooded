package game;

/**
<<<<<<< HEAD
 * A hó eltakarítására szolgáló seprőfejet reprezentáló osztály.
 * Felelőssége a hó letakarítása az aktuális sávról olyan módon, hogy
 * a havat áthelyezi a jobb oldali szomszédos sávba. A járműpáncélt nem képes feltárni.
=======
 * A hó eltakarátására szolgáló seprőfejet reprezentáló osztály.
 * Felelőssége a hó letakarítása az aktuális sávról olyan módon, hogy
 * a havat áthelyezi az egyik szomszédos sávba. A járműveket nem képes feltárnani.
>>>>>>> main
 */
public class SweepingHead extends Head {
    /**
<<<<<<< HEAD
     * Megtisztítja az adott sávot, a havat a jobb szomszédos sávra áthelyezve.
=======
     * Megtisztítja az adott sávot, a havat a szomszédos sávra áthelyezve.
>>>>>>> main
     *
     * @param lane a sáv, amelyet a seprőfej letakarít
     */
    @Override
    public void clean(Lane lane, SnowPlower snowPlower) {
        if (lane.getRightLane() != null) {
            int snowAmount = lane.getSnow().getLevel();
            int crushedStoneAmount = lane.getSnow().getCrushedStoneLevel();
            lane.getSnow().clean();
            lane.getSnow().setCrushedStoneLevel(0);
            lane.getRightLane().getSnow().raiseBy(snowAmount);
            lane.getRightLane().getSnow().setCrushedStoneLevel(crushedStoneAmount);
        }
        else {
            lane.getSnow().clean();
            lane.getSnow().setCrushedStoneLevel(0);
        }
        snowPlower.getOwner().getPaid(10);
    }
}
