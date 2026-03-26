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

    public BusDriver getOwner() {
        System.out.println("-> bus.getOwner()");
        System.out.println("<- busDriver");
        return owner;
    }

    public void setOwner(BusDriver owner) {
        System.out.println("-> bus.setOwner(busDriver)");
        this.owner = owner;
    }

    public Point getBeginningPoint() {
        System.out.println("-> bus.getBeginningPoint()");
        System.out.println("<- p1");
        return beginningPoint;
    }

    public void setBeginningPoint(Point beginningPoint) {
        System.out.println("-> bus.setBeginningPoint(p1)");
        this.beginningPoint = beginningPoint;
    }

    public Point getEndPoint() {
        System.out.println("-> bus.getEndPoint()");
        System.out.println("<- p2");
        return endPoint;
    }

    public void setEndPoint(Point endPoint) {
        System.out.println("-> bus.setEndPoint(p2)");
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
    }

    /*
    *  A busz mozog egy adott pont felé.
    *  @param point A pont, amely felé a busz mozogni fog.
     */
    @Override
    public void move(Point point) {
        System.out.println("-> bus.move(p2)");
    }
}
