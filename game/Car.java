package game;

/**
 * A Car osztály a Vehicle absztrakt osztályból származik, és egy autót reprezentál a játékban.
 */
public class Car extends Vehicle {
    private Point home; // Az autó otthona, ahová vissza akar jutni.
    private Point work; // Az autó munkahelye, ahová el kell jutni.

    public Car() {
        home = null;
        work = null;
    }

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
    public void jam(){
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
        super.getCurrentPoint().removeVehicle(this);
        super.setCurrentPoint(point);
        super.getCurrentPoint().addVehicle(this);
        super.setLastLane(point.getIncomingLane(0));
    }

    // Kiszámítja a következő pontot, amely felé az autónak mozognia kell, hogy elérje a célját (otthon vagy munkahely).
    public Point calculateNextPoint() {
        System.out.println("-> car.calculateNextPoint()");
        System.out.println("<- point");
        return null;
    }
}
