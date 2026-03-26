package game;

import java.util.List;

/**
 * A CityMap osztály egy város térképét reprezentálja,
 * amely tartalmazza a pontokat, útvonalakat és járműveket.
 */
public class CityMap {
    // A város térképén található pontok listája.
    private List<Point> points = new ArrayList<>();
    // A város térképén található sávok listája, amelyek az útvonalakat reprezentálják.
    private List<Lane> lanes = new ArrayList<>();
    // A város térképén található járművek listája, amelyek a forgalmat reprezentálják.
    private List<Vehicle> vehicles = new ArrayList<>();

    public CityMap() {
    }

    public List<Point> getPoints() { return points; }
    public void setPoints(List<Point> points) { this.points = points; }

    public List<Lane> getLanes() { return lanes; }
    public void setLanes(List<Lane> lanes) { this.lanes = lanes; }

    public List<Vehicle> getVehicles() { return vehicles; }
    public void setVehicles(List<Vehicle> vehicles) { this.vehicles = vehicles; }
}
}
