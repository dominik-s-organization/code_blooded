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

    public CityMap(List<Point> points, List<Lane> lanes, List<Vehicle> vehicles) {
        this.points = points;
        this.lanes = lanes;
        this.vehicles = vehicles;
    }

    public List<Point> getPoints() { 
        System.out.println("-> cityMap.getPoints()");
        System.out.println("<- points");
        return points; 
    }
    public void setPoints(List<Point> points) { 
        System.out.println("-> cityMap.setPoints(points)");
        this.points = points; 
    }

    public List<Lane> getLanes() {
        System.out.println("-> cityMap.getLanes()");
        System.out.println("<- lanes");
        return lanes; 
    }
    public void setLanes(List<Lane> lanes) { 
        System.out.println("-> cityMap.setLanes(lanes)");
        this.lanes = lanes; 
    }

    public List<Vehicle> getVehicles() { 
        System.out.println("-> cityMap.getVehicles()");   
        System.out.println("<- vehicles");
        return vehicles;
    }
    public void setVehicles(List<Vehicle> vehicles) { 
        System.out.println("-> cityMap.setVehicles(vehicles)");
        this.vehicles = vehicles;
    }
}
}
