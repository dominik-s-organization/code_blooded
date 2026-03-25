package game;

/**
 * A hó messzire történõ elszórására szolgáló hányó fejet reprezentáló osztály.
 * Felelõssége a hó eltávolítása az úttestrõl a szomszédos sávok érintése nélkül (kiveti a rendszerbõl).
 * Jég ellen nem hatékony.
 */
public class ThrowerHead extends Head {

    /**
     * Megtisztítja az adott sávot, a havat messzire hajítva, csökkentve a hóréteget.
     *
     * @param lane a sáv, amelyet a hóhányó fej letakarít
     */    
    @Override
    public void clean(Lane lane) {}
}
