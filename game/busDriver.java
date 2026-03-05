package game;

public class BusDriver extends Player {
    private int completedRoutes = 0;
    private Bus bus;

    public BusDriver(String name, int completedRoutes) {
        super(name);
        this.completedRoutes = completedRoutes;
    }

    @Override
    public Junction selectDestination(){}
}
