package game;

/**
 * A sózó fejet reprezentáló osztály.
 * Felelőssége, hogy az útra sót juttasson, ezáltal megakadályozza a hó lerakódását és
 * elősegíti a meglévő jég/hó olvadását. Működéséhez só szükséges.
 */
public class SalterHead extends Head {
    
    public SalterHead() {
        super();
    }
    /**
     * Végrehajtja a sózást a megadott sávon.
     * Növeli a sáv só-szintjét, miközben csökkenti a tulajdonos só-készletét.
     *
     * @param lane a sáv, amelyen a sózó fej kifejti a hatását
     */
    @Override
    public void clean(Lane lane, SnowPlower snowPlower) {
        if (snowPlower.getOwner().consumeMaterial("salt")) {
            lane.getSnow().setSaltLevel(20);

            snowPlower.getOwner().getPaid(25);
        }
    }
}
