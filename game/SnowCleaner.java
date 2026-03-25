package game;
import java.util.List;

/**
 * A hóeltakarítást végző felhasználót (játékost) reprezentáló osztály.
 * Nyilvántartja a játékos vagyonát, nyersanyagkészleteit (só, biokerozin), 
 * valamint az általa irányított hókotrókat és a raktáron lévő takarítófejeket.
 */
public class SnowCleaner extends Player {
    /**
     * A játékos által birtokolt és irányított hókotrók (munkagépek) listája.
     */
    private List<SnowPlower> snowPlowers;
    /**
     * A játékos által megvásárolt, takarítófejek listája.
     */
    private List<Head> inventory;
    /**
     * A játékos vagyona, amellyel a boltban (Store) gazdálkodhat.
     */
    private int money;
    /**
     * A rendelkezésre álló sókészlet a sózó fej (SalterHead) működtetéséhez.
     */
    private int saltStock;
    /**
     * A rendelkezésre álló biokerozin készlet a sörényfej (DragonHead) működtetéséhez.
     */
    private int bioKeroseneStock;

    /**
     * Pénzösszeget ír jövő a játékos egyenlegén (pl. sikeres takarítás után).
     *
     * @param amount a jóváíandó pénzösszeg
     */
    public void getPaid(int amount) {}

    /**
     * Csökkenti a megadott típusú nyersanyag készletét a fogyasztás során.
     *
     * @param type a felhasznált nyersanyag típusa (pl. "salt" vagy "biokerosene")
     * @return true, ha van elegendő készlet a fogyasztáshoz, és sikeresen levonták, false ha nincs elég készlet
     */
    public boolean consumeMaterial(String type) {}
}

