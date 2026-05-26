package model;

/**
 * A BusDriver osztály a Player absztrakt osztályból származik, és egy tömegközlekedési
 * sofőrt reprezentál a játékban. Felelős a saját buszának irányításáért és az
 * útvonalak sikeres teljesítésének nyilvántartásáért.
 */
public class BusDriver extends Player {
    // A buszvezető által teljesített útvonalak száma.
    private int completedRoutes = 0;
    // A busz, amelyet a buszvezető vezet.
    private Bus bus;
    /**
     * Konstruktor, amely inicializálja a sofőrt, és hozzárendel egy új buszt.
     * @param name A buszsofőr neve.
     */
    public BusDriver(String name) {
        super(name);
        completedRoutes = 0;
        bus = new Bus();
        bus.setOwner(this);
    }

    @Override
    public void stat() {
        Logger.log("BusDriver: " + getName());
        Logger.log("Completed Routes: " + getCompletedRoutes());
        Logger.log("Bus: " + getBus().getId());
        Logger.log("");
    }

    // Getterek, setterek
    public int getCompletedRoutes() {
        return completedRoutes;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    @Override
    public String getType() {
        return "bus_driver";
    }
    /**
     * Visszaadja a grafikus felület számára a buszsofőr legfontosabb információit,
     * beleértve a nevét és az aktuális célállomását (végállomás).
     * @return A formázott fő információ szövege.
     */
    @Override
    public String getMainInfo() {
        String destination = "Ismeretlen";
        
        if (this.getBus() != null) {
            // Itt lekérjük a busz jelenlegi célpontját (végállomását)
            if (this.getBus().getEndPoint() != null) {
                destination = this.getBus().getEndPoint().getId();
            }
        }
        
        return this.getName() + " -> Dest: " + destination;
    }
    /**
     * Visszaadja a grafikus felület számára az extra státusz információkat,
     * jelen esetben a sikeresen befejezett járatok számát.
     * @return A teljesített utak száma szövegesen.
     */
    @Override
    public String getSubStatusInfo() {
        return "Teljesített utak: " + this.completedRoutes;
    }
    /**
     * A buszvezető teljesít egy útvonalat (végállomástól végállomásig ér).
     * Ez a metódus megnöveli a teljesített járatok számlálóját.
     */
    public void completeRoute() {
        completedRoutes++;
    }
}
