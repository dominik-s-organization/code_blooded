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

    //Kostruktor
    public Bus(BusDriver o, Point bPoint, Point ePoint){
        owner = o;
        beginningPoint = bPoint;
        endPoint = ePoint;
    }

    // Megcseréli a kezdőpontot és a végpontot, így a busz visszafordulhat a kiindulási helyére.
    public void switchRoute() {
        beginningPoint = endPoint;
        endPoint = beginningPoint;
        System.out.print("-> switchRoute()");
    }

    // Ha ütközés történik, a busz megáll a forgalomban.
    @Override
    public void jam() {
        System.out.print("-> jam()");
    }

    /*
    *  A busz mozog egy adott pont felé, amely lehet a kezdőpont vagy a végpont.
    *  @param point
    *  A pont, amely felé a busz mozogni fog.
     */
    @Override
    public void move(Point point) {
        System.out.print("-> move()");
    }
    
}
