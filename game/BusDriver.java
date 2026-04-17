package game;

/**
 * A BusDriver osztály a Player absztrakt osztályból származik, és egy buszvezetőt reprezentál a játékban.
 */
public class BusDriver extends Player {
    // A buszvezető által teljesített útvonalak száma.
    private int completedRoutes = 0;
    // A busz, amelyet a buszvezető vezet.
    private Bus bus;

    // Konstruktor
    public BusDriver() {
        super();
        completedRoutes = 0;
        bus = new Bus();
        bus.setOwner(this);
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

    // A buszvezető teljesíti az útvonalat, és növeli a teljesített útvonalak számát.
    public void completeRoute() {
        completedRoutes++;
    }
}
