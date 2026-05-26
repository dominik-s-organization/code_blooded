package model;

/**
 * A hó eltakarátására szolgáló seprőfejet reprezentáló osztály.
 * Felelőssége a hó letakarítása az aktuális sávról olyan módon, hogy
 * a havat áthelyezi az egyik szomszédos sávba. A járműveket nem képes feltárnani.
 */
public class SweepingHead extends Head {
    /**
     * Megtisztítja az adott sávot úgy, hogy a havat (és az abban lévő zúzottkövet)
     * a jobboldali szomszédos sávra tolja át. Ha nincs jobboldali sáv, a hó eltűnik.
     * A folyamatért a játékos 10$ fizetséget kap.
     * @param lane A sáv, amelyet a seprőfej letakarít.
     * @param snowPlower A jármű, amely végrehajtja az akciót.
     */
    @Override
    public void clean(Lane lane, SnowPlower snowPlower) {
        if (lane.getRightLane() != null) {
            int snowAmount = lane.getSnow().getSnowLevel();
            int crushedStoneAmount = lane.getSnow().getCrushedStoneLevel();
            lane.getSnow().clean();
            lane.getSnow().setCrushedStoneLevel(0);
            lane.getRightLane().getSnow().raiseBy(snowAmount);
            lane.getRightLane().getSnow().setCrushedStoneLevel(crushedStoneAmount);
            Logger.log("> ACTION: " + lane.getId() + " state_changed Cleaned");
        }
        else {
            lane.getSnow().clean();
            lane.getSnow().setCrushedStoneLevel(0);
            Logger.log("> ACTION: " + lane.getId() + " state_changed Cleaned");
        }
        snowPlower.getOwner().getPaid(10);
    }

    @Override
    public String getType() {
        return "SweepingHead";
    }
}
