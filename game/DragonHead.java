package game;

/**
 * A DragonHead osztály egy sárkányfejet reprezentál a játékban.
 */
public class DragonHead extends Head {
    
    public DragonHead() {
        super();
    }
    /*
    * A sárkányfej tisztít egy adott sávot.
    * @param lane A sáv, amelyet a sárkányfej tisztítani fog.
    */
    @Override
    public void clean(Lane lane) {
        System.out.println("-> dragonHead.clean(lane)");
        
        System.out.println("-> snowCleaner.consumeMaterial(biokerosene)");
        System.out.println("<- true"); 
        
        if (lane != null && lane.getSnow() != null) {
            lane.getSnow().clean();
        } else {
            System.out.println("-> snow.clean()");
        }
    }
}
