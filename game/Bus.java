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

    // Konstruktor
    public Bus() {
        super();
        owner = null;
        beginningPoint = null;
        endPoint = null;
    }

    // Getterek, setterek
    public Point getNextPoint() {
        System.out.println("-> bus.getNextPoint()");
        System.out.println("<- nextPoint");
        return nextPoint;
    }

    public BusDriver getOwner() {
        System.out.println("-> bus.getOwner()");
        System.out.println("<- owner");
        return owner;
    }

    public void setOwner(BusDriver owner) {
        System.out.println("-> bus.setOwner(owner)");
        this.owner = owner;
    }

    public Point getBeginningPoint() {
        System.out.println("-> bus.getBeginningPoint()");
        System.out.println("<- beginningPoint");
        return beginningPoint;
    }

    public void setBeginningPoint(Point beginningPoint) {
        System.out.println("-> bus.setBeginningPoint(point)");
        this.beginningPoint = beginningPoint;
    }

    public Point getEndPoint() {
        System.out.println("-> bus.getEndPoint()");
        System.out.println("<- endPoint");
        return endPoint;
    }

    public void setEndPoint(Point endPoint) {
        System.out.println("-> bus.setEndPoint(point)");
        this.endPoint = endPoint;
    }

    // Megcseréli a kezdőpontot és a végpontot, így a busz visszafordulhat a kiindulási helyére.
    public void switchRoute() {
        beginningPoint = endPoint;
        endPoint = beginningPoint;
        System.out.println("-> bus.switchRoute()");
    }

    // Ha ütközés történik, a busz megáll a forgalomban.
    @Override
    public void jam() {
        System.out.println("-> bus.jam()");
        super.setJammedTime(5);
    }

    /*
    *  A busz mozog egy adott pont felé.
    *  @param point A pont, amely felé a busz mozogni fog.
     */
    @Override
    public void move(Point point) {
        System.out.println("-> bus.move(point)");
        
        if (super.getJammedTime() > 0) {
            return; // Ha a busz elakadt, nem mozoghat.
        }

        if (point.isReachable(this)) {
            super.getCurrentPoint().removeVehicle(this);
            super.setCurrentPoint(point);
            super.getCurrentPoint().addVehicle(this);
            super.setLastLane(point.getIncomingLanes().get(0));

            if (point.equals(endPoint)) {
                switchRoute();
            }
        }
    }
}
