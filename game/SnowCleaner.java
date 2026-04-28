package game;
import java.util.ArrayList;
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
     * A rendelkezésre álló töröttkő készlet a töröttkőfej (CrushedStoneHead) működtetéséhez.
     */
    private int crushedStoneStock;
    /**
     * A töröttő készlet maximális mennyisége
     */
    private int maxCrushedStone;

    public SnowCleaner(String name) {
        super(name);
        snowPlowers = new ArrayList<>();
        inventory = new ArrayList<>();
        money = 0;
        saltStock = 0;
        bioKeroseneStock = 0;
        crushedStoneStock = 0;
        maxCrushedStone = 100;

        SnowPlower sp = new SnowPlower();
        snowPlowers.add(sp);
        sp.setOwner(this);
        inventory.add(sp.getCurrentHead());
    }

    public List<SnowPlower> getSnowPlowers() {
        return snowPlowers;
    }

    public void setSnowPlowers(List<SnowPlower> snowPlowers) {
        this.snowPlowers = snowPlowers;
    }

    public List<Head> getInventory() {
        return inventory;
    }

    public void setInventory(List<Head> inventory) {
        this.inventory = inventory;
    }

    public void addHead(Head head) {
        inventory.add(head);
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getSaltStock() {
        return saltStock;
    }

    public void setSaltStock(int saltStock) {
        this.saltStock = saltStock;
    }

    public int getBioKeroseneStock() {
        return bioKeroseneStock;
    }

    public void setBioKeroseneStock(int bioKeroseneStock) {
        this.bioKeroseneStock = bioKeroseneStock;
    }

    public int getCrushedStoneStock() {
        return crushedStoneStock;
    }

    public void setCrushedStoneStock(int crushedStoneStock) {
        this.crushedStoneStock = crushedStoneStock;
    }

    public int getMaxCrushedStone() {
        return maxCrushedStone;
    }
    
    public void setMaxCrushedStone(int maxCrushedStone) {
        this.maxCrushedStone = maxCrushedStone;
    }


    /**
     * Pénzösszeget ír jövő a játékos egyenlegén (pl. sikeres takarítás után).
     *
     * @param amount a jóváíandó pénzösszeg
     */
    public void getPaid(int amount) {
        this.money += amount;
    }

    /**
     * Csökkenti a megadott típusú nyersanyag készletét a fogyasztás során.
     *
     * @param type a felhasznált nyersanyag típusa (pl. "salt" vagy "biokerosene")
     * @return true, ha van elegendő készlet a fogyasztáshoz, és sikeresen levonták, false ha nincs elég készlet
     */
    public boolean consumeMaterial(String type) {
        System.out.println("-> snowCleaner.consumeMaterial(type)");
        
        if (type.equals("salt")) {
            if (saltStock >= 10) {
                saltStock -= 10;
                return true;
            }
        } else if (type.equals("bioKerosene")) {
            if (bioKeroseneStock >= 10) {
                bioKeroseneStock -= 10;
                return true;
            }
        } else if (type.equals("crushedStone")) {
            if (crushedStoneStock >= 10) {
                crushedStoneStock -= 10;
                return true;
            }
        }
        return false;
    }

    @Override
    public String getType() {
        return "snow_cleaner";
    }
}

