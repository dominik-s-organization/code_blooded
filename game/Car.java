package game;

/**
 * A Car osztály a Vehicle absztrakt osztályból származik, és egy autót reprezentál a játékban.
 */
public class Car extends Vehicle {
    private Point home; // Az autó otthona, ahová vissza akar jutni.
    private Point work; // Az autó munkahelye, ahová el kell jutni.

    // Konstruktor
    public Car() {
        super();
        home = null;
        work = null;
    }

    // Getterek, setterek
    public Point getHome() {
        System.out.println("-> car.getHome()");
        System.out.println("<- home");
        return home;
    }

    public void setHome(Point home) {
        System.out.println("-> car.setHome(home)");
        this.home = home;
    }

    public Point getWork() {
        System.out.println("-> car.getWork()");
        System.out.println("<- work");
        return work;
    }

    public void setWork(Point work) {
        System.out.println("-> car.setWork(work)");
        this.work = work;
    }

    @Override
    // Ha ütközés történik, az autó megáll a forgalomban.
    public void jam() {
        System.out.println("-> car.jam()");
        super.setJammedTime(5);
    }

    @Override
    /*
    * Az autó mozog egy adott pont felé.
    * @param point A pont, amely felé az autó mozogni fog.
    */
    public void move(Point point) {
        System.out.println("-> car.move(point)");

        if (super.getJammedTime() > 0) {
            return; // Ha a busz elakadt, nem mozoghat.
        }

        if (point.isReachable(this)) {
            super.getCurrentPoint().removeVehicle(this);
            super.setCurrentPoint(point);
            super.getCurrentPoint().addVehicle(this);
            super.setLastLane(point.getIncomingLanes().get(0));
        }
    }

    /*
    * Kiszámolja és visszaadja a következő pontot, amely felé az autó haladni próbál.
    * @return A következő pont (Point), amely felé az autó haladni próbál.
    */
    public Point getNextPoint() {
        System.out.println("-> car.getNextPoint()");
        System.out.println("<- nextPoint");
        return nextPoint;
    }
}
