package game;

public abstract class Vehicle {
    private Point currentPoint;
    private Lane lastLane;
    private Point nextPoint;

    public abstract void jam();

    public abstract void move(Point point);
}
