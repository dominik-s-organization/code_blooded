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

                // Csak akkor vizsgáljuk, ha még nem jártunk ott ÉS a pont járható az autónak
                if (nextPoint != null && !visited.contains(nextPoint) && nextPoint.isReachable(this)) {
                    visited.add(nextPoint);
                    parentMap.put(nextPoint, p); 
                    edgeToMap.put(nextPoint, lane); // Feljegyezzük a SÁVOT is, amin idejutottunk!
                    queue.add(nextPoint);
                }
            }
        }

        // Ha a BFS nem talált utat (pl. minden út le van zárva/balesetes)
        if (foundTarget == null) {
            return null;
        }

        // Visszakövetjük az utat a céltól egészen a jelenlegi pont utáni első lépésig
        Point step = foundTarget;
        while (parentMap.get(step) != current) {
            step = parentMap.get(step);
        }

        // Itt a varázslat: a legelső pontot már nem pontként, hanem a hozzá vezető sávként adjuk vissza
        Lane laneToTake = edgeToMap.get(step);

        return laneToTake;
    }

    @Override
    public void stat() {
        System.out.println("Car ID: " + getId());
        System.out.println("Type: Car");
        System.out.println("Current Point: " + (getCurrentPoint() != null ? getCurrentPoint().getId() : "null"));
        System.out.println("Last Lane: " + (getLastLane() != null ? getLastLane().getId() : "null"));
        System.out.println("Next Lane: " + (getNextLane() != null ? getNextLane().getId() : "null"));
        System.out.println("Jammed Time: " + getJammedTime());
        System.out.println("Can Slip: " + canSlip);
        System.out.println("Home: " + (getHome() != null ? getHome().getId() : "null"));
        System.out.println("Work: " + (getWork() != null ? getWork().getId() : "null"));
        System.out.println("Is Heading Home: " + isHeadingHome);
    }
}
