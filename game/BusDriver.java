package game;

/**
 * A BusDriver osztály a Player absztrakt osztályból származik, és egy buszvezetőt reprezentál a játékban.
 */
public class BusDriver extends Player {
    // A buszvezető által teljesített útvonalak száma.
    private int completedRoutes = 0;
    // A busz, amelyet a buszvezető vezet.
    private Bus bus;

    public BusDriver() {
        completedRoutes = 0;
        bus = new Bus();
    }

    public int getCompletedRoutes() {
        System.out.println("-> busDriver.getCompletedRoutes()");
        System.out.println("<- completedRoutes");
        return completedRoutes;
    }

    public Bus getBus() {
        System.out.println("-> busDriver.getBus()");
        System.out.println("<- bus");
        return bus;
    }

    public void setBus(Bus bus) {
        System.out.println("-> busDriver.setBus(bus)");
        this.bus = bus;
    }

    // A buszvezető teljesíti az útvonalat, és növeli a teljesített útvonalak számát.
    public void completeRoute() {
        System.out.println("-> busDriver.completeRoute()");
        completedRoutes++;
    }
}
