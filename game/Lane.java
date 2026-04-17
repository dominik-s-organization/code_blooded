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
    private Snow snow;

    // Konstruktor
    public Lane() {
        startpoint = null;
        endpoint = null;
        leftLane = null;
        rightLane = null;
        isJammed = false;
        isUnderground = false;
        snow = new Snow();
    }

    // Getterek, setterek
    public void setStartPoint(Point startpoint) {
        this.startpoint = startpoint;
    }

    public Point getStartPoint() {
        return startpoint;
    }

    public void setEndPoint(Point endpoint) {
        this.endpoint = endpoint;
    }

    public Point getEndPoint() {
        return endpoint;
    }

    public Lane getLeftLane() {
        return leftLane;
    }

    public void setLeftLane(Lane leftLane) {
        this.leftLane = leftLane;
    }

    public Lane getRightLane() {
        return rightLane;
    }

    public void setRightLane(Lane rightLane) {
        this.rightLane = rightLane;
    }

    public boolean isJammed() {
        return isJammed;
    }
    
    public void setJammed(boolean isJammed) {
        this.isJammed = isJammed;
    }

    public boolean isUnderground() {
        return isUnderground;
    }

    public void setUnderground(boolean isUnderground) {
        this.isUnderground = isUnderground;
    }

    public Snow getSnow() {
        return snow;
    }

    public void setSnow(Snow snow) {
        this.snow = snow;
    }

    /*
     * Változtat a sáv állapotán.
     * @param vehicle, a rajta átmenő jármű, vagy null, ha csak a hó változik.
     */
    public void change(Vehicle vehicle) {        
        if (vehicle == null) {
            if (snow.getSaltLevel() > 0) {
                snow.setSaltLevel(snow.getSaltLevel() - 1);
                snow.lower();
                snow.setIce(false);
                snow.setBrokenIce(false);
            }
            else {
                if (snow.getCrushedStoneLevel() > 0) {
                    snow.setCrushedStoneLevel(snow.getCrushedStoneLevel() - 1);
                }
                snow.raise();
            }
        }
        else if (vehicle instanceof Bus || vehicle instanceof Car) {
            snow.passVehicle();
        }
        else if (vehicle instanceof SnowPlower) {
            SnowPlower sp = (SnowPlower) vehicle;
            sp.getCurrentHead().clean(this, sp);
        }
    }    
}
