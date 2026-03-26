package game;

/**
 * A hó eltakarítására szolgáló seprûfejet reprezentáló osztály.
 * Felelõssége a hó letakarítása az aktuális sávról oly módon, hogy 
 * a havat áttolja az egyik szomszédos sávba. A jégpáncélt nem képes feltörni.
 */
public class SweepingHead extends Head {
    
    public SweepingHead() {
        System.out.println("-> sweepingHead.SweepingHead()");
        System.out.println("<- void");
    }
    /**
     * Megtisztítja az adott sávot, a havat a szomszédos sávra áthelyezve.
     *
     * @param lane a sáv, amelyet a seprûfej letakarít
     */
    @Override
    public void clean(Lane lane) {
        System.out.println("-> sweepingHead.clean(lane)");
        
        System.out.println("-> lane.getSnow()");
        System.out.println("<- snow");
        
        System.out.println("-> snow.getLevel()");
        System.out.println("<- level");
        
        System.out.println("-> lane.getRightLane()");
        System.out.println("<- rightLane");
        
        System.out.println("-> rightLane.getSnow()");
        System.out.println("<- rs");
        
        System.out.println("-> rs.raiseBy(level)");
        System.out.println("<- void");
        
        System.out.println("-> snow.clean()");
        System.out.println("<- void");
        
        System.out.println("<- void");
    }
}
