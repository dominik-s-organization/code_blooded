package game;

/**
 * A sózó fejet reprezentáló osztály.
 * Felelõssége, hogy az útra sót juttasson, ezáltal megakadályozza a hó lerakódását és
 * elõsegítse a meglévõ jég/hó olvadását. Mûködéséhez só szükséges.
 */
public class SalterHead extends Head {
    /**
     * Végrehajtja a sózást a megadott sávon.
     * Növeli a sáv só-szintjét, miközben csökkenti a tulajdonos sókészletét.
     *
     * @param lane a sáv, amelyen a sózó fej kifejti a hatását
     */
    @Override
    public void clean(Lane lane) {}
}
