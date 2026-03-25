package game;

/**
 * A BusDriver osztály a Player absztrakt osztályból származik, és egy buszvezetőt reprezentál a játékban.
 */
public class BusDriver extends Player {
    // A buszvezető által teljesített útvonalak száma.
    private int completedRoutes = 0;
    // A busz, amelyet a buszvezető vezet.
    private Bus bus;

    // A buszvezető teljesíti az útvonalat, és növeli a teljesített útvonalak számát.
    public void completeRoute() {
        completedRoutes++;
    }
}
