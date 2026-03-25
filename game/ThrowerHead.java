package game;

/**
 * A hó messzire történő elszórására szolgáló hányó fejet reprezentáló osztály.
 * Felelőssége a hó eltávolítása az úttestről a szomszédos sávok érintése nélkül (kiveti a rendszerből).
 * Jég ellen nem hatékony.
 */
public class ThrowerHead extends Head {

    /**
     * Megtisztítja az adott sávot, a havat messzire hajtva, lecsökkentve a hóréteget.
     *
     * @param lane a sáv, amelyet a hányó fej letakarít
     */    
    @Override
    public void clean(Lane lane) {}
}
