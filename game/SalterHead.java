package game;

/**
 * A sózó fejet reprezentáló osztály.
 * Felelõssége, hogy az útra sót juttasson, ezáltal megakadályozza a hó lerakódását és
 * elõsegítse a meglévõ jég/hó olvadását. Mûködéséhez só szükséges.
 */
public class SalterHead extends Head {
    
    public SalterHead() {
        System.out.println("-> salterHead.SalterHead()");
        System.out.println("<- void");
    }
    /**
     * Végrehajtja a sózást a megadott sávon.
     * Növeli a sáv só-szintjét, miközben csökkenti a tulajdonos sókészletét.
     *
     * @param lane a sáv, amelyen a sózó fej kifejti a hatását
     */
    @Override
    public void clean(Lane lane) {
        System.out.println("-> salterHead.clean(lane)");
        
        System.out.println("-> snowCleaner.consumeMaterial(salt)");
        System.out.println("<- true");

        if (lane != null && lane.getSnow() != null) {
            lane.getSnow().setSaltLevel(30);
        } else {
            System.out.println("-> snow.setSaltLevel(30)");
            System.out.println("<- void");
        }
        
        System.out.println("<- void");
    }
}
