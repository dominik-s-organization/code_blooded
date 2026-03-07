package game;

public class Bus extends Vehicle {
    private BusDriver owner;
    private Point beginningPoint;
    private Point endPoint;

    public void switchRoute() {
        beginningPoint = endPoint;
        endPoint = beginningPoint;
    }

    @Override
    public void jam() {}

    @Override
    public void move(Point point) {}
    
}
