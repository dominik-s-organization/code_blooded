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
    private List<SnowPlower> snowPlowers = new ArrayList<>();
    /**
     * A játékos által megvásárolt, takarítófejek listája.
     */
    private List<Head> inventory = new ArrayList<>();
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

    public SnowCleaner() {
    }

    public List<SnowPlower> getSnowPlowers() {
        System.out.println("-> snowCleaner.getSnowPlowers()");
        System.out.println("<- snowPlowers");
        return snowPlowers;
    }

    public void setSnowPlowers(List<SnowPlower> snowPlowers) {
        System.out.println("-> snowCleaner.setSnowPlowers(snowPlowers)");
        this.snowPlowers = snowPlowers;
    }

    public List<Head> getInventory() {
        System.out.println("-> snowCleaner.getInventory()");
        System.out.println("<- inventory");
        return inventory;
    }

    public void setInventory(List<Head> inventory) {
        System.out.println("-> snowCleaner.setInventory(inventory)");
        this.inventory = inventory;
    }

    public int getMoney() {
        System.out.println("-> snowCleaner.getMoney()");
        System.out.println("<- money");
        return money;
    }

    public void setMoney(int money) {
        System.out.println("-> snowCleaner.setMoney(money)");
        this.money = money;
    }

    public int getSaltStock() {
        System.out.println("-> snowCleaner.getSaltStock()");
        System.out.println("<- saltStock");
        return saltStock;
    }

    public void setSaltStock(int saltStock) {
        System.out.println("-> snowCleaner.setSaltStock(saltStock)");
        this.saltStock = saltStock;
    }

    public int getBioKeroseneStock() {
        System.out.println("-> snowCleaner.getBioKeroseneStock()");
        System.out.println("<- bioKeroseneStock");
        return bioKeroseneStock;
    }

    public void setBioKeroseneStock(int bioKeroseneStock) {
        System.out.println("-> snowCleaner.setBioKeroseneStock(bioKeroseneStock)");
        this.bioKeroseneStock = bioKeroseneStock;
    }
    /**
     * Pénzösszeget ír jövő a játékos egyenlegén (pl. sikeres takarítás után).
     *
     * @param amount a jóváíandó pénzösszeg
     */
    public void getPaid(int amount) {
        System.out.println("-> snowCleaner.getPaid(amount)");
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
        boolean hasEnough = true; 
        System.out.println("<- hasEnough");
        return hasEnough;
    }

    /**
     * A boltban megvásárolt takarítófejet a játékos raktárába helyezi.
     * * @param head a megvásárolt takarítófej
     */
    public void addToInventory(Head head) {
        System.out.println("-> snowCleaner.addToInventory(head)");
        inventory.add(head);
    }
}

