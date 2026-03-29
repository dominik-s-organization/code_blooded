package game;

/**
 * A hó eltakarítására szolgáló seprőfejet reprezentáló osztály.
 * Felelőssége a hó letakarítása az aktuális sávról olyan módon, hogy
 * a havat áthelyezi a jobb oldali szomszédos sávba. A járműpáncélt nem képes feltárni.
 */
public class SweepingHead extends Head {
    /**
     * Megtisztítja az adott sávot, a havat a jobb szomszédos sávra áthelyezve.
     *
     * @param lane a sáv, amelyet a seprőfej letakarít
     */
    @Override
    public void clean(Lane lane, SnowPlower snowPlower) {
        System.out.println("-> sweepingHead.clean(lane, snowPlower)");
        
        if (lane.getRightLane() != null) {
            int amount = lane.getSnow().getLevel();
            lane.getSnow().clean();
            lane.getRightLane().getSnow().raiseBy(amount);
        }
        else {
            lane.getSnow().clean();
        }
        snowPlower.getOwner().getPaid(10);
    }
}
