package game;

import java.util.Map;

/**
 * A gazdas�gi rendszert menedzsel� bolt oszt�ly.
 * Felel�s a megv�s�rolhat� eszk�z �s nyersanyagok �rainak nyilv�ntart�s��rt, 
 * valamint a v�s�rl�si tranzakci�k lebonyol�t�s��rt.
 */
public class Store {
    /**
     * A bolt k�n�lat�t �s az �rakat t�rol� adatszerkezet.
     * Kulcs: a t�tel neve (pl. "SalterHead", "salt"), �rt�k: az �r.
     */
    Map<String, Integer> inventory;

    public Store() {
        inventory = null;
    }
    /**
     * Lebonyol�t egy v�s�rl�si tranzakci�t.
     * Ellen�rzi a v�s�rl� p�nz�gyi fedezet�t, majd j�v�hagy�s eset�n levonja az �sszeget 
     * �s �tadja a megv�s�rolt t�telt.
     *
     * @param item a megv�s�rolni k�v�nt t�tel azonos�t�ja
     * @param quantity a v�s�rolni k�v�nt mennyis�g
     * @param buyer a v�s�rl�st kezdem�nyez� takar�t� (SnowCleaner)
     * @return true, ha a v�s�rl�s sikeres volt, false fedezethi�ny eset�n
     */
    public boolean buy(String item, int quantity, SnowCleaner buyer) {
        System.out.println("-> store.buy(item, quantity, snowCleaner)");
        
        System.out.println("  <<create>> item");
        
        buyer.addToInventory(null);
        
        System.out.println("<- true");
        return true;
    }
}
