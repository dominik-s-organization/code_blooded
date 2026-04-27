package game;

/**
 * Interfész az entitások egyedi azonosítóinak generálására.
 */
public interface IdGenerator {
    
    /**
     * Létrehoz egy új, egyedi azonosítót a megadott prefix alapján.
     * * @param prefix Az entitás típusa vagy neve (pl. "lane", "plower", "junction")
     * @return Az egyedi azonosító string (pl. "lane_1")
     */
    String generateId(String prefix);
    
    /**
     * Visszaállítja a számlálót (hasznos lehet tesztelésnél vagy a 'load' parancs újrafutásánál).
     */
    void reset();
}