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
    public void clean(Lane lane) {}
}
