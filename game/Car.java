package game;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * A Car osztály a Vehicle absztrakt osztályból származik, és egy autót reprezentál a játékban.
 */
public class Car extends Vehicle {
    private Point home; // Az autó otthona, ahová vissza akar jutni.
    private Point work; // Az autó munkahelye, ahová el kell jutni.
    private boolean isHeadingHome; // Jelzi, hogy az autó éppen hazafelé tart-e.

    // Paraméter nélküli konstruktor
    public Car() {
        super();
        home = null;
        work = null;
        isHeadingHome = false;
        canSlip = true;
    }

    public Car(String id) {
        super(id);
        home = null;
        work = null;
        isHeadingHome = false;
        canSlip = true;
    }

    // Getterek, setterek
    public Point getHome() {
        return home;
    }

    public void setHome(Point home) {
        this.home = home;
    }

    public Point getWork() {
        return work;
    }

    public void setWork(Point work) {
        this.work = work;
    }

    @Override
    // Ha ütközés történik, az autó megáll a forgalomban.
    public void jam() {
        super.setJammedTime(10);
    }

    @Override
    /*
    * Az autó mozog egy adott pont felé.
    * @param point A pont, amely felé az autó mozogni fog.
    */
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
            Logger.log("> ACTION: " + this.getId() + " moved_to " + point.getId());
        }

        if (point.equals(work)) {
            isHeadingHome = true;
        }
        else if (point.equals(home)) {
            isHeadingHome = false;
        }
    }

    /*
    * Kiszámolja és visszaadja a következő sávot, amelyen az autó át akar haladni.
    * @return A következő sáv (Lane), amelyen az autó át akar haladni.
    */
    public Lane getNextLane() {
         Point current = this.getCurrentPoint();

        // Ha nincs beállítva otthon vagy munkahely, nem tud hova menni
        if (home == null || work == null || current == null) {
            return null;
        }

        Point target = isHeadingHome ? home : work;

        // Ha már megérkezett a célba, megfordítjuk az irányt, és az új cél felé indul
        if (current == target) {
            isHeadingHome = !isHeadingHome;
            target = isHeadingHome ? home : work;
        }

        // BFS algoritmus inicializálása
        Queue<Point> queue = new LinkedList<>();
        Map<Point, Point> parentMap = new HashMap<>(); // Melyik pontból jöttünk
        Map<Point, Lane> edgeToMap = new HashMap<>();  // Melyik SÁVON jöttünk az adott pontba

        Set<Point> visited = new HashSet<>(); // A már megvizsgált pontok halmaza

        queue.add(current);
        visited.add(current);

        Point foundTarget = null;

        // Gráf bejárása
        while (!queue.isEmpty()) {
            Point p = queue.poll();

            // Ha megtaláltuk a célt, leállítjuk a keresést
            if (p == target) {
                foundTarget = p;
                break;
            }

            // Szomszédos csomópontok (kimenő sávok végpontjai) vizsgálata
            for (Lane lane : p.getOutgoingLanes()) {
                Point nextPoint = lane.getEndPoint();

                // Útvonaltervezésnél csak a hálózat struktúráját nézzük, isReachable nélkül!
                // Ez megakadályozza a végtelen rekurziót (StackOverflowError).
                if (nextPoint != null && !visited.contains(nextPoint)) {
                    visited.add(nextPoint);
                    parentMap.put(nextPoint, p); 
                    edgeToMap.put(nextPoint, lane); // Feljegyezzük a SÁVOT is, amin idejutottunk
                    queue.add(nextPoint);
                }
            }
        }

        // Ha a BFS nem talált utat (pl. megszakadt a hálózat)
        if (foundTarget == null) {
            return null;
        }

        // Visszakövetjük az utat a céltól egészen a jelenlegi pont utáni első lépésig
        Point step = foundTarget;
        while (parentMap.get(step) != current) {
            step = parentMap.get(step);
        }

        // Visszaadjuk az első lépéshez tartozó sávot
        Lane laneToTake = edgeToMap.get(step);

        super.nextLane = laneToTake; // Beállítjuk a következő sávot, amin át akar haladni

        return laneToTake;
    }

    @Override
    public void stat() {
        Logger.log("Car ID: " + getId());
        Logger.log("Type: Car");
        Logger.log("Current Point: " + (getCurrentPoint() != null ? getCurrentPoint().getId() : "null"));
        Logger.log("Last Lane: " + (getLastLane() != null ? getLastLane().getId() : "null"));
        Logger.log("Next Lane: " + (getNextLane() != null ? getNextLane().getId() : "null"));
        Logger.log("Jammed Time: " + getJammedTime());
        Logger.log("Can Slip: " + canSlip);
        Logger.log("Home: " + (getHome() != null ? getHome().getId() : "null"));
        Logger.log("Work: " + (getWork() != null ? getWork().getId() : "null"));
        Logger.log("Is Heading Home: " + isHeadingHome);
        Logger.log("");
    }
}
