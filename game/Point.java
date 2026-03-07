package game;

import java.util.List;

public abstract class Point {
    private List<Vehicle> vehicles;
    private List<Lane> incomingLanes;
    private List<Lane> outgoingLanes;

    public abstract boolean isReachable(Vehicle vehicle, Point point);
}
