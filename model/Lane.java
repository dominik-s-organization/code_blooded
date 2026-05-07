package game;

/**
 * A Lane osztály egy sávot reprezentál a játékban.
 */
public class Lane {
    private String id; // A sáv egyedi azonosítója.
    private Point startpoint; // sáv kezdőpontja, ami lehet egy Junction vagy egy CrossRoads
    private Point endpoint; // sáv végpontja, ami lehet egy Junction vagy egy CrossRoads
    private Lane leftLane; // balra eső sáv
    private Lane rightLane; // jobbra eső sáv
    private boolean isJammed; // jelzi, hogy a sávon van-e dugó
    private boolean isUnderground; // jelzi, hogy a sáv föld alatt van-e (aluljáró)
    private Snow snow;

    // Konstruktor
    public Lane() {
        this(null);
    }

    public Lane(String id) {
        this.id = id;
        startpoint = null;
        endpoint = null;
        leftLane = null;
        rightLane = null;
        isJammed = false;
        isUnderground = false;
        snow = new Snow();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (this.id == null) {
            this.id = id;
        }
    }

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
     * Növeli a hó szintjét a sávon a megfelelő módon
     */
    public void raiseSnow() {        
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

    /**
     * Kiírja a sáv állapotát és tulajdonságait a konzolra.
     */
    public void stat() {
        Logger.log("Lane ID: " + getId());
        Logger.log("Start Point: " + (getStartPoint() != null ? getStartPoint().getId() : "null"));
        Logger.log("End Point: " + (getEndPoint() != null ? getEndPoint().getId() : "null"));
        Logger.log("Left Lane: " + (getLeftLane() != null ? getLeftLane().getId() : "null"));
        Logger.log("Right Lane: " + (getRightLane() != null ? getRightLane().getId() : "null"));
        Logger.log("Is Jammed: " + isJammed());
        Logger.log("Is Underground: " + isUnderground());
        Logger.log("Snow Level: " + snow.getLevel());
        Logger.log("Ice: " + snow.isIce());
        Logger.log("Broken Ice: " + snow.isBrokenIce());
        Logger.log("Salt Level: " + snow.getSaltLevel());
        Logger.log("Crushed Stone Level: " + snow.getCrushedStoneLevel());
        Logger.log("Vehicles Passed: " + snow.getVehiclesPassed());
        Logger.log("");
    }
}
