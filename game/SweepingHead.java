package game;

/**
 * A hó eltakarítására szolgáló seprûfejet reprezentáló osztály.
 * Felelõssége a hó letakarítása az aktuális sávról oly módon, hogy 
 * a havat áttolja az egyik szomszédos sávba. A jégpáncélt nem képes feltörni.
 */
public class SweepingHead extends Head {
    /**
     * Megtisztítja az adott sávot, a havat a szomszédos sávra áthelyezve.
     *
     * @param lane a sáv, amelyet a seprûfej letakarít
     */
    @Override
    public void clean(Lane lane) {}
}
