package game;

/**
 * A IceBreakerHead osztály egy jégtörőfejet reprezentál a játékban.
 */
public class IceBreakerHead extends Head {
    
     public IceBreakerHead() {
     }

    /*
    * A jégtörőfej tisztít egy adott sávot.
    * @param lane A sáv, amelyet a jégtörőfej tisztítani fog.
    */
    @Override
    public void clean(Lane lane) {
        System.out.println("-> iceBreakerHead.clean(lane)");
        
        if (lane != null && lane.getSnow() != null) {
            lane.getSnow().setIce(false);
            lane.getSnow().setBrokenIce(true);
        }
    }
}
