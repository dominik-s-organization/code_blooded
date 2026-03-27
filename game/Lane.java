package game;

/**
 * A Lane osztály egy sávot reprezentál a játékban.
 */
public class Lane {
    private Point startpoint; // sáv kezdőpontja, ami lehet egy Junction vagy egy CrossRoads
    private Point endpoint; // sáv végpontja, ami lehet egy Junction vagy egy CrossRoads
    private Lane leftLane; // balra eső sáv
    private Lane rightLane; // jobbra eső sáv
    private boolean isJammed; // jelzi, hogy a sávon van-e dugó
    private boolean isUnderground; // jelzi, hogy a sáv föld alatt van-e (aluljáró)

    private Snow snow = new Snow(); // a sávon lévő hóviszonyokat reprezentáló objektum

    public Lane( Point startpoint, Point endpoint, Lane leftLane, Lane rightLane, boolean isJammed ,boolean isUnderground) {
        this.startpoint = startpoint;
        this.endpoint = endpoint;
        this.leftLane = leftLane;
        this.rightLane = rightLane;
        this.isJammed = false;
        this.isUnderground = isUnderground;
    }

    public void setStartPoint(Point startpoint) {
        System.out.println("-> lane.setStartPoint(point)");
        this.startpoint = startpoint;
    }

    public Point getStartPoint() {
        System.out.println("-> lane.getStartPoint()");
        System.out.println("<- startpoint");
        return startpoint;
    }

    public void setEndPoint(Point endpoint) {
        System.out.println("-> lane.setEndPoint(point)");
        this.endpoint = endpoint;
    }

    public Point getEndPoint() {
        System.out.println("-> lane.getEndPoint()");
        System.out.println("<- endpoint");
        return endpoint;
    }

    public Lane getLeftLane() {
        System.out.println("-> lane.getLeftLane()");
        System.out.println("<- lane");
        return leftLane;
    }

    public void setLeftLane(Lane leftLane) { this.leftLane = leftLane; }

    public Lane getRightLane() {
        System.out.println("-> lane.getRightLane()");
        System.out.println("<- rightLane");
        return rightLane;
    }

    public void setRightLane(Lane rightLane) { this.rightLane = rightLane; }

    public boolean isJammed() { return isJammed; }
    
    public void setJammed(boolean isJammed) {
        System.out.println("-> lane.setJammed(isJammed)");
        this.isJammed = isJammed;
    }

    public boolean isUnderground() { return isUnderground; }
    public void setUnderground(boolean isUnderground) { this.isUnderground = isUnderground; }

    public Snow getSnow() {
        System.out.println("-> lane.getSnow()");
        System.out.println("<- snow");
        return snow;
    }

    public void setSnow(Snow snow) { this.snow = snow; }

    /*
     * Változtat a sáv állapotán.
     * @param vehicle, a rajta átmenő jármű, vagy null, ha csak a hó változik.
     */
    public void change(Vehicle vehicle){
        System.out.println("-> lane.change(vehicle)");
        
        if (vehicle != null) {
            if (snow != null) {
                snow.passVehicle();
            }
        }
    }   
}
