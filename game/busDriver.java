package game;

public class BusDriver extends Player {
    private int completedRoutes = 0;
    private Bus bus;

    public void completeRoute() {
        completedRoutes++;
    }
}
