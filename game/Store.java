package game;

import java.util.Map;

/**
 * A gazdasági rendszert menedzselő bolt osztály.
 * Felelős a megvásárolható eszköz és nyersanyagok árainak nyilvántartásáért,
 * valamint a vásárlási tranzakciók lebonyolításáért.
 */
public class Store {
    
    /**
     * A boltban megvásárolható tárgyakat és azok árait tartalmazó beágyazott felsorolás (enum).
     */
    public enum ItemType {
        SALT(1),
        BIO_KEROSENE(3),
        CRUSHED_STONE(2),
        THROWER_HEAD(180),
        ICE_BREAKER_HEAD(200),
        SALTER_HEAD(350),
        DRAGON_HEAD(400),
        CRUSHED_STONE_HEAD(300),
        SNOW_PLOWER(1000);

        private final int price;

        ItemType(int price) {
            this.price = price;
        }

        public int getPrice() {
            return price;
        }

    /**
     * Szövegből (String) enum értékké alakít.
     *@param text a szöveg, amelyet enum értékké szeretnénk alakítani
     * @return az enum érték, ha a szöveg érvényes, vagy null, ha nem található megfelelő enum
     */
        public static ItemType fromString(String text) {
            for (ItemType type : ItemType.values()) {
                if (type.name().replace("_", "").equalsIgnoreCase(text)) {
                    return type;
                }
            }
            return null;
        }
    }



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
    public boolean buy(String itemName, int quantity, SnowCleaner buyer) {
    ItemType item = ItemType.fromString(itemName);
    if (item == null) {
        return false; 
    }

    int actualQuantity = quantity;
    if (item == ItemType.CRUSHED_STONE) {
        actualQuantity = Math.min(quantity, buyer.getMaxCrushedStone() - buyer.getCrushedStoneStock());
    }

    int totalCost = actualQuantity * item.getPrice();
    if (actualQuantity == 0 || !checkCurrency(totalCost, buyer)) {
        return false; 
    }

    buyer.setMoney(buyer.getMoney() - totalCost);

    switch (item) {
        case SALT:
            buyer.setSaltStock(buyer.getSaltStock() + actualQuantity);
            break;
        case BIO_KEROSENE:
            buyer.setBioKeroseneStock(buyer.getBioKeroseneStock() + actualQuantity);
            break;
        case CRUSHED_STONE:
            buyer.setCrushedStoneStock(buyer.getCrushedStoneStock() + actualQuantity);
            break;
        case THROWER_HEAD:
            for (int i = 0; i < actualQuantity; i++) buyer.addHead(new ThrowerHead());
            break;
        case ICE_BREAKER_HEAD:
            for (int i = 0; i < actualQuantity; i++) buyer.addHead(new IceBreakerHead());
            break;
        case SALTER_HEAD:
            for (int i = 0; i < actualQuantity; i++) buyer.addHead(new SalterHead());
            break;
        case DRAGON_HEAD:
            for (int i = 0; i < actualQuantity; i++) buyer.addHead(new DragonHead());
            break;
        case CRUSHED_STONE_HEAD:
            for (int i = 0; i < actualQuantity; i++) buyer.addHead(new CrushedStoneHead());
            break;
        case SNOW_PLOWER:
            for (int i = 0; i < actualQuantity; i++) {
                SnowPlower sp = new SnowPlower();
                sp.setOwner(buyer);
                buyer.getSnowPlowers().add(sp);
            }
            break;
    }
    
    return true;
}

    private boolean checkCurrency(int amount, SnowCleaner buyer) {
        return buyer.getMoney() >= amount;
    }
}