package game;

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
}
