package game;

import java.util.Map;

/**
 * A gazdasági rendszert menedzselõ bolt osztály.
 * Felelõs a megvásárolható eszköz és nyersanyagok árainak nyilvántartásáért, 
 * valamint a vásárlási tranzakciók lebonyolításáért.
 */
public class Store {
    /**
     * A bolt kínálatát és az árakat tároló adatszerkezet.
     * Kulcs: a tétel neve (pl. "SalterHead", "salt"), Érték: az ár.
     */
    Map<String, Integer> inventory;

    public Store(Map<String, Integer> inventory) {
        System.out.println("-> store.Store(inventory)");
        this.inventory = inventory;
        System.out.println("<- void");
    }
    /**
     * Lebonyolít egy vásárlási tranzakciót.
     * Ellenõrzi a vásárló pénzügyi fedezetét, majd jóváhagyás esetén levonja az összeget 
     * és átadja a megvásárolt tételt.
     *
     * @param item a megvásárolni kívánt tétel azonosítója
     * @param quantity a vásárolni kívánt mennyiség
     * @param buyer a vásárlást kezdeményezõ takarító (SnowCleaner)
     * @return true, ha a vásárlás sikeres volt, false fedezethiány esetén
     */
    public boolean buy(String item, int quantity, SnowCleaner buyer) {
        System.out.println("-> store.buy(item, quantity, snowCleaner)");
        
        System.out.println("  <<create>> item");
        
        buyer.addToInventory(null);
        
        System.out.println("<- true");
        return true;
    }
}
