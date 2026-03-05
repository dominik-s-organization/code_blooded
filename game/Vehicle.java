package game;

public abstract class Vehicle {
    private Junction currentJunction;
    private Lane lastLane;

    abstract void jam();

    abstract void move();
}
