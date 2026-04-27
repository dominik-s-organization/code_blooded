package game;

import java.util.ArrayList;
import java.util.List;

/**
 * A CityMap osztály egy város térképét reprezentálja,
 * amely tartalmazza a pontokat, útvonalakat és járműveket.
 */
public class CityMap {
    // A város térképén található pontok listája.
    private List<Point> points;
    // A város térképén található sávok listája, amelyek az útvonalakat reprezentálják.
    private List<Lane> lanes;
    // A város térképén található járművek listája, amelyek a forgalmat reprezentálják.
    private List<Vehicle> vehicles;

    public CityMap() {
        points = new ArrayList<>();
        lanes = new ArrayList<>();
        vehicles = new ArrayList<>();
    }

    public CityMap(List<Point> points, List<Lane> lanes, List<Vehicle> vehicles) {
        this.points = points;
        this.lanes = lanes;
        this.vehicles = vehicles;
    }

    public int getPointCount() {
        return points.size();
    }

    public int getLaneCount() {
        return lanes.size();
    }

    public int getVehicleCount() {
        return vehicles.size();
    }

    public List<Point> getPoints() { 
        return points; 
    }

    public void setPoints(List<Point> points) { 
        this.points = points; 
    }

    public void addPoint(Point point) {
        points.add(point);
    }

    public List<Lane> getLanes() {
        return lanes; 
    }

    public void setLanes(List<Lane> lanes) { 
        this.lanes = lanes; 
    }

    public void addLane(Lane lane) {
        lanes.add(lane);
    }

    public List<Vehicle> getVehicles() { 
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) { 
        this.vehicles = vehicles;
    }

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }
}
