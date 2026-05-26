package model;

/**
 * A sózó fejet reprezentáló osztály.
 * Felelőssége, hogy az útra sót juttasson, ezáltal megakadályozza a hó lerakódását és
 * elősegíti a meglévő jég/hó olvadását. Működéséhez só szükséges.
 */
public class SalterHead extends Head {
    //Konstruktor
    public SalterHead() {
        super();
    }
    /**
     * Végrehajtja a sózást a megadott sávon.
     * Növeli a sáv só-szintjét, miközben csökkenti a tulajdonos (SnowCleaner) só-készletét.
     * JAVÍTVA: A nyersanyag neve illeszkedik a boltban vásárolhatóhoz ("Salt").
     * @param lane A sáv, amelyen a sózó fej kifejti a hatását.
     * @param snowPlower A jármű, amely végrehajtja az akciót.
     */
    @Override
    public void clean(Lane lane, SnowPlower snowPlower) {
        if (snowPlower.getOwner().consumeMaterial("salt")) {
            lane.getSnow().setSaltLevel(20);
            Logger.log("> ACTION: " + lane.getId() + " state_changed Salted");
            snowPlower.getOwner().getPaid(25);
        }
    }

    @Override
    public String getType() {
        return "SalterHead";
    }
}
