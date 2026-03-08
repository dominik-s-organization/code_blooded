package game;

public abstract class Vehicle {
    private Point currentPoint;
    private Lane lastLane;
    private int jammedTime;

    public abstract void jam();

    public abstract void move(Point point);
}
