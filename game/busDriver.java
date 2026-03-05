package game;

public class BusDriver extends Player {
    private int completedRoutes = 0;

    public BusDriver(String name, int completedRoutes) {
        super(name);
        this.completedRoutes = completedRoutes;
    }

    @Override
    public void makeMove() {}
}
