package game;

/**
 * Busz osztály, amely a járműveket reprezentálja.
 * Minden busznak van egy tulajdonosa (buszvezető), egy kezdőpontja és egy végpontja.
 * A busz képes váltani a járatát, megállni a forgalomban és mozogni egy adott pontra.
 */
public class Bus extends Vehicle {
    private BusDriver owner; // A busz tulajdonosa, aki a buszt vezeti.
    private Point beginningPoint; // A busz kezdőpontja, ahonnan elindul.
    private Point endPoint; // A busz végpontja, ahová tart.

    // Paraméter nélküli konstruktor
    public Bus() {
        super();
        owner = null;
        beginningPoint = null;
        endPoint = null;
        canSlip = true;
    }

    //paraméteres konstruktor
    public Bus(String id) {
        super(id);
        owner = null;
        beginningPoint = null;
        endPoint = null;
        canSlip = true;
    }

    // Getterek és setterek
    public Lane getNextLane() {
        return nextLane;
    }

    public BusDriver getOwner() {
        return owner;
    }

    public void setOwner(BusDriver owner) {
        this.owner = owner;
    }

    public Point getBeginningPoint() {
        return beginningPoint;
    }

    public void setBeginningPoint(Point beginningPoint) {
        this.beginningPoint = beginningPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
    }

    // Megcseréli a kezdőpontot és a végpontot, így a busz visszafordulhat a kiindulási helyére.
    public void switchRoute() {
        Point temp = beginningPoint;
        beginningPoint = endPoint;
        endPoint = temp;
    }

    // Ha ütközés történik, a busz megáll a forgalomban.
    @Override
    public void jam() {
        super.setJammedTime(5);
        Logger.log("> ACTION: " + this.getId() + " jammed_at " + this.getCurrentPoint().getId());
    }

    /*
    *  A busz mozog egy adott pont felé.
    *  @param point A pont, amely felé a busz mozogni fog.
     */
    @Override
    public void move(Point point) {
        if (super.getJammedTime() > 0) {
            return; // Ha a busz elakadt, nem mozoghat.
        }

        if (point.isReachable(this)) {
            super.getCurrentPoint().removeVehicle(this);
            super.setCurrentPoint(point);
            super.getCurrentPoint().addVehicle(this);
            super.setLastLane(nextLane);
            super.getLastLane().getSnow().passVehicle();
            super.setNextLane(null);

            if (point.equals(endPoint)) {
                switchRoute();
            }
            Logger.log("> ACTION: " + this.getId() + " moved_to " + point.getId());
        }
    }

    @Override
    public void stat() {
        Logger.log("Bus ID: " + getId());
        Logger.log("Type: Bus");
        Logger.log("Current Point: " + (getCurrentPoint() != null ? getCurrentPoint().getId() : "null"));
        Logger.log("Last Lane: " + (getLastLane() != null ? getLastLane().getId() : "null"));
        Logger.log("Next Lane: " + (getNextLane() != null ? getNextLane().getId() : "null"));
        Logger.log("Jammed Time: " + getJammedTime());
        Logger.log("Can Slip: " + canSlip);
        Logger.log("Owner: " + (getOwner() != null ? getOwner().getName() : "null"));
        Logger.log("Beginning Point: " + (getBeginningPoint() != null ? getBeginningPoint().getId() : "null"));
        Logger.log("End Point: " + (getEndPoint() != null ? getEndPoint().getId() : "null"));
    }
}
