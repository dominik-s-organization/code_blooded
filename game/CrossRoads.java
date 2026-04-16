package game;

/**
 * A CrossRoads osztály egy kereszteződést reprezentál a játékban.
 */
public class CrossRoads extends Point {
    
    public CrossRoads() {
        super();
    }

    protected boolean isReachableHelp(Point point, Vehicle vehicle) {
        for (Lane incomingLane : point.getIncomingLanes()) {
            if (incomingLane.getStartPoint().equals(vehicle.getCurrentPoint()) && !incomingLane.isJammed() && (incomingLane.getSnow().getLevel() < 15 || vehicle instanceof SnowPlower)) {
                return true;
            }
        }
        return false;
    }
}
