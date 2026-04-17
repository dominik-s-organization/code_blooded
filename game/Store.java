package game;

import java.util.Map;

/**
 * A gazdasági rendszert menedzselő bolt osztály.
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
     * és átadja a megvásárolt tételt.
     *
     * @param item a megvásárolni kívánt tétel azonosítója
     * @param quantity a vásárolni kívánt mennyiség
     * @param buyer a vásárlást kezdeményező takarító (SnowCleaner)
     * @return true, ha a vásárlás sikeres volt, false fedezethiány esetén
     */
    public boolean buy(String item, int quantity, SnowCleaner buyer) {
        switch (item) {
            case "salt":
                if (checkCurrency(quantity * 1, buyer)) {
                    buyer.setSaltStock(buyer.getSaltStock() + quantity);
                    buyer.setMoney(buyer.getMoney() - quantity * 1);
                    return true;
                }
                return false;
            case "bioKerosene":
                if (checkCurrency(quantity * 3, buyer)) {
                    buyer.setBioKeroseneStock(buyer.getBioKeroseneStock() + quantity);
                    buyer.setMoney(buyer.getMoney() - quantity * 3);
                    return true;
                }
                return false;
            case "crushedStone":
                if (checkCurrency(quantity * 2, buyer)) {
                    // nem szabad túlmenjen a max kapacitáson
                    int fixedQuantity = Math.min(quantity, buyer.getMaxCrushedStone() - buyer.getCrushedStoneStock());
                    buyer.setCrushedStoneStock(buyer.getCrushedStoneStock() + fixedQuantity);
                    buyer.setMoney(buyer.getMoney() - fixedQuantity * 2);
                    return true;
                }
                return false;
            case "ThrowerHead":
                if (checkCurrency(quantity * 180, buyer)) {
                    buyer.addHead(new ThrowerHead());
                    buyer.setMoney(buyer.getMoney() - quantity * 180);
                    return true;
                }
                return false;
            case "IceBreakerHead":
                if (checkCurrency(quantity * 200, buyer)) {
                    buyer.addHead(new IceBreakerHead());
                    buyer.setMoney(buyer.getMoney() - quantity * 200);
                    return true;
                }
                return false;
            case "SalterHead":
                if (checkCurrency(quantity * 350, buyer)) {
                    buyer.addHead(new SalterHead());
                    buyer.setMoney(buyer.getMoney() - quantity * 350);
                    return true;
                }
                return false;
            case "DragonHead":
                if (checkCurrency(quantity * 400, buyer)) {
                    buyer.addHead(new DragonHead());
                    buyer.setMoney(buyer.getMoney() - quantity * 400);
                    return true;
                }
                return false;
            case "CrushedStoneHead":
                if (checkCurrency(quantity * 300, buyer)) {
                    buyer.addHead(new CrushedStoneHead());
                    buyer.setMoney(buyer.getMoney() - quantity * 300);
                    return true;
                }
                return false;
            case "SnowPlower":
                if (checkCurrency(quantity * 1000, buyer)) {
                    SnowPlower sp = new SnowPlower();
                    sp.setOwner(buyer);
                    buyer.getSnowPlowers().add(sp);
                    buyer.setMoney(buyer.getMoney() - quantity * 1000);
                    return true;
                }
                return false;
            default:
                return false;
        }
    }

    private boolean checkCurrency(int amount, SnowCleaner buyer) {
        return buyer.getMoney() >= amount;
    }
}