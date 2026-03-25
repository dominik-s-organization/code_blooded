package game;

/**
 * A Car osztály a Vehicle absztrakt osztályból származik, és egy autót reprezentál a játékban.
 */
public class Car extends Vehicle {
    private Point home; // Az autó otthona, ahová vissza akar jutni.
    private Point work; // Az autó munkahelye, ahová el kell jutni.
    private boolean moveable; // Jelzi, hogy az autó képes-e mozogni.

    public Car(Point h, Point w){
        home = h;
        work = w;
        moveable = true;
    }

    @Override
    // Ha ütközés történik, az autó megáll a forgalomban.
    public void jam(){}

    @Override
    /*
    * Az autó mozog egy adott pont felé.
    * @param point A pont, amely felé az autó mozogni fog.
    */
    public void move(Point point){}

    // Kiszámítja a következő pontot, amely felé az autónak mozognia kell, hogy elérje a célját (otthon vagy munkahely).
    public Point calculateNextPoint() {
        // Implementáció majd itt
        return null;
    }
}
