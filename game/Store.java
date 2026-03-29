package game;

import java.util.Map;

/**
 * A gazdasági rendszert menedzselő bolt osztály.
<<<<<<< HEAD
 * Felelős a megvásárolható eszköz és nyersanyagok árainak nyilvántartásáért, 
 * valamint a vásárlási tranzakciók lebonyolításáért.
 */
public class Store {
    private Map<String, Integer> inventory;

    /**
     * Lebonyolít egy vásárlási tranzakciót.
     * Ellenőrzi a vásárló pénzügyi fedezetét, majd jóváhagyás esetén levonja az összeget 
=======
 * Felelős a megvásárolható eszköz és nyersanyagok árainak nyilvántartásáért,
 * valamint a vásárlási tranzakciók lebonyolításáért.
 */
public class Store {
    /**
     * A bolt kínálatát és az árakat tároló adatszerkezet.
     * Kulcs: a tétel neve (pl. "SalterHead", "salt"), érték: az ár.
     */
    Map<String, Integer> inventory;

    public Store() {
        inventory = null;
    }
    /**
     * Lebonyolít egy vásárlási tranzakciót.
     * Ellenőrzi a vásárló pénzügyi fedezetét, majd jóváhagyás esetén levonja az összeget
>>>>>>> origin/main
     * és átadja a megvásárolt tételt.
     *
     * @param item a megvásárolni kívánt tétel azonosítója
     * @param quantity a vásárolni kívánt mennyiség
     * @param buyer a vásárlást kezdeményező takarító (SnowCleaner)
     * @return true, ha a vásárlás sikeres volt, false fedezethiány esetén
     */
<<<<<<< HEAD
    public boolean buy(String item, int quantity, SnowCleaner buyer) {}
=======
    public boolean buy(String item, int quantity, SnowCleaner buyer) {
        System.out.println("-> store.buy(item, quantity, snowCleaner)");
        
        // Itt nyilvan majd durvaább logika

        Head newHead = new DragonHead();
        buyer.addToInventory(newHead);
        
        System.out.println("<- true");
        return true;
    }
>>>>>>> origin/main
}
