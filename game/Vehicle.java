package game;

public abstract class Vehicle {
    private Point currentPoint;
    private Lane lastLane;

    public abstract void jam();

    public abstract void move(Point point);
}
