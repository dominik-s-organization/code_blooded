package game;

import java.util.Map;

/**
 * A gazdasági rendszert menedzselő bolt osztály.
 * Felelős a megvásárolható eszköz és nyersanyagok árainak nyilvántartásáért, 
 * valamint a vásárlási tranzakciók lebonyolításáért.
 */
public class Store {
    private Map<String, Integer> inventory;

    /**
     * Lebonyolít egy vásárlási tranzakciót.
     * Ellenőrzi a vásárló pénzügyi fedezetét, majd jóváhagyás esetén levonja az összeget 
     * és átadja a megvásárolt tételt.
     *
     * @param item a megvásárolni kívánt tétel azonosítója
     * @param quantity a vásárolni kívánt mennyiség
     * @param buyer a vásárlást kezdeményező takarító (SnowCleaner)
     * @return true, ha a vásárlás sikeres volt, false fedezethiány esetén
     */
    public boolean buy(String item, int quantity, SnowCleaner buyer) {}
}
