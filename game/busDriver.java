package game;

public class busDriver extends Player {
    private int completedRoutes = 0;

    public busDriver(String name, int completedRoutes) {
        super(name);
        this.completedRoutes = completedRoutes;
    }

    @Override
    public void makeMove() {}
}
