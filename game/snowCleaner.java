package game;
import java.util.List;

/**
 * A hóeltakarítást végzõ felhasználót (játékost) reprezentáló osztály.
 * Nyilvántartja a játékos vagyonát, nyersanyagkészleteit (só, biokerozin), 
 * valamint az általa irányított hókotrókat és a raktáron lévõ takarítófejeket.
 */
public class SnowCleaner extends Player {
    /**
     * A játékos által birtokolt és irányított hókotrók (munkagépek) listája.
     */
    private List<SnowPlower> snowPlowers;
    /**
     * A játékos által megvásárolt, de aktuálisan egy gépre sem felszerelt takarítófejek listája.
     */
    private List<Head> inventory;
    /**
     * A játékos vagyona, amellyel a boltban (Store) gazdálkodhat.
     */
    private int money;
    /**
     * A rendelkezésre álló sókészlet a sózó fej (SalterHead) mûködtetéséhez.
     */
    private int saltStock;
    /**
     * A rendelkezésre álló biokerozin készlet a sárkányfej (DragonHead) mûködtetéséhez.
     */
    private int bioKeroseneStock;

    /**
     * Pénzösszeget ír jóvá a játékos egyenlegén (pl. sikeres takarítás után).
     *
     * @param amount a jóváírandó pénzösszeg
     */
    public void getPaid(int amount) {}

    /**
     * Csökkenti a megadott típusú nyersanyag készletét a fogyasztás során.
     *
     * @param type a felhasznált nyersanyag típusa (pl. "salt" vagy "biokerosene")
     */
    public void consumeMaterial(String type) {}
}

