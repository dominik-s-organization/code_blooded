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

    //Konstruktorok
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

    // Getterek, setterek
    public List<Point> getPoints() { 
        if (points.size() > 0) {
            System.out.println("-> cityMap.getPoints()");
            System.out.println("<- points");
        }
        return points; 
    }
    public void setPoints(List<Point> points) { 
        System.out.println("-> cityMap.setPoints(points)");
        this.points = points; 
    }

    public void addPoint(Point point) {
        System.out.println("-> cityMap.addPoint(point)");
        points.add(point);
    }

    public List<Lane> getLanes() {
        if (lanes.size() > 0) {
            System.out.println("-> cityMap.getLanes()");
            System.out.println("<- lanes");
        }
        return lanes; 
    }
    public void setLanes(List<Lane> lanes) { 
        System.out.println("-> cityMap.setLanes(lanes)");
        this.lanes = lanes; 
    }

    public void addLane(Lane lane) {
        System.out.println("-> cityMap.addLane(lane)");
        lanes.add(lane);
    }

    public List<Vehicle> getVehicles() { 
        if (vehicles.size() > 0) {
            System.out.println("-> cityMap.getVehicles()");
            System.out.println("<- vehicles");
        }
        return vehicles;
    }
    public void setVehicles(List<Vehicle> vehicles) { 
        System.out.println("-> cityMap.setVehicles(vehicles)");
        this.vehicles = vehicles;
    }

    public void addVehicle(Vehicle vehicle) {
        System.out.println("-> cityMap.addVehicle(vehicle)");
        vehicles.add(vehicle);
    }
}
